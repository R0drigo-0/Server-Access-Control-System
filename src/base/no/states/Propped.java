package base.no.states;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Implements all the methods from DoorState,
 * and it makes for a propped state.
 * This state occurs when the door was
 * locked form an unlock shortly state,
 * but it's still open, and a user have to
 * close manually.
 */
public class Propped extends DoorState {
  private static final Logger LOGGER = LoggerFactory.getLogger(Propped.class);

  public Propped(final Door door) {
    super(door);
    this.setState("propped");
    //this.door.setIsClosed(true);
  }


  @Override
  public final void open() {
    LOGGER.info("Door must be closed");
  }

  @Override
  public final void close() {
    getDoor().setIsClosed(true);
    getDoor().setState(new Locked(getDoor()));
  }

  @Override
  public final void lock() {
    LOGGER.info("Door must be closed, so can't be locked");
  }

  @Override
  public final void unlock() {
    LOGGER.info("Door must be closed, so can't be unlocked");
  }

  @Override
  public final void unlockShortly() {
    LOGGER.info("Door must be closed, so can't be unlocked shortly");
  }

  @Override
  public void update(final LocalDateTime now) {

  }
}
