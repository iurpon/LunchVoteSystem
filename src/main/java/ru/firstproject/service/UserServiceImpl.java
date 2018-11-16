package ru.firstproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.firstproject.model.User;
import ru.firstproject.repository.UserRepository;
import ru.firstproject.util.exception.NotFoundException;

import java.util.List;

@Service
public class UserServiceImpl  implements UserService{
    @Autowired
    private UserRepository repository;

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        if(!repository.delete(id)) throw new NotFoundException("No User with id = " + id);
    }

    @Override
    public User get(int id) throws NotFoundException {
        return repository.get(id);
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        User user = repository.getByEmail(email);
        if(user == null) throw new NotFoundException("No user with email = " + email);
        return user;
    }

    @Override
    public void update(User user) {
        repository.save(user);
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }
}
