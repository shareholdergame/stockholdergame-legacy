package com.stockholdergame.server.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;

/**
 * @author Alexander Savin
 */
public class DtoMapper {

    private static DtoMapper instance = new DtoMapper();

    private DtoMapper() {
    }

    public static DtoMapper getInstance() {
        return instance;
    }

    private Mapper mapper;

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    public static <T> T map(Object source, Class<? extends T> destinationClass) {
        return instance.mapper.map(source, destinationClass);
    }

    public static void map(Object source, Object destination) {
        instance.mapper.map(source, destination);
    }

    public static <T> List<T> mapList(List<?> sources, Class<? extends T> destinationClass) {
        List<T> list = new ArrayList<T>(sources.size());
        for (Object source : sources) {
            list.add(map(source, destinationClass));
        }
        return list;
    }
}
