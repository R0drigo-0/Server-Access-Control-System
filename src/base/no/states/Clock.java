package base.no.states;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Clock class provides access to the current instant,
 * date, and time using a time-zone. It uses the Singleton
 * pattern to ensure that only one instance of the class
 * is created. The class is also part of the observer
 * pattern, where it is the concrete observable.
 */
public final class Clock  extends ClockObservable {
  private Timer timer;
  private int period; // seconds
  private LocalDateTime date;
  private static Clock instance = null;
  private static final Logger LOGGER = LoggerFactory.getLogger(Clock.class);
  private static ArrayList<ClockObserver> listClockObserver = new ArrayList<>();

  /**
   * The constructor is private, because it's implementing
   * the lazy singleton pattern.
   */
  private Clock(final int phase) {
    this.period = phase;
    timer = new Timer();
  }

  /**
   * Returns the instance of the Clock class.
   * If the instance does not exist, it creates one.
   * This method uses the lazy singleton pattern to
   * ensure that only one instance of the class is created.
   */
  public static synchronized Clock getInstance(final int phase) {
    if (instance == null) {
      instance = new Clock(phase);
    }
    return instance;
  }

  /**
   * Starts the clock and schedules a task to run repeatedly at a fixed rate.
   * The task updates the current date and time and notifies all observers.
   */
  public void start() {
    LOGGER.info(String.valueOf(period));
    TimerTask repeatedTask = new TimerTask() {
      @Override
      public void run() {
        // instance of an anonymous class
        date = LocalDateTime.now();
        LOGGER.debug("run() executed at " + date);
        notifyObserver();
      }
    };
    timer.scheduleAtFixedRate(repeatedTask, 0, 1000 * period);
  }

  public void notifyObserver() {
    LOGGER.debug("Notify observer");
    for (ClockObserver observer : listClockObserver) {
      observer.update(date);
    }
  }

  public void addObserver(final ClockObserver observer) {
    LOGGER.debug("Add observer");
    listClockObserver.add(observer);
    // Inicia un nuevo temporizador para este observador
  }

  @Override
  public void deleteObserver(final ClockObserver clockObserver) {
    LOGGER.debug("Delete observable");
    listClockObserver.remove(clockObserver);
  }

  public void stop() {
    timer.cancel();
  }

  public int getPeriod() {
    return period;
  }

  public LocalDateTime getDate() {
    return date;
  }
}
