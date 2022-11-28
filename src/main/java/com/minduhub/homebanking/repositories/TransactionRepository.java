package com.minduhub.homebanking.repositories;

import com.minduhub.homebanking.models.Transaction;
import com.minduhub.homebanking.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;
@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByDateBetween(LocalDateTime minDate, LocalDateTime maxDate);//-Buscar una lista de transacciones entre dos fechas pasadas por parametro

    List<Transaction> findByAmountBetween(Double amountMin, Double amountMax);

    //-Buscar una lista de transacciones en las cuales el monto se mayor a x monto pasado como primer parametro,
    //y menor a x monto  pasado como segundo parametro
    List<Transaction> findByType(TransactionType transactionType);//-Buscar una lista de transacciones por type
//    TransactionRepository:


}
