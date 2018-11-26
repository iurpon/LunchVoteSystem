package ru.firstproject.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.firstproject.model.Menu;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu,Integer>{
    @Transactional
    @Override
    Menu save(Menu entity);

    @Override
    Optional<Menu> findById(Integer integer);

    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id = ?1")
    @Transactional
    int  delete(Integer integer);

    List<Menu> findAllByRegisteredOrderByIdAsc(Date registered);
}
