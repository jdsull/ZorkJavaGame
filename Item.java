public class Item {

    // member variables
    private String name;
    private String type;
    private String description;

    // constructor
    public Item(String pName, String pType, String pDescription) {
        name = pName;
        type = pType;
        description = pDescription;
    }

    // methods
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String pName) {
        name = pName;
    }

    public void setType(String pType) {
        type = pType;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public String toString() {
        String output = name + " [" + type + "]: " + description;
        return output;
    }

}

