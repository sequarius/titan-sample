package com.sequarius.util;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 30/01/2020
 */
public abstract class ListBeanUtils {
    public static <T> List<T> copyList(List source, Class<T> clazz) {
        List<T> result = new LinkedList<>();
        if (source.isEmpty()) {
            return result;
        }
        try {
            for (Object o : source) {
                T t = clazz.getDeclaredConstructor().newInstance();
                result.add(t);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return result;
    }
}
