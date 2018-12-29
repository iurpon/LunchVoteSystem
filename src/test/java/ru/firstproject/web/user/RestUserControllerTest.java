package ru.firstproject.web.user;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import ru.firstproject.AbstractControllerTest;
import ru.firstproject.MenuTestData;
import ru.firstproject.model.Menu;
import ru.firstproject.model.User;
import ru.firstproject.model.Vote;
import ru.firstproject.util.ValidationUtil;
import ru.firstproject.util.json.JsonUtil;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.firstproject.UserTestData.*;
import static ru.firstproject.RestaurantTestData.*;

public class RestUserControllerTest extends AbstractControllerTest {

    private static final String REST_USER_URL = RestUserController.REST_USER_URL;

    @Test
    public void getTest() throws Exception {
        mockMvc.perform(get(REST_USER_URL + "/" +  ADMIN_ID).with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN));
    }

    @Test
    public void updateTest() throws Exception {
        User updated  = new User(USER);
        updated.setName("NewUser");
        mockMvc.perform(put(REST_USER_URL + "/" + USER_ID ).with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isOk());
        assertMatch(userService.get(USER_ID),updated);

    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete(REST_USER_URL + "/"+ USER_ID).with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(),ADMIN);
    }



    @Test
    public void createVote() throws Exception {
        Menu newMenu = new Menu(MenuTestData.MENU1);
//        newMenu.setRestaurant(RESTAURANT1);


         mockMvc.perform(post(REST_USER_URL +  "/vote")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .with(userHttpBasic(USER))
                                                .content(JsonUtil.writeValue(newMenu)))
                                        .andDo(print())
                                        .andExpect(status().isOk());
        List<Vote> votes = voteService.getAllByDate(new Date());
        votes.stream().forEach(System.out::println);
        Assert.assertEquals(votes.size(),2);
    }



    @Test
    public void updateVoteTimesUp() throws Exception {
//        Vote existing = new Vote(VOTE1);
        Menu existing = new Menu(MenuTestData.MENU1);
//        existing.setRestaurant(RESTAURANT2);
        LocalTime localTime = LocalTime.now();
        localTime = localTime.minusHours(1);
        ValidationUtil.setLocalTime(localTime);


        mockMvc.perform(post(REST_USER_URL + "/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(existing)))
                .andDo(print())
                .andExpect(status().isConflict());

        ValidationUtil.TIME_TO_CHANGE_MIND = LocalTime.of(11,0);
    }

}