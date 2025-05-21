package com.mesurpreenda.api.util;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class RandomSelector {
    public static <T> Optional<T> getRandom(List<T> list) {
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(new Random().nextInt(list.size())));
    }
}