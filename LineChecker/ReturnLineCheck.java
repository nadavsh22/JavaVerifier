package oop.ex6.LineChecker;

import oop.ex6.main.Ex6Regex;
import oop.ex6.main.Ex6exception;
import oop.ex6.main.Section;

import java.io.IOException;
import java.util.regex.Matcher;

public class ReturnLineCheck implements LineChecker {
    /**
     * checks if return statement is valid
     *
     * @param line    the line to check
     * @param section the section
     * @return true if line is valid.
     * @throws IOException
     */
    public boolean checkValidity(String line, Section section) throws Ex6exception {
        String msg1 = "bad return statement";
        String msg2 = "return statement does not belong outside a method";
        if (section.getType().equals(Section.type2)) {
            throw new Ex6exception(msg2);
        }
        Matcher matcher = Ex6Regex.returnStatement.matcher(line);
        if (matcher.matches()) {
            return true;
        } else
            throw new Ex6exception(msg1);
    }
}
