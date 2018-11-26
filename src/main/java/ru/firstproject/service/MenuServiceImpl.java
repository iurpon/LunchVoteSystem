package ru.firstproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.firstproject.model.Menu;
import ru.firstproject.repository.DateLabelRepository;
import ru.firstproject.repository.MenuRepository;
import ru.firstproject.util.exception.ChangeDeniedException;

import java.util.Date;
import java.util.List;
import static ru.firstproject.util.ValidationUtil.*;
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private DateLabelRepository dateLabelRepository;

    @Override
    public Menu save(Menu menu) {
        return checkForChange(dateLabelRepository.isPresentToday(),menuRepository.save(menu));
    }

    @Override
    public int delete(int id) {
        return checkForChange(dateLabelRepository.isPresentToday(),menuRepository.delete(id));
    }

    @Override
    public Menu get(int id) {
        return checkNotFoundWithId(menuRepository.get(id),id);
    }

    @Override
    public List<Menu> getAllByDate(Date date) {
        return menuRepository.getAllByDate(date);
    }
}
