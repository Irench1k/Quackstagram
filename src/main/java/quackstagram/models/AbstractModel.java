package quackstagram.models;

/**
 * The {@code AbstractModel} class serves as a base class for model entities in the Quackstagram application.
 * It defines a framework for serializing model data, updating models, and comparing model identities.
 * Child classes are expected to extend this class, providing implementations for serialization,
 * update capability checks, and identity comparison based on unique identifiers or relevant attributes.
 *
 * @param <T> The type of the model extending {@code AbstractModel}, used for type-safe identity comparison.
 */
public abstract class AbstractModel <T extends AbstractModel<T>> {

    /**
     * Serializes the model's attributes into a {@code String[]} for storage or transmission.
     * Implementing classes should provide a concrete implementation that represents the model's data as a string array.
     *
     * @return A {@code String[]} representing the serialized form of the model.
     */
    public abstract String[] serialize();

    /**
     * Determines if the model instance is updatable. Implementing classes should specify
     * whether instances of the model can be updated after creation.
     *
     * @return {@code true} if the instance can be updated; {@code false} otherwise.
     */
    public abstract boolean isUpdatable();

    /**
     * Compares the identity of this model instance with another instance of the same type.
     * Implementing classes should define this method to establish how instances are uniquely identified,
     * typically by comparing primary key fields or unique identifiers.
     *
     * @param object The other model instance of the same type to compare with this instance.
     * @return {@code true} if the instances are considered to be the same based on their unique identifiers; {@code false} otherwise.
     */
    public abstract boolean isIdEqualTo(T object);
}
