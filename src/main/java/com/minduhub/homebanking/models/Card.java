package com.minduhub.homebanking.models;

import com.minduhub.Utils.CardUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;
    private String cardholder;
    private CardType type;
    private CardColor color;
    private String number;
    private String cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;


    public Card() {
    }

    public Card(Client client, CardType type, CardColor color, LocalDate fromDate) {
        this.client = client;
        this.cardholder = client.getFirstName() + " " + client.getLastName();
        this.type = type;
        this.color = color;
        this.number = CardUtils.generateRandomDigits(4)+"-"+CardUtils.generateRandomDigits(4)+"-"+CardUtils.generateRandomDigits(4)+"-"+CardUtils.generateRandomDigits(4);//generateRandom(4)-generateRandom(4)-generateRandom(4)-generateRandom(4)
        this.cvv = CardUtils.generateRandomDigits(3);//generateRandom(4)-
        this.fromDate = fromDate;
        this.thruDate = fromDate.plusYears(CardUtils.assignYear(this.color));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType cardType) {
        this.type = cardType;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor cardColor) {
        this.color = cardColor;
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

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }



}
