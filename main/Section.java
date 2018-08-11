package oop.ex6.main;

import oop.ex6.LineChecker.LineChecker;
import oop.ex6.LineChecker.MethodOpening;
import oop.ex6.main.Ex6exception;
import oop.ex6.main.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class Section {
    final static public String type1 = "Method", type2 = "GeneralSection", type3 = "Block";
    protected List<String> lines;
    protected HashMap<String, Variable> variables = new HashMap<>();//all the variables in the current scope
    private Section outerScope;
    protected ArrayList<Variable> params = new ArrayList<>();
    private String name;
    private String type;

    /**
     * constructor receiving a list of lines(Strings) and a string representing the section type
     * @param lines
     * @param type
     * @throws Ex6exception
     */
    public Section(List<String> lines, String type) throws Ex6exception {
        this.lines = lines;
        this.type = type;
        if (!type.equals(type1)) {
            this.name = null;
        } else {
            LineChecker checker = new MethodOpening();
            checker.checkValidity(lines.get(0), this);
        }
    }

    /**
     * iterate through lines checking if they are valid
     *
     * @throws Ex6exception if an unvalid line is found
     */
    public abstract void checkLines() throws Ex6exception; //

    /**
     *
     * @return the sections outer scope, null if called on general section
     */
    public Section getOuterScopes() {
        return this.outerScope;
    }

    /**
     * receives a section object, and sets the sections outer scope to that value
     * @param section
     */
    public void setScope(Section section) {
        this.outerScope = section;
    }

    /**
     * receives a variable name and checks if it is in the sections variables
     * @param variable a string representing a variable name
     * @return true if variable is in sections variables, false otherwise
     */
    public boolean checkVariable(String variable) {
        boolean value = false;
        value = !Objects.equals(this.variables.get(variable), null);
        for (Variable param : this.params) {
            if (param.getName().equals(variable))
                value = true;
        }
        return value;
    }

    /**
     * returns the variable in sections variables with the same name as given string
     * @param variable a string representing a variable name
     * @return the variable named as variable
     */
    public Variable getVariable(String variable) {
        return this.variables.get(variable);
    }

    /**
     * adds the given variable object to sections variables
     * @param variable
     */
    public void addVariable(Variable variable) {
        this.variables.put(variable.getName(), variable);
    }

    /**
     * sets object name to given value
     *
     * @param name string
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets the type of section
     *
     * @param type
     */
    public void setType(String type) {
        if (type.equals(type1) || type.equals(type2) || type.equals(type3)) {
            this.type = type;
        }
    }

    /**
     *
     * @return a string stating sections type
     */
    public String getType() {
        return this.type;
    }

    /**
     * a string with sections name
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * adds the given variable object to the sections parameter collection
     * @param var variable
     */
    public abstract void addParameter(Variable var);

    /**
     * receives a String of parameter name returns parameter with same name
     * @param name string
     * @return the parameter with the same name as given string
     */
    public abstract Variable getParameter(String name);

    /**
     * prints sections parameters names and values
     */
    public void printParameters() {
        for (Variable var : this.params) {
            System.out.println(var.getName());
            System.out.println(var.getType());
        }
    }

    /**
     *
     * @return the list holding all code lines (strings)
     */
    public List<String> getLines() {
        return this.lines;
    }

}
