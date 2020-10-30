package com.github.tsijercic1.xml.decoder;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;

public class PrimitiveDecoders {

    private static final Map<String, Function<String, Serializable>> decoders = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("java.lang.Integer", Integer::parseInt),
            new AbstractMap.SimpleEntry<>("java.lang.Long", Long::parseLong),
            new AbstractMap.SimpleEntry<>("java.lang.Double", Double::parseDouble),
            new AbstractMap.SimpleEntry<>("java.lang.Float", Float::parseFloat),
            new AbstractMap.SimpleEntry<>("java.lang.Boolean", Boolean::parseBoolean)
    );

    public static Serializable parseValue(String value, String type) {
        if (decoders.containsKey(type)) {
            return decoders.get(type).apply(value);
        } else {
            return value;
        }
    }

}
