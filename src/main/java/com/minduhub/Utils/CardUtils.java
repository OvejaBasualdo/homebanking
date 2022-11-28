package com.minduhub.Utils;

import com.minduhub.homebanking.models.Account;
import com.minduhub.homebanking.models.CardColor;
import com.minduhub.homebanking.models.TransactionType;

public final class CardUtils {
    private CardUtils() {
    }

    public static String generateRandomDigits(Integer valor) {
        String randomNumber = "";
        for (int j = 0; j < valor; j++) {
            int newNumber = (int) (Math.random() * 10);
            randomNumber += String.valueOf(newNumber);
        }
        return randomNumber;
    }

    public static Integer assignYear(CardColor color){
        Integer anios = 0;
        switch (color){
            case GOLD :
                anios = 5;
                break;
            case TITANIUM:
                anios = 4;
                break;
            case SILVER:
                anios = 3;
                break;
        }
        return anios;
    }

    public static Double getAmountWithType(TransactionType type, Double amount){
        if(type == TransactionType.DEBIT){
            amount = -(amount);
            return amount;
        }else {
            return amount;
        }
    }


}
