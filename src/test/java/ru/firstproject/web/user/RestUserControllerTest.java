package ru.firstproject.web.user;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.firstproject.AbstractControllerTest;
import ru.firstproject.MenuTestData;
import ru.firstproject.model.Restaurant;
import ru.firstproject.model.Role;
import ru.firstproject.model.User;
import ru.firstproject.model.Vote;
import ru.firstproject.util.json.JsonUtil;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.firstproject.UserTestData.*;
import static ru.firstproject.RestaurantTestData.*;
import static ru.firstproject.VoteTestData.VOTE1;

public class RestUserControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestUserController.REST_URL;

    @Test
    public void getTest() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + ADMIN_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN));
    }

    @Test
    public void updateTest() throws Exception {
        User updated  = new User(USER);
        updated.setName("NewUser");
        mockMvc.perform(put(REST_URL +  "/" + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isOk());
        assertMatch(userService.get(USER_ID),updated);

    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete(REST_URL + "/"+ USER_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(),ADMIN);
    }

    @Test
    public void createTest() throws Exception {
        User expected = new User(null, "NewUser", "newUser@gmail.com", "newpassword", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions resultActions = mockMvc.perform(post(REST_URL)
                .content(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isCreated());
/*        logger.info("print ResultAction : ");
        UserTestData.print(resultActions);

        User user = UserTestData.readFromJson(resultActions,User.class);
        created.setId(user.getId());

//        assertMatch(created,user);*/
        Assert.assertEquals(userService.getAll().size(),3);

    }

    @Test
    public void createVote() throws Exception {
        Vote newVote = new Vote();
        Restaurant restaurant = restaurantService.get(RESTAURANT_SEQ3);
        User user = new User(USER);
        newVote.setRestaurant(restaurant);
        newVote.setUser(user);

        ResultActions resultActions = mockMvc.perform(post(REST_URL + "/" + USER_ID + "/vote")
                                                .content(MediaType.APPLICATION_JSON_VALUE)
                                                .content(JsonUtil.writeValue(newVote)))
                                        .andDo(print())
                                        .andExpect(status().isCreated());
    }

    @Test
    public void updateVote() throws Exception {
        Vote existing = new Vote(VOTE1);
        existing.setRestaurant(RESTAURANT3);

        mockMvc.perform(put(REST_URL +  "/" + ADMIN_ID + "/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(existing)))
                .andDo(print())
                .andExpect(status().isOk());
        MenuTestData.assertMatch(voteService.get(new Date(),ADMIN_ID),existing,"registered","user");
    }

}