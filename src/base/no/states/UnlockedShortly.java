package base.no.states;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Implements all the methods from DoorState,
 * and it makes for an unlocked shortly state.
 */
public class UnlockedShortly extends DoorState implements ClockObserver {
  static final int FREQUENCY_TIMER = 10;
  private Clock clock = Clock.getInstance(FREQUENCY_TIMER);
  private LocalDateTime timeCalled;
  private static final Logger LOGGER = LoggerFactory.getLogger(
      UnlockedShortly.class);


  public UnlockedShortly(final Door door) {
    super(door);
    timeCalled = LocalDateTime.now();
    clock.addObserver(this);
    this.setState("unlocked_shortly");
  }

  @Override
  public final void open() {
    getDoor().setIsClosed(false);
  }

  @Override
  public final void close() {
    getDoor().setIsClosed(true);
  }

  @Override
  public final void lock() {
    LOGGER.info("It can't be locked now");
  }

  @Override
  public final void unlock() {
    LOGGER.info("It will lock in a few moment");
  }

  @Override
  public final void unlockShortly() {
    LOGGER.info("Already is unlocked shortly");
  }

  @Override
  public final void update(final LocalDateTime now) {
    LOGGER.debug("Start counting unlock shortly");
    if ((timeCalled.getSecond() + FREQUENCY_TIMER) % 60 == now.getSecond()) {
      if (getDoor().isClosed()) {
        getDoor().setState(new Locked(getDoor()));
      } else {
        getDoor().setState(new Propped(getDoor()));
      }
    }
  }

}
