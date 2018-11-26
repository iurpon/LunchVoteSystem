package ru.firstproject.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class DateLabelServiceImplTest {
    private  final Logger LOG = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private DateLabelService dateLabelService;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("date_label").clear();
    }

    @Test
    public void startVoting() throws Exception {
        dateLabelService.startVoting();
        assertEquals(true,dateLabelService.isPresentToday());
    }

    @Test
    public void isPresentToday() throws Exception {
        assertEquals(false,dateLabelService.isPresentToday());
    }

    @Test(expected = DataAccessException.class)
    public void startVotingException(){
        dateLabelService.startVoting();
        assertEquals(false,dateLabelService.startVoting());
    }

    @Test
    public void cacheTest(){
        dateLabelService.startVoting();
        LOG.info("1-st query : " + dateLabelService.isPresentToday());
        LOG.info("2-nd query? :" + dateLabelService.isPresentToday());
    }

}