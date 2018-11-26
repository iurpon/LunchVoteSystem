package ru.firstproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.firstproject.repository.DateLabelRepository;

@Service
public class DateLabelServiceImpl implements  DateLabelService{

    @Autowired
    private DateLabelRepository dateLabelRepository;


    @Override
    public boolean startVoting() {
        return dateLabelRepository.startVoting();
    }

    @Override
    public boolean isPresentToday() {
        return dateLabelRepository.isPresentToday();
    }
}
