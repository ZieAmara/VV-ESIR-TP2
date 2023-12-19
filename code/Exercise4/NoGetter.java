package com.myorganization.demo;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * JavaParser
 * This code use JavaParser to obtains the private fields of public classes that have no public getter in a Java project.
 * 
 * @author Zié Amara TRAORÉ
 * @version 1.0
 *
 */

public class NoGetter {
    
    public static void listeClasses(File projectDirectory) throws IOException {

        CSVWriter writer = new CSVWriter(new FileWriter("rapport.csv"));
        String[] header = { "Class Name", "Field Name", "Package Name" };
        writer.writeNext(header);

        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            System.out.println(path);
            System.out.println(file);
            try {
                new VoidVisitorAdapter<Object>() {

                    @Override
                    public void visit(ClassOrInterfaceDeclaration node, Object arg) {
                        super.visit(node, arg);
                        System.out.println(" * " + node.getName());

                        int nbrChamps = node.getFields().size();
                        int nbrMethods = node.getMethods().size();
                        boolean hasNoGetter = false;
                        

                        for (int i = 0; i < nbrChamps; i++) {
                            if (!node.getFields().get(i).isPrivate())
                                continue;

                            String name = node.getFields().get(i).getVariable(0).getNameAsString();
                            String type = node.getFields().get(i).getElementType().toString();

                            for (int j = 0; j < nbrMethods; j++) {
                                String methodName = node.getMethods().get(j).getNameAsString();

                                if (methodName.toLowerCase().equals("get" + name.toLowerCase()) &&
                                        node.getMethods().get(j).isPublic() &&
                                        node.getMethods().get(j).getType().toString().equals(type) &&
                                        node.getMethods().get(j).getBody().isPresent() &&
                                        node.getMethods().get(j).getBody().get().getStatements().size() == 1) {
                                    continue;
                                }

                                hasNoGetter = true;
                            }

                            if (hasNoGetter) {
                                String filePath = node.findCompilationUnit().orElseThrow().getStorage().map(Object::toString).orElse("");
                                String packageName = getPackageName(filePath);
    
                                // writing of informations in the CSV file
                                writer.writeNext(new String[]{ node.getNameAsString(), name, packageName });
                            }
                        }

                        if (hasNoGetter) {
                            System.out.println("No getter");
                        }
                    }

                    private String getPackageName(String filePath) {
                        String[] parts = filePath.split(File.separator.equals("\\") ? "\\\\" : File.separator);
                        int srcIndex = indexOfSrc(parts);

                        // Si "src" est trouvé, combinez les parties suivantes comme le nom du package
                        if (srcIndex != -1 && srcIndex < parts.length - 1) {
                            return String.join(".", Arrays.copyOfRange(parts, srcIndex + 1, parts.length - 1));
                        } else {
                            return "default";
                        }
                    }

                    private int indexOfSrc(String[] parts) {
                        for (int i = 0; i < parts.length; i++) {
                            if ("src".equals(parts[i])) {
                                return i;
                            }
                        }
                        return -1;
                    }

                }.visit(StaticJavaParser.parse(file), null);
                System.out.println(); // empty line
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).explore(projectDirectory);
    }
}
