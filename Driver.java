import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

    // static current location and inventory variables
    private static Location curLocation;
    private static ContainerItem myInventory;
    // ArrayList of the movable cardinal directions
    private static ArrayList<String> directions = new ArrayList<String>();

    public static void createWorld() {
        // add the a list of movable directions
        directions.add("north");
        directions.add("east");
        directions.add("south");
        directions.add("west");

        // initialize the locations
        Location kitchen = new Location("kitchen", "An ordinary kitchen, but someone left the oven on");
        Location hallway = new Location("hallway", "A long, dark hallway; the walls are lined with old pictures");
        Location bedroom = new Location("bedroom", "The dark bedroom is illuminated with a single flickering light");
        Location livingRoom = new Location("living room", "The TV is casting shadows on the torn floral wallpaper");

        // connect the locations to each other (two-way connection):
            // livingRoom - hallway
            //      |          |
            //   kitchen    bedroom
        kitchen.connect("north", livingRoom);
        livingRoom.connect("south", kitchen);

        livingRoom.connect("east", hallway);
        hallway.connect("west", livingRoom);

        hallway.connect("south", bedroom);
        bedroom.connect("north", hallway);

        // initilize the items
        Item knife = new Item("knife", "Weapon", "A knife that hasn't been sharpened for a while");
        Item turkey = new Item("turkey", "Food", "A turkey that was cooked too long");
        Item lamp = new Item("lamp", "Furniture", "A dimly lit standing lamp");
        Item picture = new Item("picture", "Decoration", "An old family portrait in a rustic wood frame");
        Item pillow = new Item("pillow", "Furniture", "A soft down pillow");

        // add the items to their locations
        kitchen.addItem(knife);
        kitchen.addItem(turkey);
        livingRoom.addItem(lamp);
        hallway.addItem(picture);
        bedroom.addItem(pillow);

        // set the current location
        curLocation = kitchen;
    }

    public static void main(String[] args) {
        // create the world and set the current location
        createWorld();

        // create the backpack container item
        myInventory = new ContainerItem("Inventory", "Container", "An old backpack that holds all of your items");

        // open scanner for user input
        Scanner inputScanner = new Scanner(System.in);

        // game loop
        boolean playingGame = true;
        while(playingGame) {
            // get user input
            System.out.println("Enter command: ");
            System.out.print("> ");
            String userInput = inputScanner.nextLine();
            String[] splitUserInput = userInput.split(" ");
            String command = splitUserInput[0];

            // switch for the commands
            switch (command.toLowerCase()) {
                case "quit":
                    // if input is quit, say thanks and break the loop
                    System.out.println("Thanks for playing!");
                    playingGame = false;

                    break;
                case "look":
                    // print locations name and description
                    System.out.println("You are in the " + curLocation.getName());
                    System.out.println(curLocation.getDescription());

                    // check to see if there are items in the loc
                    if(curLocation.numItems() > 0) {
                        // if there are, list the items and descriptions
                        System.out.println("You see some items:");
                        for(int i = 0; i < curLocation.numItems(); i++) {
                            System.out.println("- " + curLocation.getItem(i));
                        }
                    } else {
                        // if there aren't any items say no items
                        System.out.println("You don't see any items here");
                    }

                    break;
                case "examine":
                    // if input is examine x, examine x; otherwise, say idk
                    if(splitUserInput.length == 2) {
                        String commandItem = splitUserInput[1];
                        
                        // get the item
                        Item examinedItem = curLocation.getItem(commandItem);
                        if(examinedItem != null) {
                            // if the item is in the current location, examine
                            System.out.println("You examined the " + examinedItem.getName() + ":");
                            System.out.println("- " + examinedItem.toString());
                        } else {
                            // if the item isn't in the current location
                            System.out.println("There isn't a " + commandItem + " in this location");
                        }
                    } else {
                        System.out.println("I'm not sure what you want to examine");
                    }

                    break;
                case "go":
                    // if input is go x, go to x; otherwise say idk
                    if(splitUserInput.length == 2) {
                        // get the direction
                        String commandDir = splitUserInput[1];
                        
                        // make sure the direction is a valid cardinal direction
                        if(directions.contains(commandDir.toLowerCase())) {
                            // if it can move to the given direction
                            if(curLocation.canMove(commandDir)) {
                                // get the location in the direction and make it the current location
                                Location moveLocation = curLocation.getLocation(commandDir);
                                curLocation = moveLocation;

                                // print locations name and description
                                System.out.println("You moved to the " + curLocation.getName());
                                System.out.println(curLocation.getDescription());

                                // check to see if there are items in the loc
                                if(curLocation.numItems() > 0) {
                                    // if there are, list the items and descriptions
                                    System.out.println("You see some items:");
                                    for(int i = 0; i < curLocation.numItems(); i++) {
                                        System.out.println("- " + curLocation.getItem(i));
                                    }
                                } else {
                                    // if there aren't any items say no items
                                    System.out.println("You don't see any items here");
                                }
                            } else {
                                // if there is no where to go in that direction
                                System.out.println("There's no where to go " + commandDir);
                            }
                        } else {
                            // if the direction is not a valid cardinal direction
                            System.out.println("I can only move in the main cardinal directions");
                        }
                    } else {
                        // if there isn't a direction given
                        System.out.println("I'm not sure which cardinal direction you want to go");
                    }

                    break;
                case "inventory":
                    // items only?
                    System.out.println(myInventory.toString());

                    break;
                case "take":
                    if(splitUserInput.length == 2) {
                        // get the item
                        String commandItem = splitUserInput[1];
                        // if the item is in the current location
                        if(curLocation.hasItem(commandItem)) {
                            // remove and return the item from the current location
                            Item tmpItem = curLocation.removeItem(commandItem);
                            // add the item to the inventory
                            myInventory.addItem(tmpItem);
                            System.out.println("You picked up the " + tmpItem.getName());
                        } else {
                            // if there isn't that item in the current location
                            System.out.println("You can't find a " + commandItem + " in this location");
                        }
                    } else {
                        // if there wasn't an item specified
                        System.out.println("I'm not sure what item you want to take");
                    }

                    break;
                case "drop": 
                    if(splitUserInput.length == 2) {
                        // get the item
                        String commandItem = splitUserInput[1];
                        // if the item is in the inventory
                        if(myInventory.hasItem(commandItem)) {
                            // remove from inventory
                            Item tmpItem = myInventory.removeItem(commandItem);
                            // add the item back to location
                            curLocation.addItem(tmpItem);
                            System.out.println("You dropped the " + tmpItem.getName());
                        } else {
                            // if there isn't that item in the inventory
                            System.out.println("There isn't a " + commandItem + " in your inventory");
                        }
                    } else {
                        // if there wasn't an item specified
                        System.out.println("I'm not sure what item you want to drop");
                    }
                    
                    break;
                case "help":
                    // print a list of available commands
                    System.out.println("Available commands:");
                    System.out.println("- look: look around the current location for any items in the area");
                    System.out.println("- examine: look at an item to see its name, type, and description");
                    System.out.println("- go: move in one of the cardinal directions to a different location");
                    System.out.println("- inventory: show what items are currently in your inventory");
                    System.out.println("- take: pick up and add an item to your inventory");
                    System.out.println("- drop: drop an item from your inventory");
                    System.out.println("- quit: quit the game");
                    System.out.println("- help: displays this list of commands");
                    
                    break;
                default:
                    // unknown command returns idk
                    System.out.println("I am not sure how to do that");
            }

            // empty space between commands
            System.out.println("");

        }

        // close scanner when done
        inputScanner.close();

    }

}

