package ru.firstproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.firstproject.repository.DateLabelRepository;
import ru.firstproject.util.exception.VoteAlreadyStarted;

@Service
public class DateLabelServiceImpl implements  DateLabelService{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DateLabelRepository dateLabelRepository;


    @Override
    @CacheEvict(value = "date_label", allEntries = true)
    public boolean startVoting() {
        logger.info("start voting");
        if(isPresentToday()){
            throw new VoteAlreadyStarted("Vote already started");
        }
        return dateLabelRepository.startVoting();
    }

    @Override
    @Cacheable("date_label")
    public boolean isPresentToday() {
        logger.info("check if label present today");
        return dateLabelRepository.isPresentToday();
    }
}
