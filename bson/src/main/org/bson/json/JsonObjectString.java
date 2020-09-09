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

package org.bson.json;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

/**
 * A wrapper class that holds a JSON document string. This class makes decoding JSON efficient.
 * Note that this class only holds valid JSON documents, not arrays.
 *
 * @since 4.2
 */
public class JsonObjectString implements Bson {
    private final String json;

    /**
     * Constructs a new instance with the given JSON string. Clients must ensure
     * they only pass in valid JSON documents to this constructor. The constructor does not
     * perform full validation on construction, but an invalid JsonString can cause errors
     * when it is used later on.
     *
     * @param json the JSON string
     */
    public JsonObjectString(final String json) {
        if (json == null) {
            throw new IllegalArgumentException("Json cannot be null");
        }

        String trimmed = json.trim();

        if (trimmed.charAt(0) != '{' || trimmed.charAt(trimmed.length() - 1) != '}') {
            throw new IllegalArgumentException("Json must be a valid JSON document");
        }

        this.json = json;
    }

    /**
     * Gets the JSON string
     *
     * @return the JSON string
     */
    public String getJson() {
        return json;
    }

    @Override
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> documentClass, CodecRegistry registry) {
        return Document.parse(json).toBsonDocument(documentClass, registry);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JsonObjectString that = (JsonObjectString) o;

        if (!json.equals(that.getJson())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return json.hashCode();
    }

    @Override
    public String toString() {
        return json;
    }
}
