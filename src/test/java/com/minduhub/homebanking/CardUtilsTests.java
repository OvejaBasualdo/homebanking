package com.minduhub.homebanking;

import com.minduhub.Utils.CardUtils;
import com.minduhub.homebanking.models.CardColor;
import com.minduhub.homebanking.models.CardType;
import org.hamcrest.text.CharSequenceLength;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;

@SpringBootTest
public class CardUtilsTests {

    @Test
    public void cardNumberIsCreated() {
        Integer randomValue = (int) (Math.random() * 20);
        String cardNumber = CardUtils.generateRandomDigits(randomValue);
//        assertThat(cardNumber, is(not(emptyOrNullString())));
        assertThat(cardNumber, CharSequenceLength.hasLength(randomValue));//nuevo metodo
    }

    @Test
    public void cardAssignYear() {
        Integer randomType = (int) (Math.random() * 3);
        CardColor cardColor = null;
        switch (randomType) {
            case 1:
                cardColor = CardColor.SILVER;
                break;
            case 2:
                cardColor = CardColor.GOLD;
                break;
            default:
                cardColor = CardColor.TITANIUM;
                break;
        }
        Integer anios = CardUtils.assignYear(cardColor);
        assertThat(anios, isA(Integer.class));
    }

}
