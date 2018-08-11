package oop.ex6.LineChecker;

import oop.ex6.LineClassifier;
import oop.ex6.main.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class MethodSection extends Section {
    private ArrayList<Block> blocks = new ArrayList<Block>();

    public MethodSection(List<String> lines) throws Ex6exception {
        super(lines, type1);
    }

    public void checkLines() throws Ex6exception {
        this.checkBottomLines();
        LineClassifier LC = new LineClassifier(this);
        int start = 1, i = 1;
        int length = this.lines.size();
        while (i < length) {
            Matcher matcher = Ex6Regex.ifWhileOpenForm.matcher(this.lines.get(i));
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
                Block block = new Block(this.lines.subList(start, i), type3);
                block.setScope(this);
                block.checkLines();
                this.blocks.add(block);
            } else {
                while (i < length) {
                    matcher = Ex6Regex.ifWhileOpenForm.matcher(this.lines.get(i));
                    if (matcher.find())
                        break;
                    else
                        LC.classify(this.lines.get(i));
                    i++;
                }
            }
        }


    }

    private void checkBottomLines() throws Ex6exception {
        Matcher matcher1;
        Matcher matcher2;

        int i = this.lines.size() - 1;
        while (i > 0) {
            matcher1 = Ex6Regex.closeBracesChecker.matcher(this.lines.get(i));
            matcher2 = Ex6Regex.emptyLine.matcher(this.lines.get(i));
            if (matcher1.matches()) {
                i--;
                break;
            } else if (matcher2.matches()) {
                i--;
            } else
                throw new Ex6exception("missing closing braces");
        }
        while (i > 0) {
            matcher1 = Ex6Regex.returnStatement.matcher(this.lines.get(i));
            matcher2 = Ex6Regex.emptyLine.matcher(this.lines.get(i));
            if (matcher1.matches())
                break;

            else if (matcher2.matches())
                i--;
            else
                throw new Ex6exception("missing return statement");
        }
        this.lines = this.lines.subList(0, i);

    }


    public void addParameter(Variable param) {
        params.add(param);
    }

    public boolean checkParameter(Variable param, int position) {
        return this.params.get(position).getType().equals(param.getType());
    }

    public Variable getParameter(String name) {
        for (Variable var : this.params) {
            if (var.getName().equals(name)) {
                return var;
            }
        }
        return null;
    }

    public ArrayList<Variable> getParams() {
        return this.params;
    }
    
}
