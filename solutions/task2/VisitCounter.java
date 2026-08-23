package com.codility;

import java.util.*;
import java.util.stream.*;

class VisitCounter {

    Map<Long, Long> count(Map<String, UserStats>... visits) {
        if (visits == null) {
            return Collections.emptyMap();
        }
        return Arrays.stream(visits)
                .filter(Objects::nonNull)                       // a microservice map may be null
                .flatMap(service -> service.entrySet().stream())
                .filter(entry -> entry != null
                        && entry.getKey() != null               // faulty key
                        && entry.getValue() != null)            // faulty UserStats
                .map(VisitCounter::toCountedEntry)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.summingLong(Map.Entry::getValue)));
    }

    /**
     * Turns a raw entry into a (userId, visitCount) pair, or an empty Optional
     * when the key is not parseable to a Long or the visit count is absent.
     */
    private static Optional<Map.Entry<Long, Long>> toCountedEntry(Map.Entry<String, UserStats> entry) {
        Long userId;
        try {
            userId = Long.valueOf(entry.getKey());
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
        return entry.getValue()
                .getVisitCount()
                .map(visitCount -> new AbstractMap.SimpleEntry<>(userId, visitCount));
    }
}
