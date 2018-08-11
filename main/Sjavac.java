package oop.ex6.main;

import java.io.IOException;
import java.util.Objects;

public class Sjavac {
    /**
     * receives argument line and checks if given file path is a valid s-java file and if the code is
     * compatible with language rules. prints 0 if valid, 1 and detail message if the code in file isn't valid
     * s-java, and 2 for any other error (i.e wrong path)
     * @param args
     */
    public static void main(String[] args) {
        FileReader reader = new FileReader(args[0]);
        try {
            reader.readLines();
        } catch (Ex6exception e) {
            System.out.println(1);
            System.out.println(e.getMessage());
            return;
        } catch (IOException e) {
            System.out.println(2);
            System.out.println(e.getMessage());
            return;

        }
        Section GS = reader.getGeneralSection();
        if (!Objects.equals(GS, null)) {
            try {
                GS.checkLines();
            } catch (Ex6exception e) {
                System.out.println(1);
                System.out.println(e.getMessage());
                return;
            }
        }
        System.out.println(0);


    }
}
