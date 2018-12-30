package ru.firstproject;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.firstproject.service.*;

@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public abstract class AbstractServiceTest {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    protected DateLabelService dateLabelService;

    @Autowired
    protected DishService dishService;

    @Autowired
    protected UserService service;

    @Autowired
    protected VoteService voteService;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        logger.info("clear date_label cache");
        cacheManager.getCache("date_label").clear();
    }
}
