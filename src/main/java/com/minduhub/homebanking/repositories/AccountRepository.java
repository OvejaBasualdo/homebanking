package com.minduhub.homebanking.repositories;

import com.minduhub.homebanking.models.Account;
import com.minduhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findByNumber(String number);//-Buscar una cuenta por Numero de cuenta
    List<Account> findByBalanceGreaterThan(Double BalanceLessThan); //-Buscar una lista de cuentas en el cual su balance se mayor a x monto pasado por parametro
    List<Account> findByCreationDateLessThan(LocalDateTime parameterFecha);//-Buscar una lista de cuentas por en la cual sue fecha se menor a la que le pasemos por parametro

    List<Account> findByBalanceGreaterThanAndClient(Double BalanceLessThan, Client client);
}
