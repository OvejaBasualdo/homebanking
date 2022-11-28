package com.minduhub.Utils;


import com.minduhub.homebanking.models.Account;
import com.minduhub.homebanking.models.TransactionType;

import java.util.Random;


public final class AccountUtils {
    private AccountUtils() {
    }

    public static String generateRandomDigitsAccount(Integer min, Integer max) {
        Random rdm = new Random();
        Integer valor = rdm.nextInt(9 - 3) + 3;
        //con esto genero un valor entre 3 y 8 para saber la cantidad de dígitos aleatorios para la cuenta
        String randomNumber = "";
        for (int j = 0; j < valor; j++) {
            int newNumber = (int) (Math.random() * 10);
            randomNumber += String.valueOf(newNumber);
        }
        return randomNumber;
    }

    public static Double refreshBalance(TransactionType type, Account account, Double amount) {

        double total = account.getBalance();
        switch (type) {
            case DEBIT:
                total = total - amount;
                break;
            case CREDIT:
                total = total + amount;
                break;
        }

        account.setBalance(total);
        return total;
    }
}
