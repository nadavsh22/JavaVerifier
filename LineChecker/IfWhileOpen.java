package oop.ex6.LineChecker;

import oop.ex6.main.*;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;

/**
 * Created by nadav on 17-Jun-17.
 */
public class IfWhileOpen extends VariableChecker implements LineChecker {
    /**
     * checks if the line is a valid if/while block opening line
     *
     * @param line
     * @param section the section the line appears in
     * @return true if line is valid
     * @throws IOException
     */
    public boolean checkValidity(String line, Section section) throws Ex6exception {
        String msg1 = "if/while lock wasn't declared correctly";
        String msg2 = "if/while block opened outside of method";
        String msg3 = "what about brackets?";
        if (section.getType().equals(section.type2)) {
            throw new Ex6exception(msg2);
        }
        Matcher matcher = Ex6Regex.ifWhileOpenForm.matcher(line);
        if (matcher.find()) {
            line = line.substring(matcher.end());
        } else
            throw new Ex6exception(msg1);
        matcher = Ex6Regex.openBracesChecker.matcher(line);
        if (matcher.find()) {
            line = line.substring(0, matcher.start());
        }
        matcher = Ex6Regex.openBracket.matcher(line);
        if (matcher.find()) {
            line = line.substring(matcher.end());
        } else
            throw new Ex6exception(msg3);
        matcher = Ex6Regex.closeBracket.matcher(line);
        if (matcher.find()) {
            line = line.substring(0, matcher.start());
        } else
            throw new Ex6exception(msg3);
        String[] parts = line.split(Ex6Regex.splitAndOr.pattern());
        return checkExpression(parts, section);

    }

    /**
     * check if an array of strings holds valid boolean expressions
     *
     * @param parts   array of string
     * @param section the section the block belongs to
     * @return true if all expressions are valid
     * @throws IOException
     */
    private boolean checkExpression(String[] parts, Section section) throws Ex6exception {
        String msg = "not a boolean expression";
        for (int i = 0; i < parts.length; i++) {
            boolean flag = true;
            Matcher boolMatch = Ex6Regex.boolCall.matcher(parts[i]);
            Matcher variableMatch = Ex6Regex.variableNameCall.matcher(parts[i]);
            if (boolMatch.matches()) {
                flag = true;
            } else if (variableMatch.matches()) {
                if (variableFinder(parts[i], section))
                    flag = true;
            } else {
                flag = false;
            }
            if (flag == false)
                throw new Ex6exception(msg);
        }
        return true;
    }

    /**
     * checks if a variable appears in the containing scopes and whether it's type is boolean
     *
     * @param variableName
     * @param section      the section the block belongs to
     * @return true if variable exists and is boolean
     * @throws IOException
     */
    private boolean variableFinder(String variableName, Section section) throws Ex6exception {
        String msg1 = "the argument isn't boolean";
        String msg2 = "the variable u used wasn't declared";
        Variable found = isAssigned(variableName, section);
        if (found.equals(null) || Objects.equals(found.getValue(), null)) {
            throw new Ex6exception(msg2);
        }
        String typeFound = found.getType();
        if (typeFound.equals(Variable.INT) || typeFound.equals(Variable.BOOL) ||
                typeFound.equals(Variable.INT)) {
            return true;
        } else
            throw new Ex6exception(msg1);
    }
}


