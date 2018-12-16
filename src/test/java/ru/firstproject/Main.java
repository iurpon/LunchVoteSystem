package ru.firstproject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.firstproject.model.Role;
import ru.firstproject.model.User;
import ru.firstproject.util.json.JsonUtil;

import java.io.IOException;
import java.util.stream.Stream;
import static ru.firstproject.UserTestData.*;

public class Main {
    public static void main(String[] args) {
        // java 7 Automatic resource management
/*        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml"
                                                                                                    ,"spring/spring-db.xml")) {
            Stream.of(appCtx.getBeanDefinitionNames()).forEach(System.out::println);

        }*/
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonADMIN = objectMapper.writeValueAsString(ADMIN);
            System.out.println(jsonADMIN);

            User created = new User(null,"New","new@gmail.com","nopass", Role.ROLE_USER,Role.ROLE_ADMIN);
            System.out.println(objectMapper.writeValueAsString(created));
            System.out.println(JsonUtil.writeValue(created));

            User admin = objectMapper.readValue(jsonADMIN,User.class);
            System.out.println(admin);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
