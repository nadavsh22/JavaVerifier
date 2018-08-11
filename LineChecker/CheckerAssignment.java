package oop.ex6.LineChecker;

import oop.ex6.main.*;

import java.util.*;
import java.util.regex.*;


public class CheckerAssignment extends VariableChecker implements LineChecker {

    /**
     * @param line    line to be checked
     * @param section the section in which the line is
     * @return true if the line is written correctly
     * @throws Ex6exception bad format
     */
    public boolean checkValidity(String line, Section section) throws Ex6exception {
        String variableName;
        line = line.replaceAll(Ex6Regex.leftSpaces.pattern(), ""); //remove spaces
        int lineLength = line.length();
        Matcher matcher = Ex6Regex.variableName.matcher(line);
        if (matcher.find()) { // check for a valid name
            variableName = line.substring(matcher.start(), matcher.end());
            line = line.substring(matcher.end(), lineLength);
            lineLength = line.length();
        } else
            throw new Ex6exception("not a legal variable name");
        Variable varToAssign = this.isAssigned(variableName, section); // check if variable exists
        if (Objects.equals(varToAssign, null))
            throw new Ex6exception("variable does not exists");
        if (varToAssign.getIsFinal()) // final variable cant be assigned
            throw new Ex6exception("final!");
        if (!Objects.equals(varToAssign.getScope(), section)) // check if variabe is defined in this scope
            varToAssign = new Variable(varToAssign, section);


        matcher = Ex6Regex.assignment.matcher(line);
        if (matcher.find()) { // check for assignment sign
            line = line.substring(matcher.end(), lineLength);
            lineLength = line.length();
        } else
            throw new Ex6exception("no assignment was done");
        matcher = Ex6Regex.endStatement.matcher(line); // look for comma dot
        if (matcher.find())
            line = line.substring(0, matcher.start());
        else
            throw new Ex6exception("no end statement was found");
        matcher = Ex6Regex.value.matcher(line);
        if (matcher.find()) {
            line = line.substring(matcher.start(), matcher.end());
            if (!Ex6Regex.isValue(line)) { // check if legal value or a variable name
                Variable var = this.isAssigned(line, section);
                if (!Objects.equals(var, null)) { // check if assigned
                    if (!var.isParameter()) { // check if parameter and assign accordingly
                        line = var.getValue();
                        varToAssign.setValue(line);
                        if (!section.checkVariable(varToAssign.getName()))
                            section.addVariable(varToAssign);
                    } else {
                        varToAssign.setValue(var.getGenericType(var.getType()));

                    }
                } else
                    throw new Ex6exception("variable to be assigned was only declared or does not exists");
            } else {
                varToAssign.setValue(line);
                if (!section.checkVariable(varToAssign.getName())) // check if variable is not in variables
                    // list of the section
                    section.addVariable(varToAssign);
            }

        }
        return true;

    }


}

