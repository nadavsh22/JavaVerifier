package oop.ex6.main;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nadav on 16-Jun-17.
 */
public class Variable {
    private String name;
    private String type;
    private String value;
    private boolean isFinal;
    private boolean isParam;
    private Section scope;
    public static final String BOOL = "boolean";
    public static final String INT = "int";
    public static final String DOUBLE = "double";
    public static final String CHAR = "char";
    public static final String STRING = "String";
    private int lineInitialized;

    /**
     * @param name
     * @param type
     * @throws Ex6exception if one of the parameters given doesn't fit
     */
    public Variable(String name, String type) throws Ex6exception {
        this.name = checkeName(name);
        this.type = checkType(type);
        this.lineInitialized = lineInitialized;
        this.isFinal = false;
        this.isParam = false;
    }

    /**
     * @param name    of variable
     * @param type    of variable
     * @param value   of variable
     * @param isFinal whether variable value is final or not
     * @throws Ex6exception
     */
    public Variable(String name, String type, String value, boolean isFinal, boolean isParam, Section scope)
            throws
            Ex6exception {
        this.name = checkeName(name);
        this.type = checkType(type);
        if (!Objects.equals(value, null))
            this.value = checkVal(value, type);
        else
            this.value = value;
        this.isFinal = isFinal;
        this.isParam = isParam;
        this.scope = scope;
        this.lineInitialized = lineInitialized;
    }

    /**
     * copy constructor for variable created in an inner scope
     *
     * @param variable the variable to copy
     * @param section  the section the new Variable is created in
     */
    public Variable(Variable variable, Section section) {
        this.name = variable.getName();
        this.scope = section;
        this.value = variable.getValue();
        this.isFinal = false;
        this.type = variable.getType();
        this.isParam = false;
    }

    /**
     * @return if variable is final or not
     */
    public boolean getIsFinal() {
        return this.isFinal;
    }

    /**
     * @return the variable name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the variable type
     */
    public String getType() {
        return this.type;
    }

    /**
     * @return the variables value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * receives a new variable value and changes it if it is correct
     *
     * @param value
     * @throws Ex6exception
     */
    public void setValue(String value) throws Ex6exception {
        String msg1 = "variable is final and can't be changed";
        if (this.isFinal) {
            throw new Ex6exception(msg1);
        } else {
            this.value = checkVal(value, this.type);
        }
    }

    /**
     * checks if name is in a valid format
     *
     * @param name
     * @return the name if it is valid
     * @throws Ex6exception if name format unvalid
     */
    private String checkeName(String name) throws Ex6exception {
        String msg = "bad variable name";
        Pattern variableName = Pattern.compile("^_{1}[\\w]+|^[^_\\W][\\w]*");
        Matcher match = variableName.matcher(name);
        if (match.matches()) {
            return name;
        } else {
            throw new Ex6exception(msg);
        }
    }

    /**
     * checks if type of variable is valid
     *
     * @param type
     * @return a string representing the type if its valid
     * @throws Ex6exception if type is unvalid
     */
    private String checkType(String type) throws Ex6exception {
        String msg = "bad variable type";
        Pattern pat = Pattern.compile("int|String|boolean|double|char");
        Matcher match = pat.matcher(type);
        if (match.matches()) {
            return type;
        } else {
            throw new Ex6exception(msg);
        }
    }

    /**
     * checks if value fits type
     *
     * @param value string representing value
     * @param type  string representing type
     * @return type if it fits value
     * @throws Ex6exception if value and type don't fit
     */
    private String checkVal(String value, String type) throws Ex6exception {
        String msg1 = "something wrong with type";
        String msg2 = "the value did not match the type";
        Matcher match;
        switch (type) {
            case Variable.STRING:
                match = Ex6Regex.string.matcher(value);
                break;
            case INT:
                match = Ex6Regex.integer.matcher(value);
                break;
            case DOUBLE:
                match = Ex6Regex.doubleFormat.matcher(value);
                break;
            case CHAR:
                match = Ex6Regex.charFormat.matcher(value);
                break;
            case BOOL:
                match = Ex6Regex.bool.matcher(value);
                break;
            default:
                throw new Ex6exception(msg1);
        }
        if (match.matches()) {
            return value;
        } else {
            throw new Ex6exception(msg2);
        }
    }

    public boolean isParameter() {
        return this.isParam;
    }

    public Section getScope() {
        return this.scope;
    }

    public String getGenericType(String type) throws Ex6exception {
        switch (type) {
            case BOOL:
                return "true";
            case INT:
                return "9";
            case CHAR:
                return "'a'";
            case STRING:
                return "\"a\"";

            case DOUBLE:
                return "1.1";
            default:
                throw new Ex6exception("bad type");
        }

    }
}

