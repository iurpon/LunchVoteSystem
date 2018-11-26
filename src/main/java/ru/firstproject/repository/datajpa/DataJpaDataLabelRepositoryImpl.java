package ru.firstproject.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.firstproject.model.DateLabel;
import ru.firstproject.repository.DateLabelRepository;

import java.util.Date;

@Repository
public class DataJpaDataLabelRepositoryImpl implements DateLabelRepository {

    @Autowired
    private CrudDateLabelRepository crudDateLabelRepository;

    @Override
    public boolean startVoting() {
        return crudDateLabelRepository.save(new DateLabel()) != null;
    }

    @Override
    public boolean isPresentToday() {
        return crudDateLabelRepository.getByRegistered(new Date()) != null;
    }
}
