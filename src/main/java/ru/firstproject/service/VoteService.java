package ru.firstproject.service;

import ru.firstproject.model.Vote;
import ru.firstproject.util.exception.NotFoundException;
import ru.firstproject.util.exception.TimesUpException;

import java.util.Date;
import java.util.List;

public interface VoteService {

    Vote get(Date date , int userId) throws NotFoundException;

    Vote save(Vote vote,int userId) throws TimesUpException;

    List<Vote> getAllByDate(Date date);
}
