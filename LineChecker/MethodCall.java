package oop.ex6.LineChecker;

import oop.ex6.main.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.*;

public class MethodCall extends VariableChecker implements LineChecker {
    public boolean checkValidity(String line, Section section) throws Ex6exception {
        Matcher match = Ex6Regex.leftSpaces.matcher(line);
        if (match.find()) {
            line = line.substring(match.end());
        }
        match = Ex6Regex.methodName.matcher(line);
        String splitter = ",";
        String msg1 = "a method can only be called from within another method";
        String msg2 = "this method does not exist";
        String msg3 = "bad method calling format";
        String msg4 = "missing commadot";
        String msg5 = "no such method";
        if (section.getType().equals(Section.type2)) {
            throw new Ex6exception(msg1);
        }
        String methodName = line;
        if (match.find()) {
            methodName = line.substring(match.start(), match.end());
            line = line.substring(match.end());
        }
        match = Ex6Regex.leftSpaces.matcher(methodName);
        if (match.find()) {
            methodName = methodName.substring(match.end());
        }
        match = Ex6Regex.rightSpaces.matcher(methodName);
        if (match.find()) {
            methodName = methodName.substring(0, match.start());
        }
        Section scope = section.getOuterScopes();
        while (!scope.getType().equals(Section.type2)) {
            scope = scope.getOuterScopes();
        }
        GeneralSection generalSection = (GeneralSection) scope;
        if (!hasMethod(methodName, generalSection.getMethods())) {
            throw new Ex6exception(msg2);
        }
        MethodSection method;
        method = null;
        for (int i = 0; i < generalSection.getMethods().size(); i++) {
            if (generalSection.getMethods().get(i).getName().equals(methodName)) {
                method = generalSection.getMethods().get(i);
                break;
            }
        }
        if (method.equals(null))
            throw new Ex6exception(msg5);

        match = Ex6Regex.lineEnd.matcher(line);
        if (match.find()) {
            line = line.substring(0, match.start());
        } else
            throw new Ex6exception(msg4);
        match = Ex6Regex.openBracket.matcher(line);
        if (match.find()) {
            line = line.substring(match.end());
        } else
            throw new Ex6exception("missing open brackets");
        match = Ex6Regex.closeBracket.matcher(line);
        if (match.find()) {
            line = line.substring(0, match.start());
        } else throw new Ex6exception(msg3);
        String[] params = line.split(splitter);
        for (int i = 0; i < params.length; i++) {
            match = Ex6Regex.leftSpaces.matcher(params[i]);
            if (match.find()) {
                params[i] = params[i].substring(match.end());
            }
            match = Ex6Regex.rightSpaces.matcher(params[i]);
            if (match.find()) {
                params[i] = params[i].substring(0, match.start());
            }
        }
        return paramCheck(params,section ,method);
    }

    /**
     * receives a list of methods and a method name and checks if a method by that name is in the list
     *
     * @param methodName a string representing a method name
     * @param methods    an ArrayList of methods
     * @return true if name is found in list, false otherwise
     */
    private boolean hasMethod(String methodName, ArrayList<MethodSection> methods) {
        for (int i = 0; i < methods.size(); i++) {
            if (methodName.equals(methods.get(i).getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * receives an array of params, and the method to compare with, and check if the order of calling and
     * type of param are correct. if params are variables check if they exist.
     *
     * @param params        an array of params
     * @param methodSection the method to compare with
     * @return true if params are legal
     * @throws Ex6exception
     */
    private boolean paramCheck(String[] params, Section section, MethodSection methodSection) throws
            Ex6exception {
        String[] paramTypes = new String[methodSection.getParams().size()];
        for (int i = 0; i < paramTypes.length; i++) {
            Variable param = isAssigned(params[i], section);
            String paramType=null;
            if (!Objects.equals(param,null)) {
                paramType = isAssigned(params[i], section).getType();
            } else
                compareType(methodSection.getParams().get(i).getType(), params[i], paramType);
        }

        return true;
    }

    /**
     * receives a String representing a type and a String representing a parameter and checks if they are of
     * the same type
     *
     * @param type  String representing a type
     * @param param a String representing a parameter
     * @return true if parameter is of types type
     * @throws Ex6exception
     */
    private boolean compareType(String type, String param, String paramType) throws Ex6exception {
        Matcher matcher;
        String msg1 = "a parameter isn't of valid type";
        String msg2 = "a paramter isn't the type he's supposed to be";
        if (!Objects.equals(paramType,null)){
            if (paramType.equals(type))
                return true;
            else
                throw new Ex6exception(msg2);
        }
        switch (type) {
            case Variable.BOOL:
                matcher = Ex6Regex.bool.matcher(param);
                break;
            case Variable.STRING:
                matcher = Ex6Regex.string.matcher(param);
                break;
            case Variable.CHAR:
                matcher = Ex6Regex.charFormat.matcher(param);
                break;
            case Variable.INT:
                matcher = Ex6Regex.integer.matcher(param);
                break;
            case Variable.DOUBLE:
                matcher = Ex6Regex.doubleFormat.matcher(param);
                break;
            default:
                throw new Ex6exception(msg1);
        }
        if (matcher.matches()) {
            return true;
        } else
            throw new Ex6exception(msg2);
    }
}
