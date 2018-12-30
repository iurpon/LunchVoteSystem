package ru.firstproject.service;

import org.junit.Test;
import ru.firstproject.AbstractServiceTest;
import ru.firstproject.model.Vote;
import ru.firstproject.util.exception.TimesUpException;

import java.time.LocalTime;
import java.util.Date;


import static ru.firstproject.UserTestData.*;
import static ru.firstproject.MenuTestData.*;
import static ru.firstproject.VoteTestData.*;
import static ru.firstproject.RestaurantTestData.*;
import static ru.firstproject.util.ValidationUtil.TIME_TO_CHANGE_MIND;


public class VoteServiceImplTest  extends AbstractServiceTest{



    @Test
    public void get() throws Exception {
        Vote vote = voteService.get(new Date(),ADMIN_ID);
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
        TIME_TO_CHANGE_MIND = LocalTime.now().minusHours(1);
        voteService.save(VOTE1,ADMIN_ID);
    }

}