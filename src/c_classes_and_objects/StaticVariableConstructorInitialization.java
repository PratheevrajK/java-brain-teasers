package c_classes_and_objects;
//A static field belongs to the class, not to any particular instance. That means:
//  There is only one copy of Dog.name for the entire Dog class.
//  Every time you assign to this.name (which actually resolves to Dog.name because it’s static), you’re overwriting the single shared value.
public class StaticVariableConstructorInitialization {
    public static void main(String[] args) {
        Dog tommy = new Dog("tommy");
        Dog lab = new Dog("lab");

        tommy.printName(); // lab
        lab.printName(); // lab
    }
}

class Dog {
    private static String name; // name is a static variable.

    public Dog(String name) {
        this.name = name;
    }
    public void printName() {
        System.out.println(name);
    }
}
