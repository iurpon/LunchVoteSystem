package ru.firstproject.util;

import ru.firstproject.model.AbstractBaseEntity;
import ru.firstproject.util.exception.NotFoundException;

import java.time.LocalTime;

public class ValidationUtil {
    public static  LocalTime TIME_TO_CHANGE_MIND;

    public static LocalTime getLocalTime() {
        return TIME_TO_CHANGE_MIND == null ? LocalTime.of(11,0): TIME_TO_CHANGE_MIND;
    }

    public static void setLocalTime(LocalTime localTime) {
        TIME_TO_CHANGE_MIND = localTime;
    }

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

    public static void checkCorrectId(AbstractBaseEntity entity, int id){
        if(entity == null) throw new IllegalArgumentException("checkCorrectId with null entiry");
        if(id != entity.getId()){
            throw new IllegalArgumentException("checkCorrectId failed of different id");
        }
    }

}
