package ru.firstproject;


import ru.firstproject.model.Menu;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.firstproject.model.AbstractBaseEntity.START_SEQ;

public class MenuTestData {
    public static final int MENU_SEQ1 = START_SEQ + 5;
    public static final int MENU_SEQ2 = START_SEQ + 6;
    public static final int MENU_SEQ3 = START_SEQ + 7;

    public static final Menu MENU1 = new Menu(MENU_SEQ1);
    public static final Menu MENU2 = new Menu(MENU_SEQ2);
    public static final Menu MENU3 = new Menu(MENU_SEQ3);


    public static <T> void assertMatch(T actual, T expected,String ... ignoringFields) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected,ignoringFields);
    }

    public static <T> void assertMatch(Iterable<T> actual, Iterable<T> expected,String ... ignoringFields) {
        assertThat(actual).usingElementComparatorIgnoringFields(ignoringFields).isEqualTo(expected);
    }
}
