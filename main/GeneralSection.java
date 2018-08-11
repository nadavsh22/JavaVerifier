package oop.ex6.main;


import oop.ex6.LineChecker.MethodSection;
import oop.ex6.LineClassifier;

import java.util.*;

public class GeneralSection extends Section {
    private ArrayList<MethodSection> methods;

    /**
     * @param lines the lines to add to the section
     * @param type  the type of the section
     * @throws Ex6exception bad format
     */
    public GeneralSection(List<String> lines, String type) throws Ex6exception {
        super(lines, type);
        this.setScope(null);
    }

    /**
     * go over the lines and check their correctness
     *
     * @throws Ex6exception bad format
     */
    public void checkLines() throws Ex6exception {
        LineClassifier LC = new LineClassifier(this);
        int i = 0; // number of method
        for (String line : this.lines) {
            LC.classify(line);
        }
        for (MethodSection method : this.methods) { // check methods correctness
            method.checkLines();
        }

    }

    /**
     * unsupported method
     *
     * @param var variable
     */
    public void addParameter(Variable var) {
        System.out.println("operation not supported");
    }

    /**
     * add methods to the global scope
     *
     * @param methods the methods to add
     */
    public void addMethods(ArrayList<MethodSection> methods) {
        for (MethodSection method : methods)
            method.setScope(this);
        this.methods = methods;
    }

    /**
     * @return the general section methods
     */
    public ArrayList<MethodSection> getMethods() {
        return this.methods;
    }

    /**
     * un supported method
     *
     * @param name string
     * @return
     */
    public Variable getParameter(String name) {
        System.out.println("operation not supported");
        return null;
    }

    /**
     * @param variable a string representing a variable name
     * @return the variable asked for
     */
    public Variable getVariable(String variable) {
        return variables.get(variable);
    }


}
