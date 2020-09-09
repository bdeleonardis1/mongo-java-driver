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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class JsonObjectStringTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNull() {
        new JsonObjectString(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArray() {
        new JsonObjectString("['A', 'B', 'C']");
    }

    @Test
    public void testEqualsAndHashCode() {
        JsonObjectString j1 = new JsonObjectString("{hello: 1}");
        JsonObjectString j2 = new JsonObjectString("{hello: 1}");
        JsonObjectString j3 = new JsonObjectString("{world: 2}");

        assertTrue(j1.equals(j1));
        assertTrue(j1.equals(j2));
        assertTrue(j2.equals(j1));
        assertFalse(j1.equals(j3));
        assertFalse(j3.equals(j1));
        assertFalse(j1.equals(null));
        assertFalse(j1.equals("{hello: 1}"));

        assertEquals(j1.hashCode(), j1.hashCode());
        assertEquals(j1.hashCode(), j2.hashCode());
    }

    @Test
    public void testGetJson() {
        JsonObjectString j1 = new JsonObjectString("{hello: 1}");
        assertEquals(j1.getJson(), "{hello: 1}");
    }
}
