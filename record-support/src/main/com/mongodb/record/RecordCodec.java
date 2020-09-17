package com.mongodb.record;

import org.bson.codecs.configuration.CodecRegistry;

public class RecordCodec<T> {
    private String[] componentNames;
    public RecordCodec(Class<T> record, CodecRegistry registry, PropertyCodecRegistry propertyCodecRegistry) {

    }
}
