package com.bzamani.framework.common.config.dozermapper;

import com.bzamani.framework.model.core.BaseEntity;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.DozerBeanMapper;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author azizkhani
 */
@Component
public class ModelMapper {

    private static Mapper mapper;

    @Autowired(required = true)
    public ModelMapper(@Qualifier("dozerBeanMapperFactoryBean") Mapper mapper) {
        ModelMapper.mapper = mapper;
    }

    public static Mapper getMapper() {

        //if (mapper != null)
            return mapper;
/*
        try {
            mapper = new DozerBeanMapper();
        } catch (Exception ex) {
            mapper = null;
        }
        return mapper;*/
    }

    public static <T, U> U map(T source, final Class<U> destType) {
        return map(source, destType, true);
    }

    public static <T, U> U map(T source, final Class<U> destType, Boolean isSetNullModels) {
        if (source != null) {
            U dest = getMapper().map(source, destType);
            if (isSetNullModels)
                setNullEntity(dest);
            return dest;
        } else
            return null;
    }

    public static <T> void setNullEntity(T source) {
        try {
            List<Field> fields = getAllFields(source);
            for (Field field : fields) {
                field.setAccessible(true);
                Object innerObject = field.get(source);
                if (innerObject instanceof BaseEntity) {
                    Method[] methods = field.getType().getMethods();
                    for (Method method : methods) {
                        if (method.getName().contentEquals("getId")) {
                            Object invokeMethod = method.invoke(innerObject);
                            if (invokeMethod == null)
                                field.set(source, null);
                            if (invokeMethod instanceof Long) {
                                Long longValue = (Long) invokeMethod;
                                if (longValue == -1l)
                                    field.set(source, null);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static <T> List<Field> getAllFields(T source) {
        List<Field> fields = new ArrayList<Field>();
        for (Field field : source.getClass().getDeclaredFields()) {
            fields.add(field);
        }

        if (source.getClass().getSuperclass() != null) {
            for (Field field : source.getClass().getSuperclass().getDeclaredFields())
                fields.add(field);
        }
        return fields;
    }


    public static <T, U> List<U> mapList(final List<T> source, final Class<U> destType) {
        final List<U> dest = new ArrayList();
        for (T element : source) {
            dest.add(getMapper().map(element, destType));
        }
        return dest;
    }

    public static <T, U> List<U> mapList(final List<T> source, final Class<U> destType, Boolean isSetNullModels) {
        final List<U> dest = new ArrayList();
        for (T element : source) {
            dest.add(map(element, destType, isSetNullModels));
        }
        return dest;
    }

    public static <T, U> Set<U> mapSet(final Set<T> source, final Class<U> destType) {
        final Set<U> dest = new HashSet<U>();
        for (T element : source) {
            dest.add(getMapper().map(element, destType));
        }
        return dest;
    }

    public static <T, U> Set<U> mapSet(final Set<T> source, final Class<U> destType, Boolean isSetNullModels) {
        final Set<U> dest = new HashSet<U>();
        for (T element : source) {
            dest.add(map(element, destType, isSetNullModels));
        }
        return dest;
    }


    public static <T> T convertJsonToObject(String jsonData, Class<T> convertedClass) {
        try {
            return (T) new ObjectMapper().readValue(jsonData, convertedClass);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertObjectToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
