package com.minduhub.homebanking.repositories;

import com.minduhub.homebanking.models.Client;
import com.minduhub.homebanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ClientLoanRepository extends JpaRepository<ClientLoan,Long> {

    List<ClientLoan> findClientLoanByClient(Client client);//-Buscar una lista de ClientLoan por cliente

    List<ClientLoan> findByAmountGreaterThan(Double amount);//-Buscar una lista de ClientLoan que sean mayores a x monto pasado por parametro

    List<ClientLoan> findClientLoanByClientAndAmountLessThan(Client client, Double amount);
    //-Buscar una lista de ClientLoan por cliente que  en cual sus balances sean menores a x monto pasado por parametro
}
