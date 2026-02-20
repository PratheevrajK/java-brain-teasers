package b_array_vs_list;

import java.util.Arrays;
import java.util.List;

public class ArrayVsList {
    public static void main(String[] args) {
        String[] array = new String[]{"First", "Second", "Third"};
        List<String> list = Arrays.asList(array); // list variable is an array with List wrapper applied.
        System.out.println("Array:" + Arrays.toString(array));
        System.out.println("List: " + list);

        //Updating the array will update list as well, as both reference are same here.
        System.out.println("Updating element in the Array, updates List too.");
        array[1] = "Two";
        System.out.println("Array:" + Arrays.toString(array));
        System.out.println("List: " + list);

        //Updating the list will update array as well, as both reference are same here.
        System.out.println("Updating element in the List, updates Array too.");
        list.set(0, "One");
        System.out.println("Array:" + Arrays.toString(array));
        System.out.println("List: " + list);

        try{
            //Adding new element to the array will NOT work, as arrays are not resizable.
            System.out.println("Trying to add new element to the array.");
            array[3] = "Four";//java.lang.ArrayIndexOutOfBoundsException: Index 3 out of bounds for length 3
        } catch (Exception ex) {
            System.out.println(ex);
        }

        try{
            //Adding new element to the list will NOT work, as list is basically an array here.
            System.out.println("Trying to add new element to the list.");
            list.add("Four");//java.lang.UnsupportedOperationException
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
}
