package ru.firstproject.web.user;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.firstproject.AbstractControllerTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.firstproject.UserTestData.ADMIN_ID;

public class RestUserControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestUserController.REST_URL + "/";

    @Test
    public void getTest() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void create() throws Exception {
    }

    @Test
    public void createVote() throws Exception {
    }

    @Test
    public void updateVote() throws Exception {
    }

}