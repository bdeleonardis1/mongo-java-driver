package com.mongodb.record;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

public class SchoolCodec implements Codec<School> {

    @Override
    public School decode(BsonReader reader, DecoderContext decoderContext) {
        System.out.println(reader.toString());

        reader.readStartDocument();
        do  {
            String fieldName = reader.readName();
            System.out.println("fieldName: " + fieldName);
            BsonType type = reader.getCurrentBsonType();
            if (type == BsonType.OBJECT_ID) {
                System.out.println(reader.readObjectId());
            } else if (type == BsonType.STRING) {
                reader.readString();
            } else if (type == BsonType.INT32) {
                reader.readInt32();
            }
        } while (reader.readBsonType() != BsonType.END_OF_DOCUMENT);

        reader.readEndDocument();
        return new School(new ObjectId(),"Brian's Alma Matter");
    }

    @Override
    public void encode(BsonWriter writer, School value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeName("Hello");
        writer.writeString("World");
        writer.writeEndDocument();
    }

    @Override
    public Class<School> getEncoderClass() {
        return School.class;
    }
}

