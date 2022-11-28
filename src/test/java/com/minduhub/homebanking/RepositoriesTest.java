package com.minduhub.homebanking;

import com.minduhub.homebanking.models.*;
import com.minduhub.homebanking.repositories.CardRepository;
import com.minduhub.homebanking.repositories.ClientRepository;
import com.minduhub.homebanking.repositories.LoanRepository;
import com.minduhub.homebanking.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {


    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void existLoans() {
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, is(not(empty())));
    }


    @Test
    public void existPersonalLoan() {
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }

//    @Test
//    public void existVacationLoan() {
//        List<Loan> loans = loanRepository.findAll();
//        assertThat(loans, hasItem(hasProperty("name", is("Vacation"))));
//    }

    //
    @Test
    public void existClients() {
        List<Client> client = clientRepository.findAll();
        assertThat(client, is(not(empty())));
    }

    @Test
    public void clientNotNull(){
        Client client = clientRepository.findById(1L).get();
        assertThat(client, notNullValue(Client.class));
    }

    //
    @Test
    public void existCard() {
        Card cards = cardRepository.findById(22L).get();
        assertThat(cards.getType(), instanceOf(CardType.class));
    }

    @Test
    public void cardsEqualsTo(){
        Long id = Long.valueOf(22);
        Card card = cardRepository.findById(22L).get();
        Card card2 = cardRepository.findById(id).get();
        assertThat(card, equalTo(card2));
    }

    //
    @Test
    public void existTransaction() {
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, is(not(empty())));
    }

    @Test
    public void transactionContainsString(){
        Transaction transaction = transactionRepository.findById(3L).get();
        assertThat(transaction.getDescription(), containsString("Sueldo"));
    }
}

