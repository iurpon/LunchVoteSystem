package ru.firstproject.web.user;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.firstproject.model.Menu;
import ru.firstproject.model.User;
import org.springframework.http.MediaType;
import ru.firstproject.model.Vote;
import ru.firstproject.util.exception.MenuNotReadyException;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping(RestUserController.REST_USER_URL)
public class RestUserController  extends AbstractUserController{
    static final String REST_USER_URL = "/rest/users";


    @GetMapping(value = "/{id}/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") int id,@PathVariable("userId") int userId) {
        return userService.get(userId);
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

    @PostMapping(value = "/{id}/vote",consumes = MediaType.APPLICATION_JSON_VALUE
                                        ,produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote createVote(@PathVariable("id") int id,@RequestBody Menu menu){
        Vote vote = new Vote();
        vote.setUser(userService.get(id));
        vote.setRestaurant(menu.getRestaurant());
        return voteService.save(vote,id);
    }

    @PutMapping(value = "/{id}/vote",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateVote(@PathVariable("id") int id,@RequestBody Vote vote){
        voteService.save(vote,id);
    }

    @GetMapping(value = "/{id}/statistics",produces = MediaType.APPLICATION_JSON_VALUE)
    public String statistics(@PathVariable("id") int id){
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


    @GetMapping(value = "/{id}/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getTodayMenu(@PathVariable("id") int id) {
        List<Menu> allByDate = menuService.getAllByDate(new Date());
        if(allByDate.isEmpty()){
            throw new MenuNotReadyException("Menu not ready. Try again later");
        }
        return allByDate;
    }
}
