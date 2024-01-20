package base.no.states;

import java.time.LocalDateTime;

/**
 * An interface that defines the contract for a clock observer.
 * A clock observer is notified when the clock's time changes.
 */
interface ClockObserver {
  //Notifies the observer that the clock's time has changed.
  void update(LocalDateTime now);
}
