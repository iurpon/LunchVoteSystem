package ru.firstproject.repository;

import org.springframework.stereotype.Repository;
import ru.firstproject.model.User;

import java.util.List;

@Repository
public class MockUserRepository  implements UserRepository{
    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
