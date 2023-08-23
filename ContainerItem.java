import java.util.ArrayList;

public class ContainerItem extends Item {

    // member variables
    private ArrayList<Item> items;

    // constructor
    public ContainerItem(String pName, String pType, String pDescription) {
        super(pName, pType, pDescription);
        items = new ArrayList<Item>();
    }

    // methods
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

    public Item removeItem(String search) {
        for(int i = 0; i < items.size(); i++) {
            if(search.equalsIgnoreCase(items.get(i).getName())) {
                Item removed = (Item)items.get(i);
                items.remove(items.get(i));
                return removed;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String output = super.toString();
        for(int i = 0; i < items.size(); i++) {
            Item tmpItem = items.get(i);
            output += "\n - " + tmpItem.getName();
        }
        return output;
    }

}

