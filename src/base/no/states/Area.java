package base.no.states;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Abstract class that contains all the
 * functions required to interact with
 * an area. It's the component in the
 * composite between the leaf Space
 * and the composite Partition.
 */
public abstract class Area {
  private final String id;
  private final String description;

  /**
   * Constructor of Area class, create an instance this class.
   *
   * @param idCopy          Contains the name of the Area
   * @param descriptionCopy Contains the description of this Area
   * @param partition   Is an instance of a partition where
   *                    it's required to store area in partition
   */
  public Area(final String idCopy, final String descriptionCopy,
              final Partition partition) {
    this.id = idCopy;
    this.description = descriptionCopy;
    if (partition != null) {
      partition.addArea(this);
    }
  }

  protected final String getId() {
    return id;
  }

  public abstract Area findAreaById(String idToSearch);

  public abstract ArrayList<Door> getDoorsGivingAccess();

  public abstract ArrayList<Space> getSpaces();

  public abstract JSONObject toJson(int depth);
}
