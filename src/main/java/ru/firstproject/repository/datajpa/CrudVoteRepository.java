package ru.firstproject.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.firstproject.model.Vote;

import java.util.Date;
import java.util.Optional;

public interface CrudVoteRepository extends JpaRepository<Vote,Integer> {
    @Override

    Vote save(Vote entity);


    @Query("SELECT v FROM Vote v WHERE v.registered = ?1 AND v.user.id = ?2")
    Optional<Vote> find(Date date, Integer userId);
}
