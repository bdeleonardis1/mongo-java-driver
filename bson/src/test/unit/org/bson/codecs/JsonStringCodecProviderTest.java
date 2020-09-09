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

package org.bson.codecs;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.json.JsonObjectString;
import org.junit.Test;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JsonStringCodecProviderTest {

    @Test
    public void testJsonStringCodecProvider() {
        CodecProvider provider = new JsonStringCodecProvider();
        CodecRegistry registry = fromProviders(provider);
        assertEquals(provider.get(JsonObjectString.class, registry).getClass(), JsonStringCodec.class);
        assertNull(provider.get(Integer.class, registry));
    }
}
