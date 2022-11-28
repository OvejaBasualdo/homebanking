package com.minduhub.homebanking.dtos;

import com.minduhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {

    private Long id;

    private Double amount;

    private Integer payments;

    private Long loanId;
    private String name;

    public ClientLoanDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayment();
        this.loanId = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
    }

}
