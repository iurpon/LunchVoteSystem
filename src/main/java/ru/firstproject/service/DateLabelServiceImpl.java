package ru.firstproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.firstproject.repository.DateLabelRepository;

@Service
public class DateLabelServiceImpl implements  DateLabelService{

    @Autowired
    private DateLabelRepository dateLabelRepository;


    @Override
    @CacheEvict(value = "date_label", allEntries = true)
    public boolean startVoting() {
        return dateLabelRepository.startVoting();
    }

    @Override
    @Cacheable("date_label")
    public boolean isPresentToday() {
        return dateLabelRepository.isPresentToday();
    }
}
