package ru.firstproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.firstproject.model.Menu;
import ru.firstproject.repository.DateLabelRepository;
import ru.firstproject.repository.MenuRepository;
import ru.firstproject.util.ValidationUtil;
import ru.firstproject.util.exception.ChangeDeniedException;

import java.util.Date;
import java.util.List;
import static ru.firstproject.util.ValidationUtil.*;

@Service
public class MenuServiceImpl implements MenuService {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private DateLabelRepository dateLabelRepository;

    @Override
    public Menu save(Menu menu) {
        boolean isPresent = dateLabelRepository.isPresentToday();
        if(isPresent){
            throw new ChangeDeniedException("voting already started. can't change menu");
        }else{
            logger.info("Save menu. is labal present? :" + isPresent );
            return menuRepository.save(menu);
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

    @Override
    public void update(Menu menu, int id) {
        checkCorrectId(menu,id);
        save(menu);
    }
}
