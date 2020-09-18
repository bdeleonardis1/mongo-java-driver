package com.mongodb.record.borrowed;

import org.bson.codecs.configuration.CodecConfigurationException;
import org.bson.codecs.pojo.IdGenerator;
import org.bson.codecs.pojo.PropertyModel;

import static java.lang.String.format;

final class IdPropertyModelHolder<I> {
    private final PropertyModel<I> propertyModel;
    private final IdGenerator<I> idGenerator;

    static <T, I> IdPropertyModelHolder<I> create(final ClassModel<T> classModel, final PropertyModel<I> idPropertyModel) {
        return create(classModel.getType(), idPropertyModel, classModel.getIdPropertyModelHolder().getIdGenerator());
    }

    @SuppressWarnings("unchecked")
    static <T, I, V> IdPropertyModelHolder<I> create(final Class<T> type, final PropertyModel<I> idProperty,
                                                                          final IdGenerator<V> idGenerator) {
        if (idProperty == null && idGenerator != null) {
            throw new CodecConfigurationException(format("Invalid IdGenerator. There is no IdProperty set for: %s", type));
        } else if (idGenerator != null && !idProperty.getTypeData().getType().isAssignableFrom(idGenerator.getType())) {
            throw new CodecConfigurationException(format("Invalid IdGenerator. Mismatching types, the IdProperty type is: %s but"
                    + " the IdGenerator type is: %s", idProperty.getTypeData().getType(), idGenerator.getType()));
        }
        return new IdPropertyModelHolder<I>(idProperty, (IdGenerator<I>) idGenerator);
    }

    private IdPropertyModelHolder(final PropertyModel<I> propertyModel, final IdGenerator<I> idGenerator) {
        this.propertyModel = propertyModel;
        this.idGenerator = idGenerator;
    }

    PropertyModel<I> getPropertyModel() {
        return propertyModel;
    }

    IdGenerator<I> getIdGenerator() {
        return idGenerator;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IdPropertyModelHolder<?> that = (IdPropertyModelHolder<?>) o;

        if (propertyModel != null ? !propertyModel.equals(that.propertyModel) : that.propertyModel != null) {
            return false;
        }
        return idGenerator != null ? idGenerator.equals(that.idGenerator) : that.idGenerator == null;
    }

    @Override
    public int hashCode() {
        int result = propertyModel != null ? propertyModel.hashCode() : 0;
        result = 31 * result + (idGenerator != null ? idGenerator.hashCode() : 0);
        return result;
    }
}
