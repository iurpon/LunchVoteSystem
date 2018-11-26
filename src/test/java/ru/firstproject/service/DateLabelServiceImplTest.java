package ru.firstproject.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private DateLabelService dateLabelService;

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

}