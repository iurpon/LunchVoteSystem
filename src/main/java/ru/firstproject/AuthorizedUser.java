package ru.firstproject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.firstproject.model.User;

import static java.util.Objects.requireNonNull;


public class AuthorizedUser  extends org.springframework.security.core.userdetails.User{
    private static final long serialVersionUID = 1L;

    public static int id() {
        return getId();
    }

    private User user;

    public User getUser() {
        return user;
    }

    public static int getId() {
        return get().user.getId();
    }

    public AuthorizedUser(User user){
        super(user.getEmail(), user.getPassword(), true, true
                , true, true,user.getRoles());
        this.user = user;
    }

    public static AuthorizedUser safeGet(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null){
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get() {
        AuthorizedUser user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }
}
