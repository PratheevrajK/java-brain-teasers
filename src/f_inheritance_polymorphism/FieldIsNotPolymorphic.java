package f_inheritance_polymorphism;

public class FieldIsNotPolymorphic {
    public static void main(String[] args) {
        Dog puppy = new Dog(15, "Puppy", 10);
        System.out.println("puppy.age: " + puppy.age);
        executeBehaviours(puppy);
    }

    public static void executeBehaviours(Animal animal) {//Implicit Upcasting.
        animal.makeNoise(); //Dog class method would be executed due to runtime Polymorphism, as object is Dog.
        System.out.println("animal.age: " + animal.age);//As Field access is not polymorphic, age value in Animal class would get printed, because variable type is Animal.
    }
}

abstract class Animal {
    protected int age = 1;
    protected String type;
    public double weight;

    public Animal(String type, double weight) {
        this.type = type;
        this.weight = weight;
    }

    public abstract void makeNoise();
}

class Dog extends Animal{
    int age;
    public Dog(int age, String type, double weight) {
        super(type, weight);
        this.age = age;
    }
    @Override
    public void makeNoise() {
        System.out.println("Lol!");
    }
}

