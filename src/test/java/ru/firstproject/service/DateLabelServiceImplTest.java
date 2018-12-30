package ru.firstproject.service;


import org.junit.Test;
import org.springframework.dao.DataAccessException;
import ru.firstproject.AbstractServiceTest;
import static org.junit.Assert.*;



public class DateLabelServiceImplTest extends AbstractServiceTest {

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
        logger.info("1-st query : " + dateLabelService.isPresentToday());
        logger.info("2-nd query? :" + dateLabelService.isPresentToday());
    }

}