package com.codility;

import java.util.*;
import java.util.stream.*;

class VisitCounter {

    Map<Long, Long> count(Map<String, UserStats>... visits) {
        Map<Long, Long> totals = new HashMap<>();
        if (visits == null) {
            return totals;
        }
        Arrays.stream(visits)
                .filter(Objects::nonNull)
                .forEach(data -> data.forEach((key, stats) -> add(totals, key, stats)));
        return totals;
    }

    private static void add(Map<Long, Long> totals, String key, UserStats stats) {
        if (key == null || stats == null) {
            return;
        }
        long userId;
        try {
            userId = Long.parseLong(key);
        } catch (NumberFormatException e) {
            return;
        }
        stats.getVisitCount().ifPresent(sum -> totals.merge(userId, sum, Long::sum));
    }
}
