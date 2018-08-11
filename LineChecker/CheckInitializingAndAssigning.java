package oop.ex6.LineChecker;

import oop.ex6.main.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;

public class CheckInitializingAndAssigning extends VariableChecker implements LineChecker {
    private String type;
    private boolean isFinal = false;
    private boolean isParameter;

    /**
     * @param line    line to be checked
     * @param section the section in which the line is
     * @return true if the line is written correctly
     * @throws Ex6exception Ex6exception bad format
     */
    public boolean checkValidity(String line, Section section) throws Ex6exception {

        String variableName;
        String variableValue;
        line = line.replaceAll(Ex6Regex.leftSpaces.pattern(), "");
        int lineLength = line.length();
        Matcher matcher = Ex6Regex.variableName.matcher(line);
        if (matcher.find()) { // check name legality
            variableName = line.substring(matcher.start(), matcher.end());
            if (section.checkVariable(variableName)) // check that variable doesnt exists already
                throw new Ex6exception("already exists such variable");
            line = line.substring(matcher.end(), lineLength);
            lineLength = line.length();
        } else
            throw new Ex6exception("bad variable name");
        matcher = Ex6Regex.endStatement.matcher(line); // look for comma dot
        if (matcher.matches())
            if (this.isFinal && !this.isParameter) //check final of parameter condition
                throw new Ex6exception("final variable was not assigned");
            else {
                Variable var = new Variable(variableName, this.type, null, this.isFinal, this.isParameter,
                        section); // create new variable
                if (this.isParameter)
                    section.addParameter(var);
                else
                    section.addVariable(var);
                return true;
            }
        matcher = Ex6Regex.assignment.matcher(line); // look for assignment sign
        if (matcher.find()) {
            if (this.isParameter)
                throw new Ex6exception("parameter cant be assigned");
            line = line.substring(matcher.end(), lineLength);
            lineLength = line.length();
        } else
            throw new Ex6exception("no assignment");
        matcher = Ex6Regex.endStatement.matcher(line);
        if (matcher.find())
            line = line.substring(0, matcher.start());
        else
            throw new Ex6exception("no end statement");
        matcher = Ex6Regex.value.matcher(line);
        if (matcher.find()) {
            variableValue = line.substring(matcher.start(), matcher.end());
            if (!Ex6Regex.isValue(variableValue)) { // check if a value of a variable
                Variable var = this.isAssigned(variableValue, section);
                if (!Objects.equals(var, null)) {
                    if (!var.isParameter()) { // case that variable is not a parameter
                        variableValue = var.getValue();
                        if (Objects.equals(variableValue, null))
                            throw new Ex6exception("variable not assigned");
                    } else
                        variableValue = var.getGenericType(var.getType()); // if parameter add generic value
                } else
                    throw new Ex6exception("variable to be assigned was only declared or does not exists");
            }
            Variable var = new Variable(variableName, this.type, variableValue, this.isFinal, this
                    .isParameter, section);
            if (this.isParameter)
                section.addParameter(var);
            else
                section.addVariable(var);
        } else {
            throw new Ex6exception("variable was not assigned by any value");
        }
        return true;
    }

    /**
     * handle one or more initialization in one line
     *
     * @param line    the line to check
     * @param section the section in which the line is
     * @param isParam are the variables parameters
     * @throws Ex6exception bad format
     */
    public void checkAllValidates(String line, Section section, boolean isParam) throws Ex6exception {
        this.isFinal = false;
        this.isParameter = isParam;
        ArrayList<String> decelerations = new ArrayList<String>();
        int lineLength = line.length();
        Matcher matcher = Ex6Regex.initializeFinalVariable.matcher(line); // look for final sign
        if (matcher.find()) {
            line = line.substring(matcher.end(), lineLength);
            this.isFinal = true;
        }
        matcher = Ex6Regex.type.matcher(line); // get the type of the variables
        if (matcher.find()) {
            this.type = line.substring(matcher.start(), matcher.end());
            this.type = this.type.replaceAll(Ex6Regex.leftSpaces.pattern(), "");
            this.type = this.type.replaceAll(Ex6Regex.rightSpaces.pattern(), "");
            lineLength = line.length();
            line = line.substring(matcher.end(), lineLength);

        } else {
            throw new Ex6exception("not a legal type");
        }
        matcher = Ex6Regex.endStatement.matcher(line);
        if (!matcher.find())
            throw new Ex6exception("no end statement");
        line = line.substring(0, matcher.start());
        line += ",";
        matcher = Ex6Regex.separate.matcher(line);
        int start = 0;
        while (matcher.find()) { // separate different initializations
            String newDeceleration = line.substring(start, matcher.start()) + ";";
            decelerations.add(newDeceleration);
            start = matcher.end();
        }
        for (String str : decelerations) {
            this.checkValidity(str, section);
        }

    }
}

