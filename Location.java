import java.util.ArrayList;
import java.util.HashMap;

public class Location {

    // member variables
    private String name;
    private String description;
    private ArrayList<Item> items;
    private HashMap<String, Location> connections;

    // constructor
    public Location(String pName, String pDescription) {
        name = pName;
        description = pDescription;
        items = new ArrayList<Item>();
        connections = new HashMap<String, Location>();
    }

    // methods
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String pName) {
        name = pName;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public void addItem(Item pItem) {
        items.add(pItem);
    }

    public boolean hasItem(String search) {
        for(int i = 0; i < items.size(); i++) {
            if(search.equalsIgnoreCase(items.get(i).getName())) {
                return true;
            }
        }
        return false;
    }
    
    public Item getItem(String search) {
        for(int i = 0; i < items.size(); i++) {
            if(search.equalsIgnoreCase(items.get(i).getName())) {
                return (items.get(i));
            }
        }
        return null;
    }

    public Item getItem(int index) {
        if(index >= items.size()) {
            return null;
        }
        return items.get(index);
    }

    public int numItems() {
        return items.size();
    }

    public Item removeItem(String search) {
        for(int i = 0; i < items.size(); i++) {
            if(search.compareToIgnoreCase(items.get(i).getName()) == 0) {
                Item removed = (Item)items.get(i);
                items.remove(items.get(i));
                return removed;
            }
        }
        return null;
    }

    public void connect(String direction, Location loc) {
        connections.put(direction.toLowerCase(), loc);
    }

    public boolean canMove(String direction) {
        return connections.containsKey(direction.toLowerCase());
    }

    public Location getLocation(String direction) {
        return connections.get(direction.toLowerCase());
    }

}

