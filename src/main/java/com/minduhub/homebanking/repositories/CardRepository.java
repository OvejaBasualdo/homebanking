package com.minduhub.homebanking.repositories;

import com.minduhub.homebanking.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.Optional;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card,Long> {
    Optional<Card> findByNumber(String number);
    Optional<Card> findByThruDateLessThan(LocalDate thruDate);
}

