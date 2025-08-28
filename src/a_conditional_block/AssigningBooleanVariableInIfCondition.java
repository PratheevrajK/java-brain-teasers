package a_conditional_block;

public class AssigningBooleanVariableInIfCondition {
    public static void main(String[] args){
        System.out.println("Hello, Pratheev!");
        boolean isCar = true;
        if(isCar = false){ // Value assigned to isCar is treated as Condition logic.
            System.out.print("Inside first IF block!");
        }
        if(isCar = true){
            System.out.print("Inside second IF block!");
        }
    }
}
