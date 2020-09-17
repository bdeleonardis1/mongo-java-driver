package com.mongodb.record;

import org.bson.codecs.Codec;

public class RecordPropModel<T> {
    public Codec<T> codec;
    private Class<?> clazz;

    public RecordPropModel(Codec<T> codec, Class<?> clazz) {
        this.codec = codec;
        this.clazz = clazz;
    }

    public T getValue(Object o) {
        return (T) o;
    }
}
