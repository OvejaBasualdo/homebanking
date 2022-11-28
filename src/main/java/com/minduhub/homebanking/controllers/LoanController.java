package com.minduhub.homebanking.controllers;


import com.minduhub.homebanking.dtos.LoanApplicationDTO;
import com.minduhub.homebanking.dtos.LoanDTO;
import com.minduhub.homebanking.models.*;
import com.minduhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/loans")
    public List<LoanDTO> getClientLoans(){
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createClientLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        Optional<Loan> loan = loanRepository.findById(loanApplicationDTO.getLoanId());
        Client client = clientRepository.findByEmail(authentication.getName()).get();
        //-Verificar que el préstamo exista
        if (loan.isEmpty()) {
            return new ResponseEntity<>("Loan doesn't exist ", HttpStatus.FORBIDDEN);
        }
        Loan searchedLoan = loan.get();
        //-Verificar que los datos sean correctos, es decir no estén vacíos, que el monto no sea 0 o que las cuotas no sean 0.
        if (loanApplicationDTO.getAmount() <= 0 || loanApplicationDTO.getPayments() <= 0 || loanApplicationDTO.getToAccountNumber().isEmpty()) {
            return new ResponseEntity<>("Data Error", HttpStatus.FORBIDDEN);
        }
        //Verificar que el monto solicitado no exceda el monto máximo del préstamo
        if (searchedLoan.getMaxAmount() < loanApplicationDTO.getAmount()) {
            return new ResponseEntity<>("Max Amount Exceeded", HttpStatus.FORBIDDEN);
        }

        if (!searchedLoan.getPayments().contains(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>("Wrong amount of payments", HttpStatus.FORBIDDEN);
        }
        //Verificar que la cuenta de destino pertenezca al cliente autenticado
        if (client.getAccounts().stream().noneMatch(account -> account.getNumber().equals(loanApplicationDTO.getToAccountNumber()))) {
            return new ResponseEntity<>("This account doesn't belong to you", HttpStatus.FORBIDDEN);
        }
        Account account = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber()).get();
        //AQUÍ TOMAMOS EL INTERÉS DEL PRÉSTAMO ELEGIDO Y LO ASIGNAMOS A LA VARIABLE
        Double chargesLoan = (searchedLoan.getCharges()/100)+1;
        //EN EL AMOUNT, MULTIPLICAMOS EL MONTO PEDIDO CON EL INTERÉS CORRESPONDIENTE
        ClientLoan clientLoan = new ClientLoan((Math.round((loanApplicationDTO.getAmount() * chargesLoan)*100))/100.0, loanApplicationDTO.getPayments(), client, searchedLoan);
        clientLoanRepository.save(clientLoan);

        Transaction transaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), loan.get().getName() + " - Loan approved", LocalDateTime.now(), account);
        transactionRepository.save(transaction);

        return new ResponseEntity<>("CREATED LOAN, YOU WILL SUFFER", HttpStatus.CREATED);
    }

}
