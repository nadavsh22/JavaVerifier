package oop.ex6.main;

import java.util.regex.*;

/**
 * a class holding all the regular expressions used in the code
 */
public class Ex6Regex {
    public static final Pattern commentPattern = Pattern.compile("^[//]{2}.*");
    public static final Pattern string = Pattern.compile("^\".*\"$");
    public static final Pattern integer = Pattern.compile("[-]?\\d*");
    public static final Pattern doubleFormat = Pattern.compile("[-]?\\d+[.]?\\d*");
    public static final Pattern charFormat = Pattern.compile("^'.'$");
    public static final Pattern bool = Pattern.compile("true|false|\\d+[.]?\\d*");
    public static final Pattern type = Pattern.compile("^\\s*int\\s*|^\\s*String\\s*|^\\s*boolean\\s*|^\\s*double\\s*|^\\s*" +
            "char\\s*");
    public static final Pattern variableName = Pattern.compile("^_{1}[\\w]+|^[a-zA-Z][\\w]*");
    public static final Pattern initializeFinalVariable = Pattern.compile("^\\s*final +");
    public static final Pattern endStatement = Pattern.compile(";\\s*$");
    public static final Pattern assignment = Pattern.compile("^\\s*=\\s*");
    public static final Pattern value = Pattern.compile(".+");
    public static final Pattern methodChecker = Pattern.compile("^\\s*void");
    public static final Pattern openBracesChecker = Pattern.compile("\\s*\\{\\s*$");
    public static final Pattern closeBracesChecker = Pattern.compile("^\\s*}\\s*");
    public static final Pattern separate = Pattern.compile("\\s*,\\s*");
    public static final Pattern ifWhileOpenForm = Pattern.compile("^\\s*if\\s*|^\\s*while\\s*");
    //
    public static final Pattern blockClose = Pattern.compile("^\\s*}\\s*");
    ///
    public static final Pattern emptyLine = Pattern.compile("\\s*");
    public static final Pattern methodType = Pattern.compile("^(\\s*void{1}\\s+)");
    public static final Pattern methodName = Pattern.compile("^[a-zA-Z][\\w]*");
    ////
    public static final Pattern openBracket = Pattern.compile("^\\s*\\(\\s*");
    public static final Pattern closeBracket = Pattern.compile("\\s*\\)\\s*$");
    public static final Pattern leftSpaces = Pattern.compile("^\\s+");
    public static final Pattern rightSpaces = Pattern.compile("\\s+$");
    public static final Pattern splitAndOr = Pattern.compile("\\s*&&\\s*|\\s*\\|\\|\\s");
    public static final Pattern lineEnd = Pattern.compile("\\s*;\\s*$");
    public static final Pattern returnStatement = Pattern.compile("^\\s*return\\s*;\\s*");
    public static final Pattern methodCall = Pattern.compile("\\s*[a-zA-Z][\\w]*\\s*\\(");
    public static final Pattern variableNameCall = Pattern.compile("(:?^_{1}[\\w]+|^[^_\\W][\\w]*)$");
    public static final Pattern boolCall = Pattern.compile("^true|^false|^\\d+[.]?\\d*");

    /**
     * receives an input value and checks if it is of valid type
     * @param value String representing value
     * @return true if input is a valid value, false otherwise
     */
    public static boolean isValue(String value) {
        Matcher matcherString = Ex6Regex.string.matcher(value);
        Matcher matcherInt = Ex6Regex.integer.matcher(value);
        Matcher matcherChar = Ex6Regex.charFormat.matcher(value);
        Matcher matcherDouble = Ex6Regex.doubleFormat.matcher(value);
        Matcher matcherBoolean = Ex6Regex.bool.matcher(value);
        return (matcherChar.matches() || matcherDouble.matches() || matcherInt.matches() || matcherString.matches
                () || matcherBoolean.matches());
    }


}
