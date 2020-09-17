package com.mongodb.record;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.Encoder;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RecordCodec<T> implements Codec<T> {
    private Map<String, Integer> componentNames;
    private Map<String, Codec<?>> codecs;
    private Map<String, RecordPropModel> propModels;
    private Constructor<T> constructor;
    private CodecRegistry registry;
    private Class<T> recordClass;
    public String idName;

    public RecordCodec(Class<T> recordClass, CodecRegistry registry) {
        componentNames = new HashMap<>();
        codecs = new HashMap<>();

        RecordComponent[] components = recordClass.getRecordComponents();
        for (int i = 0; i < components.length; i++) {
            componentNames.put(components[i].getName(), i);
            codecs.put(components[i].getName(), registry.get(components[i].getType()));
            if (components[i].getName().equals("id") || components[i].getName().equals("_id")) {
                idName = components[i].getName();
            }
        }
        constructor = (Constructor<T>) recordClass.getConstructors()[0];
        this.registry = registry;
        this.recordClass = recordClass;
    }


    @Override
    public T decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        Object[] values = new Object[componentNames.size()];
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String name = reader.readName();
            if (name.equals("_id") || name.equals(idName)) {
                values[componentNames.get(idName)] = decodeId(reader, decoderContext);

            } else {
                Codec<?> codec = codecs.get(name);
                System.out.println("name: " + name);
                Object value = codec.decode(reader, decoderContext);
                System.out.println("value: " + value);
                values[componentNames.get(name)] = value;
            }
        }
        reader.readEndDocument();

        try {
            return constructor.newInstance(values);
        } catch (Exception e) {
            return null;
        }
    }

    private Object decodeId(BsonReader reader, DecoderContext decoderContext) {
        System.out.println("Are we decoding the id?");
        return codecs.get(idName).decode(reader, decoderContext);
    }

    @Override
    public void encode(BsonWriter writer, T value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        encodeId(writer, value, encoderContext);
        try {
            for (String propName : codecs.keySet()) {
                if (propName.equals(idName)) {
                    continue;
                }

                writer.writeName(propName);
                Method m = value.getClass().getMethod(propName);
                Object o = m.invoke(value);
                encodeProperty(codecs.get(propName), o, writer, encoderContext);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        writer.writeEndDocument();
    }

    private void encodeId(BsonWriter writer, T value, EncoderContext encoderContext) {
        writer.writeName("_id");
        try {
            Method m = value.getClass().getMethod(idName);
            Object o = m.invoke(value);
            encodeProperty(codecs.get(idName), o, writer, encoderContext);
        } catch (Exception e) {
            System.out.println("E: " + e);
        }
    }

    private <T>  void encodeProperty(Codec<T> codec, Object value, BsonWriter writer, EncoderContext encoderContext) {
        codec.encode(writer, (T) value, encoderContext);
    }

    @Override
    public Class<T> getEncoderClass() {
        return recordClass;
    }
}
