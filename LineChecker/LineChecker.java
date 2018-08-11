package oop.ex6.LineChecker;

import oop.ex6.main.Ex6exception;
import oop.ex6.main.Section;

public interface LineChecker {
    /**
     *
     * @param line the line to check
     * @param section the section in which the line is
     * @return true if the line was written correctly
     * @throws Ex6exception bad format
     */
    public boolean checkValidity(String line, Section section) throws Ex6exception;
}
