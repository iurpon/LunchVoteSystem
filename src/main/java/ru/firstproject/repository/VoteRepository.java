package ru.firstproject.repository;

import ru.firstproject.model.Vote;

import java.util.Date;
import java.util.List;

public interface VoteRepository {

    Vote get(Date date , int userId);

    Vote save(Vote vote);

}
