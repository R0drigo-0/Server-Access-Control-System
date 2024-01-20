package base.no.states;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Implements all the methods from DoorState,
 * and it makes for an unlocked state.
 */
public class Unlocked extends DoorState {
  private static final Logger LOGGER = LoggerFactory.getLogger(Unlocked.class);

  public Unlocked(final Door door) {
    super(door);
    this.setState("unlocked");
  }

  public final void open() {
    if (this.getDoor().isClosed()) {
      LOGGER.info("Opening this door");
      this.getDoor().setIsClosed(false);
    } else {
      LOGGER.info("This door is already opened");
    }
  }

  public final void close() {
    if (this.getDoor().isClosed()) {
      LOGGER.info("This door is already closed");
    } else {
      LOGGER.info("Closing this door");
      this.getDoor().setIsClosed(true);
    }
  }

  public final void lock() {
    if (!this.getDoor().isClosed()) {
      LOGGER.info("Door" + this.getDoor().getId()
          + "is open and can't be locked");
    } else {
      LOGGER.info("Door has been locked");
      this.getDoor().setState(new Locked(this.getDoor()));
    }
  }

  public final void unlock() {
    LOGGER.info("This door is already unlocked");
  }

  public final void unlockShortly() {
    LOGGER.info("This door is already unlocked");
  }

  @Override
  public void update(final LocalDateTime now) { }

}
