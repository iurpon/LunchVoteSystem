package ru.firstproject;

import ru.firstproject.model.Restaurant;



import static org.assertj.core.api.Assertions.assertThat;
import static ru.firstproject.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final int REST_SEQ1 = START_SEQ + 2;
    public static final int REST_SEQ2 = START_SEQ + 3;
    public static final int REST_SEQ3 = START_SEQ + 4;

    public static final Restaurant RESTAURANT1 = new Restaurant(REST_SEQ1, "OLIVE GARDEN", "NY, 1-st street 58");
    public static final Restaurant RESTAURANT2 = new Restaurant(REST_SEQ2, "MADDISON SQUARE AVENIU", "NY, 2-nd street 39");
    public static final Restaurant RESTAURANT3 = new Restaurant(REST_SEQ3, "BURGER KING", "NY, Washington street 2");




    public static void assertMatch(Restaurant actual, Restaurant expected,String ... ignoring) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected,ignoring);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected,String... ignoring) {
        assertThat(actual).usingElementComparatorIgnoringFields(ignoring).isEqualTo(expected);
    }

}
