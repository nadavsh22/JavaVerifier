package oop.ex6.LineChecker;

import oop.ex6.main.Ex6Regex;
import oop.ex6.main.Ex6exception;
import oop.ex6.main.Section;

import java.io.IOException;
import java.util.regex.Matcher;

/**
 * Created by nadav on 17-Jun-17.
 */
public class CloseCheck implements LineChecker {
    /**
     * checks if line is valid
     *
     * @param line    a string representing code line
     * @param section the section the code
     * @return
     * @throws IOException
     */
    public boolean checkValidity(String line, Section section) throws Ex6exception {
        String msg1 = "closing braces must be alone in line";
        Matcher matcher = Ex6Regex.closeBracesChecker.matcher(line);
        if (matcher.matches()) {
            return true;
        } else
            throw new Ex6exception(msg1);
    }
}
