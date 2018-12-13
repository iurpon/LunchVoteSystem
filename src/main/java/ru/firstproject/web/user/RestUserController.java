package ru.firstproject.web.user;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.firstproject.model.Menu;
import ru.firstproject.model.User;
import org.springframework.http.MediaType;
import ru.firstproject.model.Vote;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping(RestUserController.REST_URL)
public class RestUserController  extends AbstractUserController{
    static final String REST_URL = "/rest/users";

    @Override
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
    public User create(@RequestBody User user){
        return userService.create(user);
    }

    @PostMapping(value = "/id/vote",consumes = MediaType.APPLICATION_JSON_VALUE
                                        ,produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote createVote(@PathVariable("id") int id,@RequestBody Menu menu){
        Vote vote = new Vote();
        vote.setUser(super.get(id));
        vote.setRestaurant(menu.getRestaurant());
        return voteService.save(vote,id);
    }

    @PutMapping(value = "/id/vote",consumes = MediaType.APPLICATION_JSON_VALUE)
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
}