package com.mongodb.record;

import org.bson.types.ObjectId;

public record School(ObjectId id, String name) {
}
