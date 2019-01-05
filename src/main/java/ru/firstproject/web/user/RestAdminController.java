package ru.firstproject.web.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.firstproject.AuthorizedUser;
import ru.firstproject.model.Dish;
import ru.firstproject.model.Restaurant;
import ru.firstproject.model.User;
import ru.firstproject.model.Vote;


import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
public class RestAdminController  extends AbstractUserController{
    private Logger logger = LoggerFactory.getLogger(getClass());

//Restaurant
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/rest/admin/restaurants",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllRestaurants(){
        return restaurantService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/rest/admin/restaurants/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getRestaurant(@PathVariable("id") int id ){
        return restaurantService.get(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/rest/admin/restaurants/{id}/menu",produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getRestaurantMenu(@PathVariable("id") int id ){
        return restaurantService.getRestaurantMenu(id,new Date());
    }

    @PostMapping(value = "/rest/admin/restaurants/{id}/menu",produces = MediaType.APPLICATION_JSON_VALUE
                                                            ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createDish(@PathVariable("id") int restId ,@Valid @RequestBody Dish dish){

        Restaurant restaurant = restaurantService.getRestaurantMenu(restId,new Date());
        dish.setRestaurant(restaurant);
        Dish created = dishService.save(dish);
        restaurant.getDishList().add(created);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/admin/restaurants/{id}/menu")
                .buildAndExpand(restId).toUri();
        return ResponseEntity.created(uriOfNewResource).body(restaurant);
    }

    @PutMapping(value = "/rest/admin/restaurants/{id}/menu/{dishId}",produces = MediaType.APPLICATION_JSON_VALUE
            ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> updateDish(@PathVariable("id") int id,@Valid @RequestBody Dish dish,@PathVariable("dishId") int dishId){
        if(dish.getId() != dishId){
            throw new IllegalArgumentException("wrong menuId and dish Id");
        }
        dish.setRestaurant(restaurantService.get(id));
        Dish updated = dishService.save(dish);
        logger.info(updated.toString());
        Restaurant restaurant = restaurantService.getRestaurantMenu(id,new Date());
        return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/rest/admin/restaurants/{id}/menu/{dishId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable("id") int restId, @PathVariable("dishId") int dishId){
        dishService.delete(dishId);
    }

    @GetMapping(value = "/rest/admin/restaurants/menu",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getMenu(){
        return restaurantService.getMenu(new Date());
    }

    @PostMapping(value = "/rest/admin/restaurants",consumes = MediaType.APPLICATION_JSON_VALUE
                                                    ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody Restaurant restaurant){
        Restaurant created = restaurantService.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/admin/restaurants" + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

//User
    @GetMapping(value = "/rest/admin/users",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @GetMapping(value = "/rest/admin/users/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable("id") int id){
        AuthorizedUser.safeGet();
        return userService.get(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody User user){
        User created = userService.create(user);
        URI newUriOfResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/admin/users/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(newUriOfResource).body(created);
    }

//Start Vote
    @PostMapping(value = "/rest/admin/startVote")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public boolean startVoting(){
        return dateLabelService.startVoting();
    }

//Vote Statistics
    @GetMapping(value = "/rest/admin/statistics",produces = MediaType.APPLICATION_JSON_VALUE)
    public Statistics getVoteStatistics(){
        List<Vote> voteList = voteService.getAllByDate(new Date());
        Statistics statistics = new Statistics();
        statistics.setTotalVotes(voteList.size());
        Map<String,Long> restaurantVoteCount = voteList.stream()
                .collect(Collectors.groupingBy(v -> v.getRestaurant().getName(), Collectors.counting()));
        statistics.setRestaurantVotesCount(restaurantVoteCount);
        return statistics;
    }

    public static class Statistics{
        private int totalVotes;
        private Map<String,Long> restaurantVotesCount;

        public int getTotalVotes() {
            return totalVotes;
        }

        public void setTotalVotes(int totalVotes) {
            this.totalVotes = totalVotes;
        }

        public Map<String, Long> getRestaurantVotesCount() {
            return restaurantVotesCount;
        }

        public void setRestaurantVotesCount(Map<String, Long> restaurantVotesCount) {
            this.restaurantVotesCount = restaurantVotesCount;
        }

        @Override
        public String toString() {
            return "Statistics{" +
                    "totalVotes=" + totalVotes +
                    ", restaurantVotesCount=" + restaurantVotesCount +
                    '}';
        }
    }


}

