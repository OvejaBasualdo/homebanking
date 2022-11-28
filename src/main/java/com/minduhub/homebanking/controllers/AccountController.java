package com.minduhub.homebanking.controllers;

import com.minduhub.homebanking.dtos.AccountDTO;
import com.minduhub.homebanking.models.Account;
import com.minduhub.homebanking.models.Client;
import com.minduhub.homebanking.repositories.AccountRepository;
import com.minduhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }
    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName()).get().getAccounts().stream().map(AccountDTO::new).collect(toList());
    }

    @PostMapping("/clients/current/accounts") //localhost:8080/api/clients/1/accounts
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        try {
            Client client = clientRepository.findByEmail(authentication.getName()).get();
            if (client.getAccounts().size()>= 3){
                return new ResponseEntity<>("You already have 3 accounts", HttpStatus.FORBIDDEN);
            }else {
                Account account = accountRepository.save(new Account(LocalDateTime.now(), 0.00, client));
                return new ResponseEntity<>(HttpStatus.CREATED);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Unexpected error", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/accounts/balance/")
    public List<AccountDTO> getAccountsByBalanceGreaterThan(@RequestParam Double balance) {
        return accountRepository.findByBalanceGreaterThan(balance).stream().map(AccountDTO::new).collect(toList());
    }

    @GetMapping ("/accounts/number/")
    public AccountDTO getAccountByNumber(@RequestParam String number) {
        return accountRepository.findByNumber(number).map(AccountDTO::new).orElse(null);
    }

    @GetMapping ("/accounts/toDate/")
    public List<AccountDTO> getAccountToDate(@RequestParam String toDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return accountRepository.findByCreationDateLessThan(LocalDateTime.parse(toDate)).stream().map(AccountDTO::new).collect(toList());
    }


}
