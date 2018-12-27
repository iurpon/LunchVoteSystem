package ru.firstproject.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.firstproject.model.Menu;
import ru.firstproject.repository.MenuRepository;

import java.util.Date;
import java.util.List;
import static ru.firstproject.util.ValidationUtil.*;

@Repository
public class DataJpaMenuRepositoryImpl implements MenuRepository {

    @Autowired
    private CrudMenuRepository menuRepository;

    @Override
    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public Menu get(int id) {
        return checkNotFoundWithId(menuRepository.findById(id).orElse(null),id);
    }

    @Override
    public List<Menu> getAllByDate(Date date) {
        return menuRepository.findAllByRegisteredOrderByIdAsc(date);
    }
}
