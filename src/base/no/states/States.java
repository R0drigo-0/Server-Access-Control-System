package base.no.states;

/**
 * Defines all the states that can have a door
 */
public class States {
  protected States() {
  }
  public static final String OPEN = "open";
  public static final String CLOSED = "closed";
  public static final String LOCKED = "locked";
  public static final String UNLOCKED = "unlocked";
  public static final String UNLOCKED_SHORTLY = "unlocked_shortly";
}
