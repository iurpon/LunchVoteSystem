package ru.firstproject.web.user;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.firstproject.AbstractControllerTest;
import ru.firstproject.MenuTestData;
import ru.firstproject.TestUtil;
import ru.firstproject.model.Dish;
import ru.firstproject.model.Restaurant;
import ru.firstproject.model.Role;
import ru.firstproject.model.User;
import ru.firstproject.util.exception.ErrorType;
import ru.firstproject.util.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.firstproject.DishTestData.*;
import static ru.firstproject.UserTestData.*;


import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.firstproject.RestaurantTestData.*;


public class RestAdminControllerTest  extends AbstractControllerTest{
    private final String REST_ADMIN_URL = "/rest/admin/";

    @Test
    public void createRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant(null, "Bussines Center","Chicago, Washington Aveniu" );
        ResultActions resultActions = mockMvc.perform(post(REST_ADMIN_URL + "/restaurants")
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isCreated());

        Restaurant justCreated = readFromJson(resultActions,Restaurant.class);
        restaurant.setId(justCreated.getId());
        MenuTestData.assertMatch(restaurantService.get(justCreated.getId()),restaurant);
    }

    @Test
    public void createRestaurantNotValid() throws Exception {
        Restaurant restaurant = new Restaurant(null, "","Chicago, Washington Aveniu" );
         mockMvc.perform(post(REST_ADMIN_URL + "/restaurants")
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name()))
                .andDo(print());

    }

    @Test
    public void getRestaurant() throws Exception {
        ResultActions resultActions = mockMvc.perform(get(REST_ADMIN_URL + "restaurants/" + REST_SEQ2)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT2));
        Restaurant restaurant = readFromJson(resultActions,Restaurant.class);
        logger.info("RESULT ACTIONS RESTAURANT : " + restaurant.toString());
    }

    @Test
    public void getRestaurantMenu() throws Exception {
        ResultActions resultActions = mockMvc.perform(get(REST_ADMIN_URL + "restaurants/" + REST_SEQ1 + "/menu")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT1));
            Restaurant restaurant = readFromJson(resultActions,Restaurant.class);
            logger.info("result actions restaurant : " + restaurant.toString());
            Set<Dish> dishList = restaurant.getDishList();
            dishList.forEach(dish -> logger.info("DishList : " + dish.toString()));
            Assert.assertEquals(dishList.size(),3);
    }

    @Test
    public void getMenu() throws Exception {
        ResultActions resultActions = mockMvc.perform(get(REST_ADMIN_URL + "restaurants/menu")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT1,RESTAURANT3));
        List<Restaurant> restaurantList = readMultiFromJson(resultActions,Restaurant.class);
        Assert.assertEquals(restaurantList.size(),2);
        Assert.assertEquals(restaurantList.get(0).getDishList().size() + restaurantList.get(1).getDishList().size(),5);
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
    public void createDish() throws Exception{
        Dish created = new Dish(null,"too spicy",5.3);
        created.setRestaurant(RESTAURANT3);
        ResultActions resultActions = mockMvc.perform(post(REST_ADMIN_URL + "restaurants/" + REST_SEQ3 + "/menu")
                                            .with(userHttpBasic(ADMIN))
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(JsonUtil.writeValue(created)))
                                            .andExpect(status().isCreated());
         Restaurant returned = TestUtil.readFromJson(resultActions,Restaurant.class);
         MenuTestData.assertMatch(returned,RESTAURANT3,"dishList");
        returned.getDishList().forEach(dish -> logger.info("Added Dish : " + dish.toString()));
         Assert.assertEquals(returned.getDishList().size(),3);
    }

    @Test
    public void createDishDenied() throws Exception{
        dateLabelService.startVoting();
        Dish created = new Dish(null,"too spicy",5.3);
        created.setRestaurant(RESTAURANT3);
        mockMvc.perform(post(REST_ADMIN_URL + "restaurants/" + REST_SEQ3 + "/menu")
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isConflict());
    }

    @Test
    public void updateDish() throws Exception{
        Dish created = new Dish(DISH3);
        created.setName("Muck bigger steak");

        ResultActions resultActions = mockMvc.perform(put(REST_ADMIN_URL + "restaurants/" + REST_SEQ1 + "/menu/" + DISH_SEQ3)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isOk());
        Restaurant returned = TestUtil.readFromJson(resultActions,Restaurant.class);
        MenuTestData.assertMatch(returned,RESTAURANT1,"dishList");
        returned.getDishList().forEach(dish -> logger.info("Added Dish : " + dish.toString()));
        Assert.assertEquals(returned.getDishList().size(),3);
    }

    @Test
    public void deleteDish() throws Exception{
        mockMvc.perform(delete(REST_ADMIN_URL + "restaurants/" + REST_SEQ1 + "/menu/" + DISH_SEQ3)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        Restaurant returned = restaurantService.getRestaurantMenu(REST_SEQ1,new Date());
        returned.getDishList().forEach(dish -> logger.info("Added Dish : " + dish.toString()));
        Assert.assertEquals(returned.getDishList().size(),2);
    }

    @Test
    public void deleteDishNoId() throws Exception{
        mockMvc.perform(delete(REST_ADMIN_URL + "restaurants/" + REST_SEQ1 + "/menu/" + (DISH_SEQ3-100))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());

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
    public void startVoting() throws Exception {
        mockMvc.perform(post(REST_ADMIN_URL + "startVote").with(userHttpBasic(ADMIN)));
        Assert.assertEquals(dateLabelService.isPresentToday(),true);
    }

    @Test
    public void startVotingTwiceDenied() throws Exception {
        dateLabelService.startVoting();
        mockMvc.perform(post(REST_ADMIN_URL + "startVote").with(userHttpBasic(ADMIN)))
                        .andExpect(status().isConflict());

    }

    @Test
    public void getStatistics() throws Exception{
        ResultActions resultActions = mockMvc.perform(get(REST_ADMIN_URL + "statistics").with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());

        RestAdminController.Statistics stats = TestUtil.readFromJson(resultActions,RestAdminController.Statistics.class);
        logger.info("Result actions to string : " + stats.toString());
    }

    @Test
    public void createTest() throws Exception {
        User created = new User(null,"newUser","newUser@mail.ru","newPassword", Role.ROLE_USER);
        ResultActions resultActions = mockMvc.perform(post("/rest/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(created)))
                .andDo(print())
                .andExpect(status().isCreated());
        User returned = TestUtil.readFromJson(resultActions,User.class);
        created.setId(returned.getId());
        assertMatch(created, returned);
    }

}