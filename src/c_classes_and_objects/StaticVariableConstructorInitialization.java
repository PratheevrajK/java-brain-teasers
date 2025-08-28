package c_classes_and_objects;

public class StaticVariableConstructorInitialization {
    public static void main(String[] args) {
        Dog tommy = new Dog("tommy");
        Dog lab = new Dog("lab");

        tommy.printName(); // lab
        lab.printName(); // lab
    }
}

class Dog {
    private String name; // name is a static variable.

    public Dog(String name) {
        this.name = name;
    }
    public void printName() {
        System.out.println(name);
    }
}
