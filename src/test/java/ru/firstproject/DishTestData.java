package ru.firstproject;

import ru.firstproject.model.Dish;


import static ru.firstproject.RestaurantTestData.*;
import static ru.firstproject.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static final int DISH_SEQ1 = START_SEQ + 8;
    public static final int DISH_SEQ2 = START_SEQ + 9;
    public static final int DISH_SEQ3 = START_SEQ + 10;
    public static final int DISH_SEQ4 = START_SEQ + 11;
    public static final int DISH_SEQ5 = START_SEQ + 12;

    public static final Dish DISH1 = new Dish(DISH_SEQ1,"Big Burger",5.2,RESTAURANT3);
    public static final Dish DISH2 = new Dish(DISH_SEQ2,"Kola",2.0,RESTAURANT3);
    public static final Dish DISH3 = new Dish(DISH_SEQ3,"Steak",10,RESTAURANT1);
    public static final Dish DISH4 = new Dish(DISH_SEQ4,"Milk Cocktail",1.5,RESTAURANT1);
    public static final Dish DISH5 = new Dish(DISH_SEQ5,"Pancake",2,RESTAURANT1);
}
