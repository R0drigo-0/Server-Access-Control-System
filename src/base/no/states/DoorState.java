package base.no.states;

import java.time.LocalDateTime;


/**.
 * Defines in an abstract class with
 * all the posible states that a door can be
 */
public abstract class DoorState implements ClockObserver {

  private Door door;
  private String state = "closed"; //By default, it's closed

  public DoorState(final Door entrance, final String mode) {
    this.door = entrance;
    this.state = mode;
  }

  public DoorState(final Door entrance) {
    this.door = entrance;
  }

  public final Door getDoor() {
    return door;
  }

  public final String getState() {
    return state;
  }

  public final void setState(final String mode) {
    this.state = mode;
  }

  public abstract void open();

  public abstract void close();

  public abstract void lock();

  public abstract void unlock();

  public abstract void unlockShortly();

  public abstract void update(LocalDateTime now);

}
