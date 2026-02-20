package e_try_catch_finally;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TryWithoutCatch {
    public static void main(String[] args) {
        try {
            System.out.println("Inside try block");
            int result = 10 / 0; // This will throw ArithmeticException
        } finally {
            System.out.println("Finally block executed");
        }
        // Program will terminate after exception if not caught
        //Inside try block
        //Finally block executed
        //  Exception in thread "main" java.lang.ArithmeticException: / by zero
        //	at e_try_catch_finally.TryWithoutCatch.main(TryWithoutCatch.java:7)
    }
}

class TryWithResourcesExample {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("test.txt"))) {
            System.out.println(br.readLine());
        }
        // No catch block, but resources are closed automatically
    }
}