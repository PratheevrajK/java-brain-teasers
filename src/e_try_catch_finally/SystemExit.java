package e_try_catch_finally;

public class SystemExit {
    public static void main(String[] args) {
        try {
            System.out.println("Inside try block.");
            int result = 10 / 0; // This will throw ArithmeticException.
        } catch(ArithmeticException ex) {
            System.out.println("Inside catch block. " + ex.toString());
            System.exit(0);
        } finally {
            System.out.println("Inside Finally block.");
        }
        //Inside try block.
        //Inside catch block. java.lang.ArithmeticException: / by zero
    }
}
