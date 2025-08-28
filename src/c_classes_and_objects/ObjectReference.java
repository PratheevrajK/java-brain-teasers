package c_classes_and_objects;

public class ObjectReference {
    public static void main(String[] args) {
        House blueHouse = new House("blue");
        House anotherHouse = blueHouse; // reference of House object is assigned to anotherHouse variable. So, both blueHouse and anotherHouse refers to same object.

        System.out.println(blueHouse.getColour()); // blue
        System.out.println(anotherHouse.getColour()); // blue

        anotherHouse.setColour("yellow");
        System.out.println(blueHouse.getColour()); // yellow
        System.out.println(anotherHouse.getColour()); // yellow

        House greenHouse = new House("green");
        anotherHouse = greenHouse;

        System.out.println(greenHouse.getColour()); // green
        System.out.println(anotherHouse.getColour()); // green
        System.out.println(blueHouse.getColour()); // yellow
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
