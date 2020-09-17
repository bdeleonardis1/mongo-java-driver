package com.mongodb.record;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class RecordCodecProvider implements CodecProvider {
    private boolean automatic;

    private RecordCodecProvider(boolean automatic) {
        this.automatic = automatic;
    }


    public static Builder builder() {
        return new Builder();
    }

    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        return (Codec<T>) new SchoolCodec();
    }

    public static final class Builder {
        private boolean automatic;

        public Builder automatic(boolean automatic) {
            this.automatic = automatic;
            return this;
        }

        public RecordCodecProvider build() {
            return new RecordCodecProvider(automatic);
        }

    }
}
