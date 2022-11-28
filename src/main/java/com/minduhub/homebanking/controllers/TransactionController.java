package com.minduhub.homebanking.controllers;

import com.minduhub.homebanking.dtos.TransactionDTO;
import com.minduhub.homebanking.models.Account;
import com.minduhub.homebanking.models.Client;
import com.minduhub.homebanking.models.Transaction;
import com.minduhub.homebanking.models.TransactionType;
import com.minduhub.homebanking.repositories.AccountRepository;
import com.minduhub.homebanking.repositories.ClientRepository;
import com.minduhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactions() {
        return transactionRepository.findAll().stream().map(TransactionDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/transactions/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id) {
        return transactionRepository.findById(id).map(TransactionDTO::new).orElse(null);
    }

    @GetMapping("/transactions/type/")
    public List<TransactionDTO> getTransaction(@RequestParam TransactionType type) {
        return transactionRepository.findByType(type).stream().map(TransactionDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/transactions/transactionBetweenAmount/")
    public List<TransactionDTO> getTransactionBetween(@RequestParam Double amountMin, Double amountMax) {
        return transactionRepository.findByAmountBetween(amountMin, amountMax).stream().map(TransactionDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/transactions/transactionBetweenDate/")
    public List<TransactionDTO> getTransactionBetweenDate(@RequestParam String maxDate, String minDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return transactionRepository.findByDateBetween(LocalDateTime.parse(minDate), LocalDateTime.parse(maxDate)).stream().map(TransactionDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransactionsBetweenAccount(Authentication authentication, @RequestParam Double amount, @RequestParam String description,
                                                                   @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber) {
        try {
            Client client = clientRepository.findByEmail(authentication.getName()).get();
            if (!(amount <= 0.00) && !(description.isEmpty()) && !(fromAccountNumber.isEmpty()) && !(toAccountNumber.isEmpty())) {
                Optional<Account> optionalFromAccount = accountRepository.findByNumber(fromAccountNumber);
                Optional<Account> optionalToAccount = accountRepository.findByNumber(toAccountNumber);
                if (!(fromAccountNumber.equals(toAccountNumber)) && optionalFromAccount.isPresent()) {//valuamos q los numeros sean diferentes
                    Set<Account> accountsClientAuthentication = client.getAccounts();//toma todas las cuentas del cliente autenticado
                    if (accountsClientAuthentication.contains(optionalFromAccount.get()) &&
                            optionalToAccount.isPresent() &&
                            optionalFromAccount.get().getBalance() >= amount) {
                        Transaction transactionFrom = new Transaction(TransactionType.DEBIT, amount, description, LocalDateTime.now(), optionalFromAccount.get());
                        transactionRepository.save(transactionFrom);
                        Transaction transactionTo = new Transaction(TransactionType.CREDIT, amount, description, LocalDateTime.now(), optionalToAccount.get());
                        transactionRepository.save(transactionTo);
                        return new ResponseEntity<>("Transaction successful", HttpStatus.CREATED);
                    } else {
                        return new ResponseEntity<>("Transaction error, wrong data", HttpStatus.FORBIDDEN);
                    }
                } else {
                    return new ResponseEntity<>("The accounts are the same", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>("Empty parameters", HttpStatus.FORBIDDEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>("Unexpected", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

