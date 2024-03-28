package quackstagram.models;

/**
 * Defines the Observer part of the Observer Design Pattern.
 * Objects implementing this interface act as observers that can be notified of changes
 * in the state of a subject they are observing. This mechanism is typically used to implement
 * event handling systems where observers react to changes in the subject's state.
 *
 * Example usage includes updating user interfaces in response to model changes or
 * propagating state changes across different parts of an application.
 */
public interface Observer {

    /**
     * Called by the subject to notify this observer about a state change.
     * Implementing classes should define specific actions to be taken in response
     * to the notification, such as updating the user interface or processing the
     * changes in the subject.
     */
    void update();
}
