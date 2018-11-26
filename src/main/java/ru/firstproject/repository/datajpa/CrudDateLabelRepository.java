package ru.firstproject.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.firstproject.model.DateLabel;

import java.util.Date;

@Transactional(readOnly = true)
public interface CrudDateLabelRepository extends JpaRepository<DateLabel,Integer> {

    @Transactional
    @Override
    DateLabel save(DateLabel entity);

    DateLabel getByRegistered(Date date);
}
