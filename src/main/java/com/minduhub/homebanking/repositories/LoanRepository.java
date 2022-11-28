package com.minduhub.homebanking.repositories;

import com.minduhub.homebanking.models.Client;
import com.minduhub.homebanking.models.ClientLoan;
import com.minduhub.homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan, Long> {



}
