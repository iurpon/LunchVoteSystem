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
import java.util.List;

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
        if(TIME_TO_CHANGE_MIND == null){
            setLocalTime(LocalTime.of(11,0));
        }
        boolean isBefore = LocalTime.now().isBefore(TIME_TO_CHANGE_MIND);
        logger.info("Save method voteService. is time to revote? = " + isBefore);
        Vote anyVote = voteRepository.get(new Date(),userId);
        if(anyVote == null){
            return voteRepository.save(vote);
        }else{
            vote.setId(anyVote.getId());
            if(isBefore){
                return voteRepository.save(vote);
            }else{
                throw new TimesUpException("you already voted");
            }
        }
    }

    @Override
    public List<Vote> getAllByDate(Date date) {
        return voteRepository.getAllByDate(date);
    }
}
