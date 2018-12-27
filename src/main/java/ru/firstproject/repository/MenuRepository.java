package ru.firstproject.repository;

import ru.firstproject.model.Menu;

import java.util.Date;
import java.util.List;

public interface MenuRepository {
    Menu save(Menu menu);

    // null if not found
    Menu get(int id);

    List<Menu> getAllByDate(Date date);
}
