package com.mongodb.record.borrowed;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;


/**
 * Maps the index of a class's generic parameter type index to a property's.
 */
final class TypeParameterMap {
    private final Map<Integer, Either<Integer, TypeParameterMap>> propertyToClassParamIndexMap;

    /**
     * Creates a new builder for the TypeParameterMap
     *
     * @return the builder
     */
    static TypeParameterMap.Builder builder() {
        return new TypeParameterMap.Builder();
    }

    /**
     * Returns a mapping of property type parameter index to the class type parameter index.
     *
     * <p>Note: A property index of -1, means the class's parameter type represents the whole property</p>
     *
     * @return a mapping of property type parameter index to the class type parameter index.
     */
    Map<Integer, Either<Integer, TypeParameterMap>> getPropertyToClassParamIndexMap() {
        return propertyToClassParamIndexMap;
    }

    boolean hasTypeParameters() {
        return !propertyToClassParamIndexMap.isEmpty();
    }

    /**
     * A builder for mapping field type parameter indices to the class type parameter indices
     */
    static final class Builder {
        private final Map<Integer, Either<Integer, TypeParameterMap>> propertyToClassParamIndexMap = new HashMap<>();

        private Builder() {
        }

        /**
         * Adds the type parameter index for a class that represents the whole property
         *
         * @param classTypeParameterIndex the class's type parameter index that represents the whole field
         * @return this
         */
        TypeParameterMap.Builder addIndex(final int classTypeParameterIndex) {
            propertyToClassParamIndexMap.put(-1, Either.left(classTypeParameterIndex));
            return this;
        }

        /**
         * Adds a mapping that represents the property
         *
         * @param propertyTypeParameterIndex the property's type parameter index
         * @param classTypeParameterIndex the class's type parameter index
         * @return this
         */
        TypeParameterMap.Builder addIndex(final int propertyTypeParameterIndex, final int classTypeParameterIndex) {
            propertyToClassParamIndexMap.put(propertyTypeParameterIndex, Either.left(classTypeParameterIndex));
            return this;
        }


        /**
         * Adds a mapping that represents the property
         *
         * @param propertyTypeParameterIndex the property's type parameter index
         * @param typeParameterMap the sub class's type parameter map
         * @return this
         */
        TypeParameterMap.Builder addIndex(final int propertyTypeParameterIndex, final TypeParameterMap typeParameterMap) {
            propertyToClassParamIndexMap.put(propertyTypeParameterIndex, Either.right(typeParameterMap));
            return this;
        }

        /**
         * @return the TypeParameterMap
         */
        TypeParameterMap build() {
            if (propertyToClassParamIndexMap.size() > 1 && propertyToClassParamIndexMap.containsKey(-1)) {
                throw new IllegalStateException("You cannot have a generic field that also has type parameters.");
            }
            return new TypeParameterMap(propertyToClassParamIndexMap);
        }
    }

    @Override
    public String toString() {
        return "TypeParameterMap{"
                + "fieldToClassParamIndexMap=" + propertyToClassParamIndexMap
                + "}";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeParameterMap that = (TypeParameterMap) o;

        if (!getPropertyToClassParamIndexMap().equals(that.getPropertyToClassParamIndexMap())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return getPropertyToClassParamIndexMap().hashCode();
    }

    private TypeParameterMap(final Map<Integer, Either<Integer, TypeParameterMap>> propertyToClassParamIndexMap) {
        this.propertyToClassParamIndexMap = unmodifiableMap(propertyToClassParamIndexMap);
    }
}

