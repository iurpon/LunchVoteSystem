package ru.firstproject.service;

import ru.firstproject.model.Menu;

import java.util.Date;
import java.util.List;

public interface MenuService {
    Menu save(Menu menu);

    // null if not found
    Menu get(int id);

    List<Menu> getAllByDate(Date date);

    void update(Menu menu, int id);
}
