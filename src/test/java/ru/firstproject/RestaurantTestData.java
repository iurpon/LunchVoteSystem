package ru.firstproject;

import ru.firstproject.model.Restaurant;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.firstproject.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final int RESTAURANT_SEQ1 = START_SEQ + 2;
    public static final int RESTAURANT_SEQ2 = START_SEQ + 3;
    public static final int RESTAURANT_SEQ3 = START_SEQ + 4;


    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT_SEQ3, "BURGER KING", "NY, Washington street 2");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT_SEQ2, "MADDISON SQUARE AVENIU", "NY, 2-nd street 39");
    public static final Restaurant RESTAURANT3 = new Restaurant(RESTAURANT_SEQ1, "OLIVE GARDEN", "NY, 1-st street 58");




    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields().isEqualTo(expected);
    }

}
