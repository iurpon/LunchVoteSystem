package ru.firstproject.repository.datajpa;


import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.firstproject.model.Restaurant;

import java.util.Date;
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

/*    @EntityGraph(attributePaths = {"dishList"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r,Dish d WHERE d.registered=?1  order by r.id asc")*/
    @Query("select DISTINCT r from Restaurant r LEFT join fetch r.dishList d where d.registered=:registered order by r.id asc")
    List<Restaurant> getMenu(@Param("registered") Date registered);

/*    @EntityGraph(attributePaths = {"dishList"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT  r FROM Restaurant r, Dish d WHERE r.id=?1 and d.registered=?2")*/
    @Query("select DISTINCT r from Restaurant r LEFT join fetch r.dishList d where r.id=:id and d.registered=:registered order by r.id asc")
    Restaurant getRestaurantMenu(@Param("id") int id,@Param("registered") Date date);
}
