package oop.ex6.main;


import oop.ex6.LineClassifier;

import java.util.List;
import java.util.regex.Matcher;

public class Block extends Section {

    /**
     * @param lines the lines of the block
     * @param type  the type of the section
     * @throws Ex6exception bad format
     */
    public Block(List<String> lines, String type) throws Ex6exception {
        super(lines, type);
    }

    /**
     * check the section lines
     *
     * @throws Ex6exception bad format
     */
    public void checkLines() throws Ex6exception {
        this.checkBottomLine(); // check the last line for closing braces
        LineClassifier LC = new LineClassifier(this);
        LC.classify(this.lines.get(0)); // check first line
        int start = 1, i = 1;
        int length = this.lines.size();
        while (i < length) {
            Matcher matcher = Ex6Regex.ifWhileOpenForm.matcher(this.lines.get(i)); // look for another
            // section
            if (matcher.find()) {
                start = i;
                i++;
                int countOpenBraces = 1;
                int countClosedBraces = 0;
                while (i < length) {
                    matcher = Ex6Regex.openBracesChecker.matcher(this.lines.get(i));
                    if (matcher.find())
                        countOpenBraces++;
                    matcher = Ex6Regex.closeBracesChecker.matcher(this.lines.get(i));
                    if (matcher.find())
                        countClosedBraces++;
                    if (countClosedBraces == countOpenBraces) {
                        i++;
                        break;
                    }
                    i++;
                }
                Block block = new Block(this.lines.subList(start, i), Section.type3); // create new block
                // and check its lines
                block.setScope(this);
                block.checkLines();
            } else {
                while (i < length) {
                    matcher = Ex6Regex.ifWhileOpenForm.matcher(this.lines.get(i));
                    if (matcher.find())
                        break;
                    else
                        LC.classify(this.lines.get(i)); // check sections lines
                    i++;
                }
            }
        }

    }

    /**
     * check last line in section
     *
     * @throws Ex6exception bad format
     */
    private void checkBottomLine() throws Ex6exception {
        Matcher matcher1;
        Matcher matcher2;

        int i = this.lines.size() - 1;
        while (i > 0) { // bottom up look for closing braces
            matcher1 = Ex6Regex.closeBracesChecker.matcher(this.lines.get(i));
            matcher2 = Ex6Regex.emptyLine.matcher(this.lines.get(i));
            if (matcher1.matches())
                break;
            else if (matcher2.matches()) {
                i--;
            } else
                throw new Ex6exception("missing closing braces");
        }
        this.lines = this.lines.subList(0, i);
    }

    /**
     * unsupported methods
     *
     * @param var variable
     */
    public void addParameter(Variable var) {
        System.out.println("method not supported");
    }

    /**
     * unsupported method
     *
     * @param name string
     * @return
     */
    public Variable getParameter(String name) {
        System.out.println("operation not supported");
        return null;
    }

}
