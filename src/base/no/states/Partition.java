package base.no.states;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class helps us to be able to divide the areas into smaller areas.
 * All the information referent to the tree of areas in the building,
 * is stored in this class.
 */
public class Partition extends Area {
  //This array only contains the children of the area
  private ArrayList<Area> childrenAreas = new ArrayList<>();
  private static final Logger LOGGER = LoggerFactory.getLogger(Partition.class);

  public Partition(final String id, final String empty,
                   final Partition partition) {
    super(id, empty, partition);
  }

  public final String getPartitionId() {
    return super.getId();
  }

  public final void addArea(final Area area) {
    childrenAreas.add(area);
  }

  @Override
  public final Area findAreaById(final String id) {
    LOGGER.debug("Current partition: " + this.getPartitionId());
    if (id.equals("ROOT")) {
      // Special id that means that the wanted area is the root.
      // This is because the Flutter app client doesn't know the
      // id of the root, differently from the simulator
      return findAreaById("building");
    } else {
      if (this.getPartitionId().equals(id)) { //idArea == idParameter
        return this;
      } else {
        //Iterate by all the children and
        //call the same function
        //from the specific class
        for (Area area : childrenAreas) {
          Area instance = area.findAreaById(id);
          if (instance != null) {
            return instance;
          }
        }
      }
      return null;
    }
  }

  @Override
  public final ArrayList<Door> getDoorsGivingAccess() {
    ArrayList<Door> allChildrenDoorsGivingAccess = new ArrayList<>();
    //Iterate by all the children from the actual area
    //and save all the doors giving access to the child area
    for (Area area : childrenAreas) {
      allChildrenDoorsGivingAccess.addAll(area.getDoorsGivingAccess());
    }
    return allChildrenDoorsGivingAccess;
  }

  @Override
  public final ArrayList<Space> getSpaces() {
    ArrayList<Space> allChildrenSpaces = new ArrayList<>();
    //Iterate by all the children and
    //call the function from the specific method
    for (Area area : childrenAreas) {
      allChildrenSpaces.addAll(area.getSpaces());
    }
    return allChildrenSpaces;
  }

  public JSONObject toJson(int depth) {
    // for depth=1 only the root and children,
    // for recusive = all levels use Integer.MAX_VALUE
    JSONObject json = new JSONObject();
    json.put("class", "partition");
    json.put("id", this.getId());
    JSONArray jsonAreas = new JSONArray();
    if (depth > 0) {
      for (Area a : childrenAreas) {
        jsonAreas.put(a.toJson(depth - 1));
      }
      json.put("areas", jsonAreas);
    }
    return json;
  }
}
