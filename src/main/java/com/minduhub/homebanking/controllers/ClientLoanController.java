package com.minduhub.homebanking.controllers;

import com.minduhub.homebanking.dtos.ClientLoanDTO;
import com.minduhub.homebanking.models.Client;
import com.minduhub.homebanking.repositories.ClientLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientLoanController {
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @GetMapping("/clientLoans/getByClient/")
    public List<ClientLoanDTO> getByClient(@RequestParam Client client){
        return clientLoanRepository.findClientLoanByClient(client).stream().map(ClientLoanDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/clientLoans/getAmountGreaterThan/")
    public List<ClientLoanDTO> getAmountGreaterThan(@RequestParam Double amount){
        return clientLoanRepository.findByAmountGreaterThan(amount).stream().map(ClientLoanDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/clientLoans/getByClientAndAmountLessThan/")
    public List<ClientLoanDTO> getByClientsAndAmountLessThan(@RequestParam Client client, Double amount){
        return clientLoanRepository.findClientLoanByClientAndAmountLessThan(client,amount).stream().map(ClientLoanDTO::new).collect(Collectors.toList());
    }
}
