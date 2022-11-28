package com.minduhub.homebanking.dtos;

import com.minduhub.homebanking.models.Card;
import com.minduhub.homebanking.models.CardColor;
import com.minduhub.homebanking.models.CardType;

import java.time.LocalDate;

public class CardDTO {
    private Long id;
    private CardType type;

    private String cardholder;
    private CardColor color;
    private String number;
    private String cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;

    public CardDTO(CardDTO cardDTO) {
    }

    public String getCardHolder() {
        return cardholder;
    }

    public void setCardHolder(String cardholder) {
        this.cardholder = cardholder;
    }

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardholder = card.getCardholder();
        this.type = card.getType();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
    }


    public CardDTO() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }
}
