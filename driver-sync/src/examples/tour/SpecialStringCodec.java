/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tour;

import org.bson.BsonInvalidOperationException;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.RepresentationConfigurable;
import org.bson.codecs.configuration.CodecConfigurationException;
import org.bson.types.ObjectId;

import static org.bson.assertions.Assertions.isTrueArgument;

/**
 * Encodes and decodes {@code String} objects.
 *
 * @since 3.0
 */
public class SpecialStringCodec implements Codec<String>, RepresentationConfigurable<String> {
    private BsonType representation;

    /**
     * Constructs a StringCodec with a String representation.
     */
    public SpecialStringCodec() {
        representation = BsonType.STRING;
    }

    private SpecialStringCodec(final BsonType representation) {
        this.representation = representation;
    }

    @Override
    public BsonType getRepresentation() {
        return representation;
    }

    @Override
    public Codec<String> withRepresentation(final BsonType representation) {
        return new SpecialStringCodec(representation);
    }


    @Override
    public void encode(final BsonWriter writer, final String value, final EncoderContext encoderContext) {
        switch (representation) {
            case STRING:
                writer.writeString(value);
                break;
            case INT32:
                writer.writeInt32(Integer.parseInt(value));
                break;
            default:
                throw new BsonInvalidOperationException("Cannot encode a String to a " + representation);
        }
    }

    @Override
    public String decode(final BsonReader reader, final DecoderContext decoderContext) {
        switch (representation) {
            case STRING:
                if (reader.getCurrentBsonType() == BsonType.SYMBOL) {
                    return reader.readSymbol();
                } else {
                    return reader.readString();
                }
            case INT32:
                return "" + reader.readInt32();
            default:
                throw new CodecConfigurationException("Cannot decode " + representation + " to a String");
        }
    }

    @Override
    public Class<String> getEncoderClass() {
        return String.class;
    }
}
