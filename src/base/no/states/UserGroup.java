package base.no.states;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**.
 * This class store all the limitations by
 * each type of user that it can be.
 */
public class UserGroup {
  private String groupName;
  private Scheduler scheduler; //Contains the time and date limitations
  private ArrayList<String> actionsForbidden = new ArrayList<>();
  //Action that a group can't do
  private ArrayList<String> areasForbidden = new ArrayList<>();
  //Areas that a group can't access

  public UserGroup(final String nameGroup,
                   final Scheduler itinerary,
                   final ArrayList<String> forbiddenActions,
                   final ArrayList<String> forbiddenAreas) {
    this.groupName = nameGroup;
    this.scheduler = itinerary;
    this.actionsForbidden = forbiddenActions;
    this.areasForbidden = forbiddenAreas;
  }

  public final boolean canSendRequest(final LocalDateTime now) {
    LocalDateTime startDateTime = LocalDateTime.of(
        scheduler.getStartDate(), scheduler.getStartTime());
    LocalDateTime endDateTime = LocalDateTime.of(
        scheduler.getEndDate(), scheduler.getEndTime());

    return now.isAfter(startDateTime)
        && now.isBefore(endDateTime)
        && now.toLocalTime().isAfter(scheduler.getStartTime())
        && now.toLocalTime().isBefore(scheduler.getEndTime())
        && scheduler.getDaysAllowed().contains(now.getDayOfWeek());
  }

  public final boolean canBeInSpace(final Space space) {
    return !areasForbidden.contains(space.getId());
  }


  public final boolean doAction(final String action) {
    return !actionsForbidden.contains(action);
  }

}
