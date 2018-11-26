package ru.firstproject.repository.datajpa;


import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.firstproject.model.Restaurant;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {


    @Override
    List<Restaurant> findAll(Sort sort);

    @Override
    @Transactional
    Restaurant save(Restaurant entity);

    @Override
    Optional<Restaurant> findById(Integer integer);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Restaurant r where r.id = ?1")
    int delete(Integer integer);


    @Override
    @Transactional
    void delete(Restaurant entity);

    Restaurant findByName(String name);
}
