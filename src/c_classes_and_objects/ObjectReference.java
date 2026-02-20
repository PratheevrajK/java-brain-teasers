package c_classes_and_objects;

public class ObjectReference {
    public static void main(String[] args) {
        House house1 = new House("blue");
        House house2 = house1; // reference of House object is assigned to house2 variable. So, both house1 and house2 refers to same object.

        System.out.println(house1.getColour()); // blue
        System.out.println(house2.getColour()); // blue

        house2.setColour("yellow");
        System.out.println(house1.getColour()); // yellow
        System.out.println(house2.getColour()); // yellow

        House house3 = new House("green");
        house2 = house3;

        System.out.println(house3.getColour()); // green
        System.out.println(house2.getColour()); // green
        System.out.println(house1.getColour()); // yellow
    }
}

class House {
    private String colour;

    public House(String colour) {
        this.colour = colour;
    }

    public String getColour() {
        return colour;
    }
    public void setColour(String colour) {
        this.colour = colour;
    }
}
