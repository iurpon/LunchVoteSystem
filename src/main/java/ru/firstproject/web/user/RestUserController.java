package ru.firstproject.web.user;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.firstproject.AuthorizedUser;
import ru.firstproject.model.Restaurant;
import ru.firstproject.model.User;
import org.springframework.http.MediaType;
import ru.firstproject.model.Vote;

import java.net.URI;
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
    public void update(@RequestBody User user,@PathVariable("id") int id){
        userService.update(user,id);
    }


    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id){
        userService.delete(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@RequestBody User user){
        User created = userService.create(user);
        URI newUriOfResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path(REST_USER_URL + "/id")
                                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(newUriOfResource).body(created);


    }

    @PostMapping(value = "/vote",consumes = MediaType.APPLICATION_JSON_VALUE
                                        ,produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote createVote(@RequestBody Restaurant restaurant){
        Vote created = new Vote();
        created.setRestaurant(restaurant);
        User user = AuthorizedUser.get().getUser();
        created.setUser(user);
        return voteService.save(created,user.getId());
    }


    @GetMapping(value = "/statistics",produces = MediaType.APPLICATION_JSON_VALUE)
    public String statistics(){
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
        return String.format("Statistics : 1) Total votes : %d.\n" +
                                          "2) %s\n" +
                                          "3) Your choise : %s\n",totalVotes,collect.toString(),choise[0]);
    }








    @GetMapping(value = "/text",produces = MediaType.APPLICATION_JSON_VALUE)
    public String testUTF(@PathVariable("id") int id) {
        log.info("GET mapping to /text");
        return String.format("Русский текст");
    }


/*    @GetMapping(value = "/{id}/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getTodayMenu(@PathVariable("id") int id) {
        List<Menu> allByDate = menuService.getAllByDate(new Date());
        if(allByDate.isEmpty()){
            throw new MenuNotReadyException("Menu not ready. Try again later");
        }
        return allByDate;
    }*/
}
