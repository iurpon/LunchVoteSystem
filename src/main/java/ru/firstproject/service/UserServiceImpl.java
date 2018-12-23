package ru.firstproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.firstproject.AuthorizedUser;
import ru.firstproject.model.User;
import ru.firstproject.repository.UserRepository;
import ru.firstproject.util.ValidationUtil;
import ru.firstproject.util.exception.NotFoundException;

import java.util.List;

@Service("userService")
public class UserServiceImpl  implements UserService, UserDetailsService{
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
        User user = repository.get(id);
        if(user == null){
            throw new NotFoundException("no user wiht id = " + id);
        }
        return  user;
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        User user = repository.getByEmail(email);
        if(user == null) throw new NotFoundException("No user with email = " + email);
        return user;
    }

    @Override
    public void update(User user,int id) {
        ValidationUtil.checkCorrectId(user,id);
        repository.save(user);
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User " + email + " not found");
        }
        return new AuthorizedUser(user);
    }
}
