package ru.firstproject.util;

import ru.firstproject.model.AbstractBaseEntity;
import ru.firstproject.util.exception.ChangeDeniedException;
import ru.firstproject.util.exception.NotFoundException;

public class ValidationUtil {
    public static <T> T checkNotFoundWithId(T object, int id){
        if(object == null){
            throw new NotFoundException("Not found object with id = " + id);
        }
        return object;
    }
    public static <T> T checkNotFound(T object, String msg){
        if(object == null){
            throw new NotFoundException("Not found object : " + msg);
        }
        return object;
    }
    public static void  checkNotFoundWithId(boolean cond, int id){
        if(!cond){
            throw new NotFoundException("Not found object with id = " + id);
        }
    }

}
