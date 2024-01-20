package base.no.states;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The Space class is an implementation of the Area class which contains
 * all the doors that gives access to an area.
 */
public class Space extends Area {
  //IMPORTANT: Only are stored the doors that
  //gives access to a partition(area)
  private ArrayList<Door> doors = new ArrayList<>();

  public Space(final String id, final String empty, final Partition partition) {
    super(id, empty, partition);
  }

  public final String getSpaceId() {
    return super.getId();
  }

  @Override
  public final Area findAreaById(final String id) {
    if (id.equals("ROOT")) {
      // Special id that means that the wanted area is the root.
      // This is because the Flutter app client doesn't know the
      // id of the root, differently from the simulator
      return findAreaById("building");
    } else {
      if (this.getSpaceId().equals(id)) { //idArea == idParameter
        return this;
      }
      return null;
    }
  }

  @Override
  public final ArrayList<Door> getDoorsGivingAccess() {
    doors.clear(); // Clear the list to avoid duplicates
    ArrayList<Door> allDoorsList = DirectoryAreas.getAllDoors();
    for (Door door : allDoorsList) {
      // Compare using equals method for proper object comparison
      if (door.getToSpace().equals(this)) { //doorGivingAccessArea == thisArea
        doors.add(door);
      }
    }
    return doors;
  }

  @Override
  public final ArrayList<Space> getSpaces() {
    //It returns the same instance of
    //space class, but in an array format
    return new ArrayList<>(List.of(this));
  }

  public JSONObject toJson(int depth) { // depth not used here
    JSONObject json = new JSONObject();
    json.put("class", "space");
    json.put("id", this.getId());
    JSONArray jsonDoors = new JSONArray();
    for (Door d : getDoorsGivingAccess()) {
      jsonDoors.put(d.toJson());
    }
    json.put("access_doors", jsonDoors);
    return json;
  }
}
