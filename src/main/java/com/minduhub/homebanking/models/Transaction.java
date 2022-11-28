package com.minduhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minduhub.Utils.AccountUtils;
import com.minduhub.Utils.CardUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    public Transaction( TransactionType type, Double amount, String description, LocalDateTime date, Account account) {
        this.type = type;
        this.amount = CardUtils.getAmountWithType(this.type,amount);//método que, según el tipo de transacción, pone en negativo o positivo
        this.date = date;
        this.description = description;
        this.account = account;
        AccountUtils.refreshBalance(this.type,this.account, amount);


    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


}
