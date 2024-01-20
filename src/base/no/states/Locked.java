package base.no.states;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Implements all the methods from DoorState,
 * and it makes for a locked state.
 */
public class Locked extends DoorState {
  private static final Logger LOGGER = LoggerFactory.getLogger(Locked.class);

  public Locked(final Door door) {
    super(door);
    this.setState("locked");
  }

  public final void open() {
    LOGGER.info("This door is locked");
  }

  public final void close() {
    LOGGER.info("This door is locked");
  }

  public final void lock() {
    LOGGER.info("This door is already locked");
  }

  public final void unlock() {
    LOGGER.info("Unlocking this door");
    //Changes door state to unlocked
    this.getDoor().setState(new Unlocked(this.getDoor()));
  }

  public final void unlockShortly() {
    LOGGER.info("Opening door for 10 seconds");
    getDoor().setState(new UnlockedShortly(getDoor()));
  }

  @Override
  public void update(final LocalDateTime now) {
  }
}

