package c_classes_and_objects;

import java.util.ArrayList;
import java.util.List;

public class ListOfObject {
    public static void main(String[] args) {
        Employee e1 = new Employee("e1", "Pratheev", 25);
        Employee e2 = new Employee("e2", "Raj", 18);
        List<Employee> l1 = new ArrayList<>();
        l1.add(e1);
        l1.add(e2);

        List<Employee> l2 = new ArrayList<>(l1); // Shallow copy. Both list refers to same elements.
        System.out.println(l1);//[Employee@8efb846, Employee@2a84aee7] // (printed without toString implementation)
        System.out.println(l2);//[Employee@8efb846, Employee@2a84aee7] // references are same in both list. Updating an Employee object will reflect in both lists.
        //This can be resolved either by creating Copy constructor or deep-copy method in Employee class.
        List<Employee> l3 = l1.stream().map(Employee::new).toList(); //creating a new list with new Employee objects.
        e1.setAge(40);
        System.out.println(l1);//[Employee{id='e1', name='Pratheev', age=40}, Employee{id='e2', name='Raj', age=18}]
        System.out.println(l2);//[Employee{id='e1', name='Pratheev', age=40}, Employee{id='e2', name='Raj', age=18}]
        System.out.println(l3);//[Employee{id='e1', name='Pratheev', age=25}, Employee{id='e2', name='Raj', age=18}]
    }
}

class Employee {
    private String id;
    private String name;
    private int age;

    public Employee(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // Copy constructor (deep copy for current fields)
    public Employee(Employee employee) {
        this.id = employee.id;
        this.name = employee.name;
        this.age = employee.age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}