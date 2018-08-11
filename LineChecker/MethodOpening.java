package oop.ex6.LineChecker;

import oop.ex6.main.Ex6Regex;
import oop.ex6.main.Ex6exception;
import oop.ex6.main.Section;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;


/**
 * Created by nadav on 17-Jun-17.
 */
public class MethodOpening implements LineChecker {
    /**
     * splits a string to parts according to splitter and adds toAdd to each part
     *
     * @param line     the string to split
     * @param splitter the regex that splits
     * @param toAdd    the string to be added to each part
     * @return an array of strings
     */
    private String[] splitString(String line, String splitter, String toAdd) {
        String[] parts = line.split(splitter);
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i] + toAdd;
        }
        return parts;
    }

    /**
     * receives a line of method opening returns true if line is valid
     *
     * @param line
     * @param section the method
     * @return
     * @throws IOException
     */
    public boolean checkValidity(String line, Section section) throws Ex6exception {
        String msg1 = "bad method deceleration";
        String msg2 = "bad method name";
        String msg3 = "forgot braces";
        String msg4 = "bad param deceleration";
        Matcher match = Ex6Regex.methodType.matcher(line);
        if (match.find()) {
            line = line.substring(match.end());
        } else {
            throw new Ex6exception(msg1);
        }
        match = Ex6Regex.methodName.matcher(line);
        if (match.find()) {
            String name = line.substring(match.start(), match.end());
            section.setName(name);
            line = line.substring(match.end());
        } else {
            throw new Ex6exception(msg2);
        }
        match = Ex6Regex.openBracesChecker.matcher(line);
        if (match.find()) {
            line = line.substring(0, match.start());
        } else {
            throw new Ex6exception(msg3);
        }
        match = Ex6Regex.openBracket.matcher(line);
        if (match.find()) {
            line = line.substring(match.end());
        } else {
            throw new Ex6exception(msg4);
        }
        match=Ex6Regex.closeBracket.matcher(line);
        if (match.find()){
            line=line.substring(0,match.start());
        }else
            throw new Ex6exception(msg4);
        if (line.length() == 0)
            return true;
        String[] variableStrings = splitString(line, ",", ";");
        paramsCheck(variableStrings, section);
        return isTwice(variableStrings);
    }

    /**
     * receives a list of strings
     *
     * @param params
     * @param section
     * @return true if all of the strings are in valid variable declaration format
     * @throws IOException if format unvalid.
     */
    private void paramsCheck(String[] params, Section section) throws Ex6exception {
        CheckInitializingAndAssigning variableCheck = new CheckInitializingAndAssigning();
        variableCheck.checkAllValidates(params[0], section, true);
        int i = 1;
        while (i < params.length) {
            variableCheck.checkAllValidates(params[i], section, true);
            i++;
        }
    }

    /**
     * receives an array of strings of parameter declarations and checks if two params have the same name
     *
     * @param lines an array of strings
     * @return true if no two params have the same name.
     * @throws IOException
     */
    private boolean isTwice(String[] lines) throws Ex6exception {
        String msg1 = "at least two params have the same name";
        String splitter = " ";
        Set<String> names = new HashSet<>();
        for (int i = 0; i < lines.length; i++) {
            String[] words;
            Matcher matcher = Ex6Regex.lineEnd.matcher(lines[i]);
            if (matcher.find()) {
                lines[i] = lines[i].substring(0, matcher.start());
            }
            words = lines[i].split(splitter);
            String name = words[words.length - 1];
            if (names.contains(name)) {
                throw new Ex6exception(msg1);
            } else
                names.add(name);

        }
        return true;
    }

}


