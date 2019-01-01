package ru.firstproject.web.user;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.firstproject.AbstractControllerTest;
import ru.firstproject.TestUtil;
import ru.firstproject.model.Role;
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
    public void createTest() throws Exception {
        User created = new User(null,"newUser","newUser@mail.ru","newPassword", Role.ROLE_USER);
        ResultActions resultActions = mockMvc.perform(post(REST_USER_URL)
                                             .contentType(MediaType.APPLICATION_JSON)
                                            .with(userHttpBasic(USER))
                                            .content(JsonUtil.writeValue(created)))
                                            .andDo(print())
                                            .andExpect(status().isCreated());
        User returned = TestUtil.readFromJson(resultActions,User.class);
        created.setId(returned.getId());
        assertMatch(created, returned);
    }



    @Test
    public void createVote() throws Exception {
         ValidationUtil.TIME_TO_CHANGE_MIND = LocalTime.now().plusMinutes(3);

         ResultActions resultActions = mockMvc.perform(post(REST_USER_URL +  "/vote")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .with(userHttpBasic(USER))
                                                .content(JsonUtil.writeValue(RESTAURANT1)))
                                        .andDo(print())
                                        .andExpect(status().isOk());
         Vote returned = TestUtil.readFromJson(resultActions,Vote.class);
         logger.info(returned.toString());
        List<Vote> votes = voteService.getAllByDate(new Date());
        Assert.assertEquals(votes.size(),2);
    }



    @Test
    public void updateVoteTimesUp() throws Exception {
        ValidationUtil.TIME_TO_CHANGE_MIND = LocalTime.now().minusMinutes(3);
        mockMvc.perform(post(REST_USER_URL + "/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(RESTAURANT1)))
                .andDo(print())
                .andExpect(status().isConflict());
    }
}