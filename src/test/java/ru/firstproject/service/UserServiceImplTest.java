package ru.firstproject.service;

import org.junit.Test;
import org.springframework.dao.DataAccessException;
import ru.firstproject.AbstractServiceTest;
import ru.firstproject.UserTestData;
import ru.firstproject.model.Role;
import ru.firstproject.model.User;
import ru.firstproject.util.exception.NotFoundException;

import java.util.List;

import static ru.firstproject.UserTestData.*;



public class UserServiceImplTest extends AbstractServiceTest {

    @Test
    public void save() throws Exception {
        User newUser = new User(null,"NewUser","newUser@mail.ru","password", Role.ROLE_USER);
        newUser = service.create(newUser);
        List<User> users = service.getAll();
        UserTestData.assertMatch(users, ADMIN,newUser, USER);
        users.forEach(user -> logger.info(user.toString()));
    }
    @Test(expected = DataAccessException.class)
    public void saveExitingEmail() throws Exception {
        User newUser = new User(null,"newUser","newUser@mail.ru","password", Role.ROLE_USER);
        service.create(newUser);
        User someUser = new User(null,"someUser","newUser@mail.ru","password", Role.ROLE_USER);
        service.create(someUser);

    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_ID);
        List<User> users = service.getAll();
        UserTestData.assertMatch(users, ADMIN);
        users.forEach(user -> logger.info(user.toString()));
    }
    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(USER_ID -100);
        List<User> users = service.getAll();
        users.forEach(user -> logger.info(user.toString()));
    }

    @Test
    public void get() throws Exception {
        User user =  service.get(USER_ID);
        assertMatch(USER,user);
    }

    @Test
    public void getByEmail() throws Exception {
        User user = service.getByEmail("user@yandex.ru");
        assertMatch(USER,user);
    }

    @Test(expected = NotFoundException.class)
    public void getByWrongEmail() throws Exception {
        String email = "users@yandex.ru";
        service.getByEmail(email);
    }

    @Test
    public void getAll() throws Exception {
        List<User> all = service.getAll();
        all.stream().forEach(user -> logger.info(user.toString()));
    }

    @Test
    public void update(){
        User user = new User(USER);
        user.setName("newUser");
        service.update(user, USER_ID);
        User updatedUser = service.get(USER_ID);
        assertMatch(updatedUser,user);
    }

}