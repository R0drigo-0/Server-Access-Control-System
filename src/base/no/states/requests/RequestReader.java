package base.no.states.requests;

import base.no.states.DirectoryUserGroups;
import base.no.states.DirectoryAreas;
import base.no.states.Door;
import base.no.states.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class represents a request to read a door.
 * It contains information about the credential of
 * the action that the user wants to perform,
 * the time at which the request is being made,
 * and the ID of the door that the user wants to read.
 */
public class RequestReader implements Request {
  private final String credential; // who
  private final String action;     // what
  private final LocalDateTime now; // when
  private final String doorId;     // where
  private String userName;
  private boolean authorized;
  private final ArrayList<String> reasons; // why not authorized
  private String doorStateName;
  private boolean doorClosed;
  private static final Logger LOGGER = LoggerFactory.getLogger(
      RequestReader.class);


  public RequestReader(final String accreditation, final String thingToDo,
                       final LocalDateTime timeNow, final String idDoor) {
    this.credential = accreditation;
    this.action = thingToDo;
    this.doorId = idDoor;
    reasons = new ArrayList<>();
    this.now = timeNow;
  }

  public void setDoorStateName(final String name) {
    doorStateName = name;
  }

  public String getAction() {
    return action;
  }

  public boolean isAuthorized() {
    return authorized;
  }

  public void addReason(final String reason) {
    reasons.add(reason);
  }


  @Override
  public String toString() {
    if (userName == null) {
      userName = "unknown";
    }
    return "Request{"
        + "credential=" + credential
        + ", userName=" + userName
        + ", action=" + action
        + ", now=" + now
        + ", doorID=" + doorId
        + ", closed=" + doorClosed
        + ", authorized=" + authorized
        + ", reasons=" + reasons
        + "}";
  }

  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("authorized", authorized);
    json.put("action", action);
    json.put("doorId", doorId);
    json.put("closed", doorClosed);
    json.put("state", doorStateName);
    json.put("reasons", new JSONArray(reasons));
    return json;
  }

  // see if the request is authorized and
  // put this into the request, then send it to the door.
  // if authorized, perform the action.
  public void process() {
    User user = DirectoryUserGroups.findUserByCredential(credential);
    Door door = DirectoryAreas.findDoorById(doorId);
    assert door != null : "door " + doorId + " not found";
    authorize(user, door);
    // this sets the boolean authorize
    // attribute of the request
    door.processRequest(this);
    // even if not authorized we process
    // the request, so that if desired we could log all
    // the requests made to the server
    // as part of processing the request
    doorClosed = door.isClosed();
  }

  // the result is put into the request
  // object plus, if not authorized, why not,
  // only for testing
  private void authorize(final User user, final Door door) {
    authorized = false;
    if (user == null) {
      addReason("user doesn't exists");
    } else {
      //todo: get the who, where, when and what in order to decide, and if not
      // authorized add the reason(s)
      if (!Objects.equals(user.getCredential(), credential)) {
        reasons.add("The user does not have the appropriate credential.");
        LOGGER.warn("The user does not have the appropriate credential.");
      }

      if (!user.canBeInSpace(door.getToSpace())) {
        reasons.add("The user cannot access the destination space.");
        LOGGER.warn("The user cannot access the destination space.");

      }

      if (!user.canBeInSpace(door.getFromSpace())) {
        reasons.add("The user cannot access the source space.");
        LOGGER.warn("The user cannot access the source space.");

      }

      if (!user.canSendRequest(now)) {
        reasons.add("The user cannot send a request at this time.");
        LOGGER.warn("The user cannot send a request at this time.");
      }

      if (!user.doAction(action)) {
        reasons.add("The user cannot perform this action.");
        LOGGER.warn("The user cannot perform this action.");
      }

      if (reasons.isEmpty()) {
        authorized = true;
      }
    }
  }
}

