package ru.firstproject.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.firstproject.model.Vote;
import ru.firstproject.util.exception.TimesUpException;

import java.util.Date;

import static org.junit.Assert.*;
import static ru.firstproject.UserTestData.*;
import static ru.firstproject.MenuTestData.*;
import static ru.firstproject.VoteTestData.*;
import static ru.firstproject.RestaurantTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class VoteServiceImplTest {

    @Autowired
    private VoteService voteService;

    @Test
    public void get() throws Exception {
        Vote vote = voteService.get(new Date(),ADMIN_ID);
        System.out.println(vote);
        System.out.println(VOTE1);
        assertMatch(vote,VOTE1,"registered","user","restaurant");
    }

    @Test
    public void save() throws Exception {
        Vote vote = new Vote();
        vote.setRestaurant(RESTAURANT1);
        vote.setUser(USER);
        voteService.save(vote,USER_ID);
    }

    @Test(expected = TimesUpException.class)
    public void saveDenied() throws Exception {
        voteService.save(VOTE1,ADMIN_ID);
    }

}