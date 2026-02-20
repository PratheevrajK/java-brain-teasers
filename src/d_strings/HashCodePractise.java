package d_strings;
//hashCode() of a String is calculated based on its content, not its memory location or how it was created.
public class HashCodePractise {
    public static void main(String[] args) {
        String myString = "Raj";
        System.out.println(myString.hashCode()); // 81915

        String myString2 = new String("Raj");
        System.out.println(myString2.hashCode()); // 81915

        System.out.println("Raj".hashCode()); // 81915

        String s1 = null;
        System.out.println(s1.hashCode()); // NPE
    }
}
