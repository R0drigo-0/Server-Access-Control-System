package base.no.states;

/*
 * This class contains all the posible actions to
 * interact with the readers of the doors.
 */
public final class Actions {
  // possible actions in reader and area requests
  public static final String LOCK = "lock";
  public static final String UNLOCK = "unlock";
  public static final String UNLOCK_SHORTLY = "unlock_shortly";
  // possible actions in door requests
  public static final String OPEN = "open";
  public static final String CLOSE = "close";

  private Actions() {
  }
}
