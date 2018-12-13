package ru.firstproject.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.firstproject.model.Menu;
import ru.firstproject.model.Restaurant;
import ru.firstproject.model.User;


import java.util.Date;
import java.util.List;


@RestController
public class RestAdminController  extends AbstractUserController{

//Restaurant
    @GetMapping(value = "/rest/admin/restaurants",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllRestaurants(){
        return restaurantService.getAll();
    }

    @GetMapping(value = "/rest/admin/restaurants/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getRestaurant(@PathVariable("id") int id ){
        return restaurantService.get(id);
    }

    @DeleteMapping(value = "/rest/admin/restaurants/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable("id") int id) {
        restaurantService.delete(id);
    }

    @PutMapping(value = "/rest/admin/restaurants/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateRestaurant(@RequestBody Restaurant restaurant,@PathVariable("id") int id){
        restaurantService.update(restaurant,id);
    }

    @PostMapping(value = "/rest/admin/restaurants",consumes = MediaType.APPLICATION_JSON_VALUE
                                                    ,produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant){
        return restaurantService.create(restaurant);
    }

//User
    @GetMapping(value = "/rest/admin/users",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @GetMapping(value = "/rest/admin/users/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable("id") int id){
        return userService.get(id);
    }



//Menu
    @GetMapping(value = "/rest/admin/menus",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getTodayMenus(){
        return menuService.getAllByDate(new Date());
    }

    @GetMapping(value = "/rest/admin/menus/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu getMenu(@PathVariable("id") int id){
        return menuService.get(id);
    }

    @PostMapping(value = "/rest/admin/menus",produces = MediaType.APPLICATION_JSON_VALUE
            ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Menu createMenu(@RequestBody Menu menu){
        return menuService.save(menu);
    }

    @DeleteMapping(value = "/rest/admin/menus/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMenu(@PathVariable("id") int id){
        menuService.delete(id);
    }

    @PutMapping(value = "/rest/admin/menus/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateMenu(@RequestBody Menu menu, @PathVariable("id") int id){
        menuService.update(menu,id);
    }

//Start Vote
    @PostMapping(value = "/rest/admin/startVote",produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean startVoting(){
        return dateLabelService.startVoting();
    }

/*//Vote
    @GetMapping(value = "/rest/admin/votes/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote getUserTodayVote(@PathVariable("id") int id){
        return voteService.get(new Date(),id);
    }*/


}

