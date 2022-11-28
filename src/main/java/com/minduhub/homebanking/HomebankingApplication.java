package com.minduhub.homebanking;

import com.minduhub.Utils.AccountUtils;
import com.minduhub.homebanking.models.*;
import com.minduhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}


//	@Autowired
//	PasswordEncoder passwordEncoder;
//	@Bean
//	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository,
//									  LoanRepository loanRepository, ClientLoanRepository clientLoanRepository,
//									  CardRepository cardRepository) {
//		return (args) -> {
//			Client cliente = new Client("Melba", "Lorenzo", "melba@mindhub.com", passwordEncoder.encode("Galletita123"));
//			clientRepository.save(cliente);
//			Account account1 = new Account(LocalDateTime.now(),0.00,cliente);
//			accountRepository.save(account1);
//			Transaction transactionAcc1_1 = new Transaction(TransactionType.CREDIT, 500000.00,"Sueldo Accenture", LocalDateTime.now().minusDays(3),account1);
//			transactionRepository.save(transactionAcc1_1);
//			Transaction transactionAcc1 = new Transaction(TransactionType.DEBIT, 500.00,"Compra" , LocalDateTime.now(),account1);
//			transactionRepository.save(transactionAcc1);
//			accountRepository.save(account1);
//			Account account2 = new Account("VIN002", LocalDateTime.now().plusDays(1), 7000.00,cliente);
//			accountRepository.save(account2);
//			Transaction transactionAcc2 = new Transaction(TransactionType.DEBIT, 7500.00,"MercadoLibre", LocalDateTime.now(),account2);
//			transactionRepository.save(transactionAcc2);
//			Transaction transactionAcc2_1 = new Transaction(TransactionType.CREDIT, 400000.00,"Sueldo Accenture", LocalDateTime.now(),account2);
//			transactionRepository.save(transactionAcc2_1);
//			accountRepository.save(account2);
//			Client cliente2 = new Client("Juan", "Pipoca", "juan@mindhub.com", passwordEncoder.encode("Pochoclo456"));
//			clientRepository.save(cliente2);
//			Account account3 = new Account("VIN003", LocalDateTime.now(),0.00,cliente2);
//			accountRepository.save(account3);
//			Transaction transactionAcc3 = new Transaction(TransactionType.DEBIT, 18500.00,"CYBERMONDAY - Fravega", LocalDateTime.now(),account3);
//			transactionRepository.save(transactionAcc3);
//			Transaction transactionAcc3_1 = new Transaction(TransactionType.CREDIT, 575000.00,"Sueldo Accenture", LocalDateTime.now(),account3);
//			transactionRepository.save(transactionAcc3_1);
//			accountRepository.save(account3);
//			Account account4 = new Account("VIN004", LocalDateTime.now().plusDays(1), 0.00,cliente2);
//			accountRepository.save(account4);
//			Transaction transactionAcc4 = new Transaction(TransactionType.DEBIT, 20000.00,"CYBERMONDAY - MercadoLibre", LocalDateTime.now(),account4);
//			transactionRepository.save(transactionAcc4);
//			Transaction transactionAcc4_1 = new Transaction(TransactionType.CREDIT, 575000.00,"Sueldo Accenture", LocalDateTime.now(),account4);
//			transactionRepository.save(transactionAcc4_1);
//			accountRepository.save(account4);
//			//-----//
//			Loan loan1 = new Loan("Hipotecario",500000.00, List.of(12,24,36,48,60));
//			Loan loan2 = new Loan("Personal",100000.00, List.of(6,12,24));
//			Loan loan3 = new Loan("Automotriz",300000.00, List.of(6,12,24,36));
//			loanRepository.save(loan1);
//			loanRepository.save(loan2);
//			loanRepository.save(loan3);
//			ClientLoan clientLoan1 = new ClientLoan(400000.00,60,cliente,loan1);
//			ClientLoan clientLoan2 = new ClientLoan(50000.00,12,cliente,loan2);
//			clientLoanRepository.save(clientLoan1);
//			clientLoanRepository.save(clientLoan2);
//			ClientLoan client1Loan1 = new ClientLoan(100000.00,24,cliente2,loan2);
//			ClientLoan client1Loan2 = new ClientLoan(200000.00,36,cliente2,loan3);
//			clientLoanRepository.save(client1Loan1);
//			clientLoanRepository.save(client1Loan2);
//
//			Card card1 = new Card(cliente,CardType.DEBIT,CardColor.GOLD,LocalDate.now());
//			Card card2 = new Card(cliente,CardType.CREDIT,CardColor.TITANIUM,LocalDate.now());
//			Card card3 = new Card(cliente2,CardType.CREDIT,CardColor.SILVER,LocalDate.now());
//			cardRepository.save(card1);
//			cardRepository.save(card2);
//			cardRepository.save(card3);
//
//
//
//		};
//
//	}
	}



