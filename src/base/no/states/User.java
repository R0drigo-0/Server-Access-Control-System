package base.no.states;

import java.time.LocalDateTime;

/**.
 * This class is in charge of save specific
 * information relative to a user
 */
public class User {
  private final String name;
  private final String credential;
  private UserGroup userGroup; //Store the type of user

  public User(final String denomination, final String accreditation,
              final UserGroup groupUser) {
    this.name = denomination;
    this.credential = accreditation;
    this.userGroup = groupUser;
  }

  public final String getCredential() {
    return credential;
  }

  public final UserGroup getUserGroup() {
    return userGroup;
  }

  /**.
   * Convert the attributes name and credential in a predefined string
   *
   * @return String with the name and credential of the user
   */
  @Override
  public  String toString() {
    return "User{name=" + name + ", credential=" + credential + "}";
  }

  /**.
   * This method executes the specific method my each UserGroup.
   *
   * @param now Get the time when the request was made
   * @return true if the UserGroup of user can send the request;
   * if not return false
   */
  public boolean canSendRequest(final LocalDateTime now) {
    return userGroup.canSendRequest(now);
  }

  public final boolean canBeInSpace(final Space space) {
    return userGroup.canBeInSpace(space);
  }

  public final boolean doAction(final String action) {
    return userGroup.doAction(action);
  }
}
