package base.no.states;

import base.no.states.requests.RequestReader;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class that represents a door between two spaces.
 * The door can be between a diverse states, defined
 * in the States class
 */
public class Door {
  private Space toSpace;
  private boolean closed; // false ==  open || true == closed
  private final String id;
  private Space fromSpace;
  private DoorState currentState;
  private static final Logger LOGGER = LoggerFactory.getLogger(Door.class);

  public Door(final String identification,
              final Space spaceFrom, final Space spaceTo) {
    this.id = identification;
    this.fromSpace = spaceFrom;
    this.toSpace = spaceTo;

    closed = true; //By default, the door is closed
    currentState = new Unlocked(this); //By default, the door is unlocked
  }

  public final void setState(final DoorState newState) {
    this.currentState = newState;
  }

  public final Space getFromSpace() {
    return fromSpace;
  }

  public final Space getToSpace() {
    return toSpace;
  }

  public final void setIsClosed(final boolean seal) {
    this.closed = seal;
  }

  public final void processRequest(final RequestReader request) {
    // It's the Door that process the request because the
    // door has and knows it's state, and if closed or open
    if (request.isAuthorized()) {
      String action = request.getAction();
      doAction(action);
    } else {
      LOGGER.warn("not authorized");
    }
    request.setDoorStateName(getStateName());
  }

  private void doAction(final String action) {
    LOGGER.debug("action " + action + " performed");
    switch (action) {
      case Actions.OPEN:
        this.currentState.open();
        break;
      case Actions.CLOSE:
        this.currentState.close();
        break;
      case Actions.LOCK:
        this.currentState.lock();
        break;
      case Actions.UNLOCK:
        this.currentState.unlock();
        break;
      case Actions.UNLOCK_SHORTLY:
        this.currentState.unlockShortly();
        break;
      default:
        assert false : "Unknown action " + action;

        System.exit(-1);
    }
  }

  public final boolean isClosed() {
    return closed;
  }

  public final String getId() {
    return id;
  }

  public final String getStateName() {
    return currentState.getState();
  }

  @Override
  public final String toString() {
    return "Door{"
        + ", id='" + id + '\''
        + ", closed=" + closed
        + ", state=" + getStateName()
        + "}";
  }

  public final JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", id);
    json.put("state", getStateName());
    json.put("closed", closed);

    System.out.println(json);
    return json;
  }
}
