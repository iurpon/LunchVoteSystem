package ru.firstproject.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.firstproject.UserTestData;
import ru.firstproject.model.Role;
import ru.firstproject.model.User;
import ru.firstproject.util.exception.NotFoundException;

import java.util.List;


@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))

public class UserServiceImplTest {

    @Autowired
    private UserService service;

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
        newUser = service.create(newUser);
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
        User user = service.getByEmail("users@yandex.ru");
    }

    @Test
    public void getAll() throws Exception {
        List<User> all = service.getAll();
        all.stream().forEach(System.out::println);
    }

    @Test
    public void update(){
        User user = new User(UserTestData.USER);
        user.setName("newUser");
        service.update(user);
        User updatedUser = service.get(UserTestData.USER_ID);
        UserTestData.assertMatch(updatedUser,user);
    }

}