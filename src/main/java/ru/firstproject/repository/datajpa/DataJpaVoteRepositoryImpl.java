package ru.firstproject.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.firstproject.model.Vote;
import ru.firstproject.repository.VoteRepository;

import java.util.Date;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DataJpaVoteRepositoryImpl  implements VoteRepository{

    @Autowired
    private CrudVoteRepository crudVoteRepository;

    @Override
    public Vote get(Date date, int userId) {
        return crudVoteRepository.find(date,userId).orElse(null);
    }

    @Override
    @Transactional
    public Vote save(Vote vote) {
        return crudVoteRepository.save(vote);
    }

    public List<Vote> getAllByDate(Date date){
        return crudVoteRepository.getAllByRegistered(date);
    }
}
