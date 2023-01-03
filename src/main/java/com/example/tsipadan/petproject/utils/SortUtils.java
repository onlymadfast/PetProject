package com.example.tsipadan.petproject.utils;

import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class SortUtils {

    public static Sort parseSort(String sort) {
        if (!StringUtils.hasLength(sort)) {
            return Sort.unsorted();
        }
        String[] sorts = sort.split(",");
        return Sort.by(Arrays.stream(sorts)
                .map(SortUtils::parseOrder)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    private static Sort.Order parseOrder(String sort) {
        if (!StringUtils.hasLength(sort)) {
            return null;
        }

        String field = sort.substring(1);
        if (!StringUtils.hasLength(field)) {
            throw new IllegalArgumentException("Sort parameter is malformed: " + sort);
        }

        if (sort.startsWith("-")) {
            return Sort.Order.desc(field);
        } else {
            return Sort.Order.asc(field);
        }
    }

}
