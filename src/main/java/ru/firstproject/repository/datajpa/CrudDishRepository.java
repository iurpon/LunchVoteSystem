package ru.firstproject.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.firstproject.model.Dish;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
public interface CrudDishRepository extends JpaRepository<Dish,Integer> {
    @Transactional
    @Override
    Dish save(Dish entity);

    @Override
    Optional<Dish> findById(Integer integer);

    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id = ?1")
    @Transactional
    int  delete(Integer integer);

    List<Dish> findAllByRegisteredAndRestaurant_IdOrderByIdAsc(Date date, Integer restId);
}
