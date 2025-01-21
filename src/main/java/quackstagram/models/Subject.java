package quackstagram.models;

/**
 * Defines the Subject part of the Observer Design Pattern.
 * Objects implementing this interface act as subjects that can be observed.
 * Observers can subscribe to or unsubscribe from updates from the subject,
 * and the subject is responsible for notifying its observers about state changes.
 *
 * Example implementations could include models representing user data,
 * configurations, or other entities that might change state within the application.
 */
public interface Subject {

    /**
     * Registers an observer to receive updates from this subject.
     *
     * @param observer the observer that wants to subscribe to updates
     */
    void addObserver(Observer observer);

    /**
     * Unregisters an observer so that it no longer receives updates from this subject.
     *
     * @param observer the observer that wants to unsubscribe from updates
     */
    void removeObserver(Observer observer);

    /**
     * Notifies all registered observers about a state change in this subject.
     */
    void notifyObservers();
}
