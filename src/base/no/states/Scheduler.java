package base.no.states;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**.
 * Store all the time and date information,
 * that is required to control an UserGroup
 */
public class Scheduler {
  private LocalTime startTime;
  private LocalTime endTime;
  private LocalDate startDate;
  private LocalDate endDate;
  private ArrayList<DayOfWeek> daysAllowed;

  public Scheduler(final LocalTime timeStart,
                   final LocalTime timeEnd,
                   final LocalDate dateStart,
                   final LocalDate dateEnd,
                   final ArrayList<DayOfWeek> allowedDays) {
    this.startTime = timeStart;
    this.endTime = timeEnd;
    this.startDate = dateStart;
    this.endDate = dateEnd;
    this.daysAllowed = allowedDays;
  }

  public final LocalTime getStartTime() {
    return startTime;
  }

  public final LocalTime getEndTime() {
    return endTime;
  }

  public final LocalDate getStartDate() {
    return startDate;
  }

  public final LocalDate getEndDate() {
    return endDate;
  }

  public final ArrayList<DayOfWeek> getDaysAllowed() {
    return daysAllowed;
  }
}
