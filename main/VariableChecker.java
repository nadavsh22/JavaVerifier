package oop.ex6.main;

import java.util.Objects;

public abstract class VariableChecker {
    /**
     * @param name    name of a variable
     * @param section the section in which the variable was declared
     * @return the variable
     */
    public Variable isAssigned(String name, Section section) {
        Variable var = section.getVariable(name); // if exists get variable from the section
        while (Objects.equals(var, null) && !Objects.equals(section, null) && !Objects.equals(section
                .getOuterScopes(), null)) { // check if the variable was not found and the section and its
            // outerscope isnt null
            if (Objects.equals(var, null) && section.getType().equals(Section.type1)) {
                var = section.getParameter(name); // if we reach a method section look for the variable in
                // the method parameters
                if (!Objects.equals(var, null))// && (!Objects.equals(var.getValue(), null)|| var.isParameter()))
                    return var;
            }
            if (!Objects.equals(var, null))
                return var;
            section = section.getOuterScopes();
            var = section.getVariable(name);


        }
        var = section.getVariable(name);
        if (!Objects.equals(var, null))
            return var;

        return null;

    }
}
