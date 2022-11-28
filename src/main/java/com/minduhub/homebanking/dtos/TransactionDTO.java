package com.minduhub.homebanking.dtos;

import com.minduhub.homebanking.models.Transaction;
import com.minduhub.homebanking.models.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDateTime date;

    public TransactionDTO() {
    }

    public TransactionDTO(Long id, TransactionType type, Double amount, String description, LocalDateTime date) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.date = transaction.getDate();
        this.description = transaction.getDescription();
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
        String newDescription = description;
        switch (this.type){
            case CREDIT :
                newDescription += " - CRÉDITO";
                break;
            case DEBIT:
                newDescription+= " - DÉBITO";
                break;
            default:
                newDescription+= " - ERROR";
                break;
        }
        //crear una transformación de localdatetime a localdate
        LocalDate fecha = this.date.toLocalDate();
        LocalDate hoy = LocalDate.now();
        int diferencia = Math.abs(fecha.compareTo(hoy));
        newDescription += "- Hace " + diferencia + " dias";
        return newDescription;
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
}
