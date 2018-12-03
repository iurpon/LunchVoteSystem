package ru.firstproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.firstproject.model.Vote;
import ru.firstproject.repository.VoteRepository;
import ru.firstproject.util.exception.NotFoundException;
import ru.firstproject.util.exception.TimesUpException;

import java.time.LocalTime;
import java.util.Date;

import static ru.firstproject.util.ValidationUtil.*;

@Service
public class VoteServiceImpl implements VoteService {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public Vote get(Date date, int userId) throws NotFoundException {
        return checkNotFoundWithId(voteRepository.get(date,userId),userId);
    }

    @Override
    public Vote save(Vote vote,int userId) throws TimesUpException{
        boolean isBefore = LocalTime.now().isBefore(LOCAL_TIME);
        logger.info("Save method. isBefore = " + isBefore);
        if(isBefore){
            return voteRepository.save(vote);
        }else{
            Vote anyVote = voteRepository.get(new Date(),userId);
            if(anyVote == null){
                voteRepository.save(vote);
            }
        }
        throw new TimesUpException("you already voted");
    }
}
