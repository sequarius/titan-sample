package com.sequarius.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 30/01/2020
 */
public abstract class BeanUtils {
    public static <T> List<T> copyList(List source, Class<T> clazz) {
        List<T> result = new LinkedList<>();
        if (source.isEmpty()) {
            return result;
        }
        try {
            for (Object o : source) {
                T t = clazz.getDeclaredConstructor().newInstance();
                copyProperties(o, t);
                result.add(t);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> List<T> copyListIgnoreNull(List source, Class<T> clazz) {
        List<T> result = new LinkedList<>();
        if (source.isEmpty()) {
            return result;
        }
        try {
            for (Object o : source) {
                T t = clazz.getDeclaredConstructor().newInstance();
                copyPropertiesIgnoreNull(o, t);
                result.add(t);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static void copyProperties(Object src, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(src, target);
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
