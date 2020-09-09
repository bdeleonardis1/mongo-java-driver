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

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.bson.types.ObjectId;

/**
 * The Person Pojo
 */
public final class PersonStringId {
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String id;

    private String name;

    private int age;
    /**
     * Construct a new instance
     */
    public PersonStringId() {
    }

    /**
     * Construct a new instance
     *
     * @param name the name
     * @param age the age
     */
    public PersonStringId(final String name, final int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Returns the id
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id
     *
     * @param id the id
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Returns the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     *
     * @param name the name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Returns the age
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age
     *
     * @param age the age
     */
    public void setAge(final int age) {
        this.age = age;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Person person = (Person) o;

        if (getAge() != person.getAge()) {
            return false;
        }
        if (getId() != null ? !getId().equals(person.getId()) : person.getId() != null) {
            return false;
        }
        if (getName() != null ? !getName().equals(person.getName()) : person.getName() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + getAge();
        return result;
    }

    @Override
    public String toString() {
        return "Person{"
                + "id='" + id + "'"
                + ", name='" + name + "'"
                + ", age=" + age
                + "}";
    }
}

