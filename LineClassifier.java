package oop.ex6;

import oop.ex6.LineChecker.*;
import oop.ex6.main.Ex6Regex;
import oop.ex6.main.Ex6exception;
import oop.ex6.main.Section;

import java.util.regex.Matcher;

public class LineClassifier {
    private Section section;

    public LineClassifier(Section section) {
        this.section = section;
    }

    public void classify(String line) throws Ex6exception {
        LineChecker checker;
        Matcher matcher = Ex6Regex.emptyLine.matcher(line);
        if (matcher.matches()) {
            return;
        }
        matcher = Ex6Regex.ifWhileOpenForm.matcher(line);
        if (matcher.find()) {
            checker = new IfWhileOpen();
            checker.checkValidity(line, section);
            return;
        }
        matcher = Ex6Regex.methodCall.matcher(line);
        if (matcher.find()) {
            checker = new MethodCall();
            checker.checkValidity(line, section);
            return;
        }
        matcher = Ex6Regex.commentPattern.matcher(line);
        if (matcher.find()) {
            return;
        }
        matcher = Ex6Regex.blockClose.matcher(line);
        if (matcher.matches()) {
            return;
        }
        matcher = Ex6Regex.initializeFinalVariable.matcher(line);
        Matcher matcher1 = Ex6Regex.type.matcher(line);
        if (matcher.find() || matcher1.find()) {
            CheckInitializingAndAssigning initChecker = new CheckInitializingAndAssigning();
            initChecker.checkAllValidates(line, section, false);
            return;
        }
        matcher = Ex6Regex.returnStatement.matcher(line);
        if (matcher.matches())
            return;
        checker = new CheckerAssignment();
        checker.checkValidity(line, section);
    }
}