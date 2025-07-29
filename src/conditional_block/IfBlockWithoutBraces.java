package conditional_block;
//Take away: Always use curly braces{} for opening and closing the IF block.
public class IfBlockWithoutBraces {
    public static void main(String[] args) {
        printAlienOrNot(true);
        printAlienOrNot(false);
        printAlienOrNotWithClosedIfBlock(true);
        printAlienOrNotWithClosedIfBlock(false);
        printAlienOrNotWithMultipleStatements(true);
        printAlienOrNotWithMultipleStatements(false);
    }

    public static void printAlienOrNot(boolean isAlien) {
        System.out.println("Inside printAlienOrNot() method.");
        if(isAlien)
            System.out.println("It is an alien!");
    }
    public static void printAlienOrNotWithClosedIfBlock(boolean isAlien) {
        System.out.println("Inside printAlienOrNotWithClosedIfBlock() method.");
//        If block is closed with semicolon and so following print statement is treated as outside if blocl.
        if(isAlien);
            System.out.println("It is an alien!");
    }
    public static void printAlienOrNotWithMultipleStatements(boolean isAlien) {
        System.out.println("Inside printAlienOrNotWithMultipleStatements() method.");
//        Only first statement is counted as inside If block, other following statements are treated outside of If block.
        if(isAlien)
            System.out.println("It is an alien!");
            System.out.println("And I'm scared of alien!");
    }
}
