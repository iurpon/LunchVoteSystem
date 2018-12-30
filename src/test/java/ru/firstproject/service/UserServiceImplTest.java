package ru.firstproject.service;

import org.junit.Test;
import org.springframework.dao.DataAccessException;
import ru.firstproject.AbstractServiceTest;
import ru.firstproject.UserTestData;
import ru.firstproject.model.Role;
import ru.firstproject.model.User;
import ru.firstproject.util.exception.NotFoundException;

import java.util.List;



public class UserServiceImplTest extends AbstractServiceTest {

    @Test
    public void save() throws Exception {
        User newUser = new User(null,"NewUser","newUser@mail.ru","password", Role.ROLE_USER);
        newUser = service.create(newUser);
        List<User> users = service.getAll();
        UserTestData.assertMatch(users,UserTestData.ADMIN,newUser,UserTestData.USER);
        users.stream().forEach(System.out::println);
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
        service.delete(UserTestData.USER.getId());
        List<User> users = service.getAll();
        UserTestData.assertMatch(users,UserTestData.ADMIN);
        users.stream().forEach(System.out::println);
    }
    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(0);
        List<User> users = service.getAll();
        users.stream().forEach(System.out::println);
    }

    @Test
    public void get() throws Exception {
        User user =  service.get(UserTestData.USER_ID);
        UserTestData.assertMatch(UserTestData.USER,user);
    }

    @Test
    public void getByEmail() throws Exception {
        User user = service.getByEmail("user@yandex.ru");
        UserTestData.assertMatch(UserTestData.USER,user);
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
        User user = new User(UserTestData.USER);
        user.setName("newUser");
        service.update(user,UserTestData.USER_ID);
        User updatedUser = service.get(UserTestData.USER_ID);
        UserTestData.assertMatch(updatedUser,user);
    }

}