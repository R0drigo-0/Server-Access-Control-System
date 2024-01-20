package base.no.states;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is in charge of create each type of users
 * with the limitations that should have.
 */
public class DirectoryUserGroups {
  private static final ArrayList<User> ALL_USERS = new ArrayList<>();
  private static final Logger LOGGER = LoggerFactory.getLogger(
      DirectoryUserGroups.class);

  protected DirectoryUserGroups() {
    makeUsers();
  }

  public static void makeUsers() {
    // employees :
    // Sep. 1 2023 to Mar. 1 2024
    // week days 9-17h
    // just open, close and unlock shortly
    // groundfloor, floor1, exterior, stairs
    // (this, for all), that is, everywhere but the parking
    UserGroup employees = new UserGroup("employees",
        new Scheduler(
            LocalTime.of(9, 0),
            LocalTime.of(17, 0),
            LocalDate.of(2023, 9, 1),
            LocalDate.of(2024, 3, 1),
            new ArrayList<>(Arrays.asList(
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY))),
        new ArrayList<>(Arrays.asList("lock", "unlock")),
        new ArrayList<>(Arrays.asList("parking")));

    // managers :
    // Sep. 1 2023 to Mar. 1 2024
    // week days + saturday, 8-20h
    // all actions
    // all spaces
    UserGroup managers = new UserGroup("managers",
        new Scheduler(
            LocalTime.of(8, 0),
            LocalTime.of(20, 0),
            LocalDate.of(2023, 9, 1),
            LocalDate.of(2024, 3, 1),
            new ArrayList<>(Arrays.asList(
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY,
                DayOfWeek.SATURDAY))),
        new ArrayList<>(),
        new ArrayList<>());

    // admin :
    // always=2023 to 2100
    // all days of the week
    // all actions
    // all spaces
    UserGroup admin = new UserGroup("admin",
        new Scheduler(
            LocalTime.of(0, 0, 0),
            LocalTime.of(23, 59, 59),
            LocalDate.of(2023, 1, 1),
            LocalDate.of(2100, 12, 31),
            new ArrayList<>(Arrays.asList(
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY,
                DayOfWeek.SATURDAY,
                DayOfWeek.SUNDAY))),
        new ArrayList<>(),
        new ArrayList<>());

    // users without any privilege, just to keep
    // temporally users instead of deleting them,
    // this is to withdraw all permissions but
    // still to keep user data to give back
    // permissions later
    UserGroup blank = new UserGroup(null,
        new Scheduler(
            null,
            null,
            null,
            null,
            new ArrayList<>()),
        new ArrayList<>(),
        new ArrayList<>());

    //Create each user with her group
    ALL_USERS.add(new User("Bernat", "12345", blank));
    ALL_USERS.add(new User("Blai", "77532", blank));

    ALL_USERS.add(new User("Ernest", "74984", employees));
    ALL_USERS.add(new User("Eulalia", "43295", employees));

    ALL_USERS.add(new User("Manel", "95783", managers));
    ALL_USERS.add(new User("Marta", "05827", managers));

    ALL_USERS.add(new User("Ana", "11343", admin));
  }

  public static User findUserByCredential(final String credential) {
    for (User user : ALL_USERS) {
      if (user.getCredential().equals(credential)) {
        //userCredential == credentialParameter
        return user;
      }
    }
    LOGGER.info("user with credential " + credential + " not found");
    return null;
  }

}
