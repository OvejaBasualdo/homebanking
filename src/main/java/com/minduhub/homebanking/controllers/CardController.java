package com.minduhub.homebanking.controllers;

import com.minduhub.homebanking.dtos.CardDTO;
import com.minduhub.homebanking.dtos.CardOperationDTO;
import com.minduhub.homebanking.dtos.LoanDTO;
import com.minduhub.homebanking.models.*;
import com.minduhub.homebanking.repositories.AccountRepository;
import com.minduhub.homebanking.repositories.CardRepository;
import com.minduhub.homebanking.repositories.ClientRepository;
import com.minduhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/clients/current/cards")
    public List<CardDTO> getCards(Authentication authentication) {

        Optional<Client> optionalClient =  clientRepository.findByEmail(authentication.getName());

//FORMA DESARROLLADA PASO A PASO
        if (optionalClient.isPresent()) {
            return optionalClient.get().getCards().stream().map(CardDTO::new).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
        //FORMA CORTA
//        return optionalClient.map(client -> client.getCards().stream().map(CardDTO::new).collect(Collectors.toList())).orElseGet(ArrayList::new);

        //clientRepository.findById(id).get() ---->>> captura el cliente con ese ID
        //getCards --->>> captura todo el Set de tarjetas
        //.stream().map(CardDTO::new).collect(Collectors.toList()) --->>> hace la conversión a lista de Card a CardDTO
        //return clientRepository.findById(id).map(ClientDTO::new).orElse(null).getCards().stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/cardOperation")
    public List<CardDTO> getCardOperation(){
        return cardRepository.findAll().stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, CardColor cardColor, Authentication authentication) {
        //Ojo! El cliente no puede tener mas de tres Cards del mismo cardType
        //Si tiene mas de tres
        //return new ResponseEntity<>("You already have 3 " + cardType + " cards", HttpStatus.FORBIDDEN);
        /***********FORMA HIPERMEGACORTA DE REALIZAR LO MISMO DE ABAJO*************/
//        Optional<Client> client = this.clientRepository.findByEmail(authentication.getName());
//        if (client.get().getCards().stream().filter(card -> card.getType() == cardType).count() >= 3) {
//            return new ResponseEntity<>("You already have 3 " + cardType + " cards", HttpStatus.FORBIDDEN);
//        }

        /*************FORMA LARGA DE REALIZAR EL PASO A PASO*************/
        try {
            Optional<Client> clientOptional = this.clientRepository.findByEmail(authentication.getName());
            if (clientOptional.isPresent()) {
                Client client = clientOptional.get();
                List<Card> cardClient = client.getCards().stream().toList();
                int contador = 0;
                for (Card card : cardClient) {
                    if (card.getType() == cardType) {
                        contador++;
                    }
                }
                if (contador < 3) {
                    cardRepository.save(new Card(client, cardType, cardColor, LocalDate.now()));
                    return new ResponseEntity<>(HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>("You already have 3 " + cardType + " cards", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>("Missing client", HttpStatus.FORBIDDEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Unexpected error", HttpStatus.FORBIDDEN);
        }
    }

    @Transactional
    @PostMapping("/cardOperation")
    public ResponseEntity<Object> createCardOperation(@RequestBody CardOperationDTO cardOperationDTO, Authentication authentication){
        Optional<Card> optionalCard = cardRepository.findByNumber(cardOperationDTO.getCardNumber());
        Client client = clientRepository.findByEmail(authentication.getName()).get();
        //verifico que exista la tarjeta
        if (optionalCard.isEmpty()) {
            return new ResponseEntity<>("card doesn't exist", HttpStatus.FORBIDDEN);
        }
        Card searchedCard = optionalCard.get();
        if (!searchedCard.getCvv().equals(cardOperationDTO.getCvv())) {
            return new ResponseEntity<>("CVV wrong", HttpStatus.FORBIDDEN);
        }
        //verifico que los datos no esten vacios
        if (cardOperationDTO.getAmount() <= 0 || cardOperationDTO.getCardNumber().isEmpty() || cardOperationDTO.getCvv().isEmpty() || cardOperationDTO.getDescription().isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        //verificar que sea de débito la tarjeta
        if (searchedCard.getType().equals(CardType.CREDIT)){
            return new ResponseEntity<>("You can't do this operation. Use a DEBIT CARD", HttpStatus.FORBIDDEN);
        }
        //verificacion de montos de cuenta, traer el set de cuentas y verificar que el monto de débito sea menor al saldo

        if (accountRepository.findByBalanceGreaterThanAndClient(cardOperationDTO.getAmount(), client).isEmpty()){
            return new ResponseEntity<>("You don't have enough money.", HttpStatus.FORBIDDEN);
        }
        List<Account> accountsWithBalance = accountRepository.findByBalanceGreaterThanAndClient(cardOperationDTO.getAmount(), client);
        //tarjeta vencida
        if(!cardRepository.findByThruDateLessThan(LocalDate.now()).isEmpty()){
            return new ResponseEntity<>("You're card is expired", HttpStatus.FORBIDDEN);
        }

        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, cardOperationDTO.getAmount(), cardOperationDTO.getDescription(), LocalDateTime.now(),accountsWithBalance.get(0));
        transactionRepository.save(debitTransaction);

        return new ResponseEntity<>("You payed something", HttpStatus.CREATED);
    }
}
