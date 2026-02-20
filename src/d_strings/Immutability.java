package d_strings;

public class Immutability {
    public static void main(String[] args) {
        String first_name = "John";
        String second_name = "John";
        // variables first_name and second_name points to same object in String pool.
        String third_name = new String("John");
        // For variable third_name, new object will be created out of String pool.
        String fourth_name = new String("John");
        // For variable fourth_name also, NEW object will be created out of String pool.
        System.out.println(first_name == second_name); // compares by object/reference // true
        System.out.println(first_name == third_name);// false
        System.out.println(third_name == fourth_name);// false
        System.out.println("-----");

        System.out.println(first_name.equals(second_name)); // compares by value/content // true
        System.out.println(first_name.equals(third_name));// true
        System.out.println(third_name.equals(fourth_name));// true
        System.out.println("-----");

        String temp_name = "  John  ";
        String fifth_name = temp_name.trim();
        // Methods that “modify” actually return new strings
        System.out.println(first_name == fifth_name); // false
        System.out.println(first_name.equals(fifth_name)); // true
    }
}
