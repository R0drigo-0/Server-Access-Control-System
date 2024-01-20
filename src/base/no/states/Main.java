package base.no.states;

// Before executing enable assertions :
// https://se-education.org/guides/tutorials/intellijUsefulSettings.html


/**
 * A class that contains the main method for the program.
 * The main method initializes the clock, directories, and web server.
 */
public class Main {
  protected Main() {
  }
  public static final Clock CLOCK = Clock.getInstance(1);
  public static void main(final String[] args) {
    CLOCK.start();
    DirectoryAreas.makeAreas();
    DirectoryUserGroups.makeUsers();
    new WebServer();
  }
}
