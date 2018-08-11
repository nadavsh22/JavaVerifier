package oop.ex6.main;

import oop.ex6.LineChecker.MethodSection;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;


public class FileReader {
    private String filePath;
    private GeneralSection generalSection;
    private ArrayList<MethodSection> methods = new ArrayList<>();

    /**
     * constructor
     *
     * @param filePath the path of the the file
     */
    public FileReader(String filePath) {
        this.filePath = filePath;

    }

    /**
     * read the lines and create initial sections
     *
     * @throws IOException
     * @throws Ex6exception
     */
    public void readLines() throws IOException, Ex6exception {
        ArrayList<List<String>> methodSections = new ArrayList<List<String>>(); // methods lines
        ArrayList<List<String>> nonMethodSections = new ArrayList<List<String>>(); // general section lines
        Path path = Paths.get(this.filePath);
        int start = 0, i = 0;
        List<String> fileLines = Files.readAllLines(path);
        while (i < fileLines.size()) { // check for methods and add the appropriate lines to the methods array
            Matcher matcher1 = Ex6Regex.methodChecker.matcher(fileLines.get(i));
            if (matcher1.find()) {
                start = i;
                i++;
                int countOpenBraces = 1;
                int countClosedBraces = 0;
                while (i < fileLines.size()) {
                    Matcher matcher3 = Ex6Regex.openBracesChecker.matcher(fileLines.get(i));
                    if (matcher3.find())
                        countOpenBraces++;
                    matcher3 = Ex6Regex.closeBracesChecker.matcher(fileLines.get(i));
                    if (matcher3.find())
                        countClosedBraces++;
                    if (countClosedBraces == countOpenBraces) {
                        i++;
                        break;
                    }
                    i++;
                }
                List<String> newMethod = fileLines.subList(start, i);
                methodSections.add(newMethod);
            } else { // add the non methods line to the general section
                start = i;
                Matcher matcher2;
                while (i < fileLines.size()) {
                    matcher2 = Ex6Regex.methodChecker.matcher(fileLines.get(i));
                    if (matcher2.find())
                        break;
                    i++;
                }
                List<String> newNonMethod = fileLines.subList(start, i);
                nonMethodSections.add(newNonMethod);
            }
        }

        this.createMethodsSections(methodSections); // create methods objects
        this.createGeneralSection(nonMethodSections); // create general section object


    }

    /**
     * create global scope
     *
     * @param nonMethodSections the lines to add
     * @throws Ex6exception bad format
     */
    private void createGeneralSection(ArrayList<List<String>> nonMethodSections) throws Ex6exception {
        ArrayList<String> generateGS = new ArrayList<String>();
        for (List<String> line : nonMethodSections) {
            generateGS.addAll(line);
        }

        this.generalSection = new GeneralSection(generateGS, Section.type2);
        this.generalSection.addMethods(this.methods);

    }

    /**
     * create method objects
     *
     * @param methodSections - array of lists of lines that each method holds
     * @throws IOException
     */
    private void createMethodsSections(ArrayList<List<String>> methodSections) throws IOException {
        for (List<String> method : methodSections) {
            MethodSection newMethod = new MethodSection(method);
            this.methods.add(newMethod);
        }
    }

    /**
     * @return the global scope
     */
    Section getGeneralSection() {
        return this.generalSection;

    }
}
