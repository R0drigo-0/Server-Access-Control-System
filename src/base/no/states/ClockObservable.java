package base.no.states;

/**
 * This abstract class defines the methods that are required
 * to be implemented by any class that wants to be observed
 * by the clock observer. It's part of the observer pattern.
 */
public abstract class ClockObservable {
  public abstract void addObserver(ClockObserver clockObserver);
  public abstract void deleteObserver(ClockObserver clockObserver);
  public abstract void notifyObserver();
}
