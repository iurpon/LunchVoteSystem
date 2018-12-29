package ru.firstproject.web.user;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.firstproject.AbstractControllerTest;
import ru.firstproject.MenuTestData;
import ru.firstproject.model.Menu;
import ru.firstproject.model.Restaurant;
import ru.firstproject.util.json.JsonUtil;
import static ru.firstproject.UserTestData.*;


import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.firstproject.RestaurantTestData.*;


public class RestAdminControllerTest  extends AbstractControllerTest{
    private final String REST_ADMIN_URL = "/rest/admin/";

    @Test
    public void updateRestaurant() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT1);
        updated.setName("new updated name");
        mockMvc.perform(put(REST_ADMIN_URL + "restaurants/" + REST_SEQ1)
                        .with(userHttpBasic(ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MenuTestData.assertMatch(restaurantService.get(REST_SEQ1),updated);

    }

    @Test
    public void createRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant(null, "Bussines Center","Chicago, Washington Aveniu" );
        ResultActions resultActions = mockMvc.perform(post(REST_ADMIN_URL + "/restaurants")
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isOk());

        Restaurant justCreated = readFromJson(resultActions,Restaurant.class);
        restaurant.setId(justCreated.getId());
        MenuTestData.assertMatch(restaurantService.get(justCreated.getId()),restaurant);

    }
    @Test
    public void deleteRestaurant() throws Exception {
         mockMvc.perform(delete(REST_ADMIN_URL + "restaurants/" + REST_SEQ1)
                                                       .with(userHttpBasic(ADMIN)))
                                             .andExpect(status().isNoContent());


        MenuTestData.assertMatch(restaurantService.getAll(),Arrays.asList(RESTAURANT2,RESTAURANT3));

    }

    @Test
    public void getRestaurant() throws Exception {
        mockMvc.perform(get(REST_ADMIN_URL + "restaurants/" + REST_SEQ1)
                        .with(userHttpBasic(ADMIN)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(contentJson(RESTAURANT1));

    }


    @Test
    public void getAllRestaurants() throws Exception {
        mockMvc.perform(get(REST_ADMIN_URL + "restaurants")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT1,RESTAURANT2,RESTAURANT3));

    }

    @Test
    public void getAllUsers() throws Exception {
        mockMvc.perform(get(REST_ADMIN_URL + "users").with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Assert.assertEquals(userService.getAll().size(), 2);
        assertMatch(userService.getAll(), ADMIN,USER);
        MenuTestData.assertMatch(userService.getAll(), Arrays.asList(ADMIN,USER),"registered");
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_ADMIN_URL + "users"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetForbidden() throws Exception {
        mockMvc.perform(get(REST_ADMIN_URL + "users")
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getUser() throws Exception {
        mockMvc.perform(get(REST_ADMIN_URL + "users/" + ADMIN_ID).with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN));
    }

    @Test
    public void getUserWrongId() throws Exception {
        mockMvc.perform(get(REST_ADMIN_URL + "users/" + 1).with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void getTodayMenus() throws Exception {
        mockMvc.perform(get(REST_ADMIN_URL + "menus").with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MenuTestData.MENU1,MenuTestData.MENU2));
    }

    @Test
    public void getMenu() throws Exception {
        mockMvc.perform(get(REST_ADMIN_URL + "menus/" + MenuTestData.MENU_SEQ2).with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MenuTestData.MENU2));
    }

    @Test
    public void createMenu() throws Exception {

        Menu expected  = new Menu(MenuTestData.MENU3);
//        expected.setRestaurant(RESTAURANT1);
        ResultActions resultActions = mockMvc.perform(post(REST_ADMIN_URL + "menus").with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isOk());

        Menu returned = JsonUtil.readValue(getContent(resultActions),Menu.class);
        expected.setId(returned.getId());

        MenuTestData.assertMatch(returned,expected,"restaurant");
    }

/*    @Test
    public void deleteMenu() throws Exception {
        mockMvc.perform(delete(REST_ADMIN_URL + "menus/" + MenuTestData.MENU_SEQ2).with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assert.assertEquals(menuService.getAllByDate(new Date()).size(),1);
        MenuTestData.assertMatch(menuService.getAllByDate(new Date()),
                Collections.singletonList(MenuTestData.MENU1),"restaurant","registered");
    }

    @Test
    public void updateMenu() throws Exception {
        Menu updated = new Menu(MenuTestData.MENU1);
//        updated.setDescription1("New Description1");

        mockMvc.perform(put(REST_ADMIN_URL+ "menus/" + MenuTestData.MENU_SEQ1).with(userHttpBasic(ADMIN))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
        MenuTestData.assertMatch(menuService.get(MenuTestData.MENU_SEQ1),updated,"registered","restaurant");
    }*/

    @Test
    public void startVoting() throws Exception {
        mockMvc.perform(post(REST_ADMIN_URL + "startVote").with(userHttpBasic(ADMIN)));
        Assert.assertEquals(dateLabelService.isPresentToday(),true);
    }

    @Test
    public void updateMenuDenied() throws Exception {
        if(dateLabelService.startVoting()){
            logger.info("vote started");
        }

        Menu updated = new Menu(MenuTestData.MENU1);
//        updated.setDescription1("New Description1");

        mockMvc.perform(put(REST_ADMIN_URL+ "menus/" + MenuTestData.MENU_SEQ1).with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isConflict());

    }

    @Test
    public void deleteMenuDenied() throws Exception {
        if(dateLabelService.startVoting()){
            logger.info("vote started");
        }

        mockMvc.perform(delete(REST_ADMIN_URL + "menus/" + MenuTestData.MENU_SEQ2).with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict());

    }

    @Test
    public void startVotingTwiceDenied() throws Exception {
        dateLabelService.startVoting();

        mockMvc.perform(post(REST_ADMIN_URL + "startVote").with(userHttpBasic(ADMIN)))
                        .andExpect(status().isOk());

    }

}