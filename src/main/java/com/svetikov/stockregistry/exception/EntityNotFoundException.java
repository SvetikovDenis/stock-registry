package com.svetikov.stockregistry.exception;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class clazz, String... searchParamsMap) {
        super(EntityNotFoundException.generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) +
                " was not found for parameters " +
                searchParams;
    }

    private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object... entries) {
        if (entries.length % 2 == 1)
            throw new IllegalArgumentException("Invalid entries");

        Map<K, V> entriesReturn = new HashMap<>();

        for (int i = 0; i < entries.length /2; i++) {

            entriesReturn.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1]));
        }
        return entriesReturn;
    }

}