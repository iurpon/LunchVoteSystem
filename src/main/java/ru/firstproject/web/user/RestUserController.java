package ru.firstproject.web.user;


import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;


import ru.firstproject.AuthorizedUser;
import ru.firstproject.model.Restaurant;
import ru.firstproject.model.User;
import org.springframework.http.MediaType;
import ru.firstproject.model.Vote;
import ru.firstproject.util.ValidationUtil;
import ru.firstproject.util.exception.MenuNotReadyException;

import javax.validation.Valid;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping(RestUserController.REST_USER_URL)
public class RestUserController  extends AbstractUserController{
    static final String REST_USER_URL = "/rest/users";


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") int id) {
        return userService.get(id);
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody User user, @PathVariable("id") int id){
        userService.update(user,id);
    }


    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id){
        User user = AuthorizedUser.get().getUser();
        ValidationUtil.checkCorrectId(user,id);
        userService.delete(id);
    }



    @PostMapping(value = "/vote",consumes = MediaType.APPLICATION_JSON_VALUE
                                        ,produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote createVote(@Valid @RequestBody Restaurant restaurant){
        Vote created = new Vote();
        created.setRestaurant(restaurant);
        User user = AuthorizedUser.get().getUser();
        created.setUser(user);
        return voteService.save(created,user.getId());
    }

    @GetMapping(value = "/menu",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getMenu(){
        List<Restaurant> menu = restaurantService.getMenu(new Date());
        if(menu.isEmpty()){
            throw new MenuNotReadyException("menu not ready yet. please try again later.");
        }
        return menu;
    }


    @GetMapping(value = "/statistics",produces = MediaType.APPLICATION_JSON_VALUE)
    public UserStatistics statistics(){
        int id = AuthorizedUser.id();
        List<Vote> votes = voteService.getAllByDate(new Date());
        int totalVotes = votes.size();
        String[] choise = new String[1];
        Map<String, Long> collect = votes.stream()
                .filter(v -> {
                    if(v.getUser().getId() == id){
                        choise[0] = v.getRestaurant().getName();
                    }
                    return true;})
                .collect(Collectors.groupingBy(v -> v.getRestaurant().getName(), Collectors.counting()));
        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setTotalVotes(totalVotes);
        userStatistics.setRestaurantVotesCount(collect);
        userStatistics.setUserChoise(choise[0]);
        return userStatistics;
    }
    public static class UserStatistics{
        private int totalVotes;
        private Map<String,Long> restaurantVotesCount;
        private String userChoise;

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

        public String getUserChoise() {
            return userChoise;
        }

        public void setUserChoise(String userChoise) {
            this.userChoise = userChoise;
        }

        @Override
        public String toString() {
            return "Statistics{" +
                    "totalVotes=" + totalVotes +
                    ", restaurantVotesCount=" + restaurantVotesCount +
                    ", yourChoise='" + userChoise + '\'' +
                    '}';
        }
    }
}
