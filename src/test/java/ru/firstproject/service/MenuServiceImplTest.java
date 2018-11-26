package ru.firstproject.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.firstproject.model.Menu;
import ru.firstproject.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static ru.firstproject.MenuTestData.*;
import static ru.firstproject.RestaurantTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MenuServiceImplTest {

    @Autowired
    private MenuService menuService;
    
    @Autowired
    private DateLabelService dateLabelService;

    @Test
    public void save() throws Exception {
        Menu menu = new Menu(null,"HotDog","Milk Cocktail");
        menu.setRestaurant(RESTAURANT1);
        menuService.save(menu);
        assertEquals(4,menuService.getAllByDate(new Date()).size());
    }


    @Test
    public void delete() throws Exception {
        menuService.delete(MENU_SEQ1);
        assertMatch(menuService.getAllByDate(new Date()),Arrays.asList(MENU2,MENU3),"registered","restaurant");
    }

    @Test
    public void get() throws Exception {
        Menu menu = menuService.get(MENU_SEQ1);
        assertMatch(MENU1,menu,"registered","restaurant");
    }
    @Test(expected = NotFoundException.class)
    public void getWrongId() throws Exception {
        Menu menu = menuService.get(1);

    }

    @Test
    public void getAllByDate() throws Exception {
        Date date = new Date();
        List<Menu> list = menuService.getAllByDate(date);
        assertMatch(list, Arrays.asList(MENU1,MENU2,MENU3),"restaurant","registered");
        list.stream().forEach(System.out::println);
    }

}