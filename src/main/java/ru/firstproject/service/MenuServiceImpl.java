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
        if(!dateLabelRepository.isPresentToday()){
            return menuRepository.save(menu);
        }else{
            throw new ChangeDeniedException("voting already started. can't change menu");
        }
    }

    @Override
    public int delete(int id) {
        if(!dateLabelRepository.isPresentToday()){
            return menuRepository.delete(id);
        }
        else{
            throw new ChangeDeniedException("voting already started. can't change menu");
        }
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
