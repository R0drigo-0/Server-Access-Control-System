package base.no.states;

import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class is in charge of manage all the items of the building,
 * like partitions, spaces or doors.
 * It stores all the doors in an array, and contains a root area,
 * where the children are smaller areas of this areas
 */
public final class DirectoryAreas {
  private static Area root; //Beginning of the building tree
  private static ArrayList<Door> allDoors;
  private static final Logger LOGGER = LoggerFactory.getLogger(
      DirectoryAreas.class);

  private static final DirectoryAreas UNIQUE_INSTANCE = new DirectoryAreas();

  private DirectoryAreas() {
    makeAreas();
  }

  public static DirectoryAreas getInstance() {
    return UNIQUE_INSTANCE;
  }

  public static void makeAreas() {
    Partition building = new Partition("building", "...", null);
    Partition basement = new Partition("basement", "", building);
    Partition groundfloor = new Partition("ground_floor", "...", building);
    Partition floor1 = new Partition("floor1", "...", building);

    Space stairs = new Space("stairs", "...", building);
    Space exterior = new Space("exterior", "...", building);
    Space parking = new Space("parking", "...", basement);
    Space hall = new Space("hall", "...", groundfloor);
    Space room1 = new Space("room1", "...", groundfloor);
    Space room2 = new Space("room2", "...", groundfloor);
    Space room3 = new Space("room3", "...", floor1);
    Space corridor = new Space("corridor", "...", floor1);
    Space it = new Space("IT", "...", floor1);

    // basement
    Door d1 = new Door("D1", exterior, parking); // exterior, parking
    Door d2 = new Door("D2", stairs, parking); // stairs, parking

    // groundfloor
    Door d3 = new Door("D3", exterior, hall); // exterior, hall
    Door d4 = new Door("D4", stairs, hall); // stairs, hall
    Door d5 = new Door("D5", hall, room1); // hall, room1
    Door d6 = new Door("D6", hall, room2); // hall, room2

    // first floor
    Door d7 = new Door("D7", stairs, corridor); // stairs, corridor
    Door d8 = new Door("D8", corridor, room3); // corridor, room3
    Door d9 = new Door("D9", corridor, it); // corridor, IT

    allDoors = new ArrayList<>(Arrays.asList(
        d1, d2, d3, d4, d5, d6, d7, d8, d9));
    root = building;
  }

  public static Area findAreaById(final String id) {
    //it calls the findAreaById of the specific class,
    //it can be spaces or partitions
    return root.findAreaById(id);
  }

  public static Door findDoorById(final String id) {
    for (Door door : allDoors) {
      if (door.getId().equals(id)) { //idDoor == idParameter
        return door;
      }
    }
    LOGGER.info("door with id " + id + " not found");
    return null;
  }

  // IMPORTANT: This function is needed by RequestRefresh
  public static ArrayList<Door> getAllDoors() {
    return allDoors;
  }
}
