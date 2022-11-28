package com.minduhub.homebanking.controllers;

import com.minduhub.homebanking.dtos.ClientDTO;
import com.minduhub.homebanking.models.Account;
import com.minduhub.homebanking.models.Client;
import com.minduhub.homebanking.repositories.AccountRepository;
import com.minduhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        /* Java FULL HD 4K 8K LASTEST VERSION */
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
        //hace una conversión del cliente a clienteDTO
    }

    @GetMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication) {
        /* Java Old*/
//        Optional<Client> clientOptional=clientRepository.findById(id);
//        if (clientOptional.isPresent()){
//            return new ClientDTO(clientOptional.get());
//        }else {
//            return null;
//        }
        /* Java FULL HD 4K 8K LASTEST VERSION */
        return clientRepository.findByEmail(authentication.getName()).map(ClientDTO::new).orElse(null);
    }


    @GetMapping("/clients/lastName/")
    public List<ClientDTO> getClientByLastName(@RequestParam String lastName) {
        return clientRepository.findByLastNameIgnoreCase(lastName).stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/clients/firstName/")
    public List<ClientDTO> getClientByFirstName(@RequestParam String firstName) {
        return clientRepository.findByFirstNameIgnoreCaseOrderByLastNameAsc(firstName).stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/clients/{id}/clientLoans")
    public ClientDTO getLoansByClient(@PathVariable Long id) {
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

    @GetMapping("/clients/emails/")
    public ClientDTO getClientByEmail(@RequestParam String email) {
        return clientRepository.findByEmail(email).map(ClientDTO::new).orElse(null);
    }

    //
    @PostMapping("/clients")
    public ResponseEntity<Object> createClient(@RequestParam String firstName, @RequestParam String lastName,
                                               @RequestParam String email, @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(email).isPresent()) {
            return new ResponseEntity<>("UserName already exists", HttpStatus.FORBIDDEN);
        }

        try {
            //guarda el cliente
            Client client = clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
            Account account;//declaro la variable account

                do {
                    account = new Account(LocalDateTime.now(), 0.00, client);
                } while (accountRepository.findByNumber(account.getNumber()).isPresent());
                //comprobar que no este repetido
                //el numero de cuenta, si lo está realiza nuevamente el ciclo
                accountRepository.save(account);


                return new ResponseEntity<>(HttpStatus.CREATED);


        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/clients/nameAndEmails/")
    public ClientDTO getClientByFirstNameAndEmail(@RequestParam String firstName, String email) {
        return clientRepository.findByFirstNameIgnoreCaseAndEmailIgnoreCase(firstName, email).map(ClientDTO::new).orElse(null);
    }
    @PostMapping("/clients/new")
    public ClientDTO newClient(@RequestBody Client newClient){
        Client client = this.clientRepository.save(newClient);
        return new ClientDTO(client);
    }
    @DeleteMapping("/clients/delete/{id}")
    public ResponseEntity<Object> deleteClients(@PathVariable Long id){
        this.clientRepository.deleteById(id);
        return new ResponseEntity<>("Deleted Client",HttpStatus.OK);
    }

}
