# Code of your exercise

```java
package com.myorganization.demo;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TCCCalculator extends VoidVisitorAdapter<Void> {

    private static int range = 0;
    private List<ClassMetrics> classMetricsList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // Spécifiez le chemin d'accès au code source du projet en argument
        /*
        if (args.length != 1) {
            System.out.println("Usage: java -jar demo.jar <project_directory>");
            return;
        }
        String projectPath = args[0];
        */

        // Spécifiez le chemin d'accès au code source du projet
        String projectPath = "/Users/zie/github-projects/apache-commons-collections/src/main/java/org/apache/commons/collections4";
        

        TCCCalculator tccCalculator = new TCCCalculator();
        tccCalculator.calculateTCC(projectPath);
        tccCalculator.generateCsvReport();
    }

    public void calculateTCC(String projectPath) {
        File projectDir = new File(projectPath);

        if (!projectDir.isDirectory()) {
            System.err.println("Le chemin spécifié n'est pas un répertoire valide.");
            return;
        }

        File[] files = projectDir.listFiles((dir, name) -> name.endsWith(".java"));

        if (files == null || files.length == 0) {
            System.err.println("Aucun fichier Java trouvé dans le répertoire spécifié.");
            return;
        }

        for (File file : files) {
            try {
                CompilationUnit cu = StaticJavaParser.parse(file);
                visit(cu, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void generateCsvReport() throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter("reportTCCCalculator.csv"));
        String[] header = {"N° ", "Package Name", "Class Name", "TCC value" };
        writer.writeNext(header);

        try {
            for (ClassMetrics classMetric : classMetricsList) {
                writer.writeNext(new String[]{setAndGetRange() + " ", classMetric.getPackageName(), classMetric.getClassName(), classMetric.getTcc() + ""});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        if (!declaration.isPublic()) return;

        ClassMetrics classMetrics = new ClassMetrics();
        classMetrics.setClassName(declaration.getNameAsString());
        classMetrics.setPackageName(declaration.findCompilationUnit().get().getPackageDeclaration().map(pd -> pd.getNameAsString()).orElse(""));

        // Collect methods
        for (MethodDeclaration method : declaration.getMethods()) {
            if (method.isPublic()) {
                classMetrics.addMethod(method.getNameAsString());
            }
        }

        // Calculate TCC
        calculateTCC(classMetrics, declaration);

        classMetricsList.add(classMetrics);
    }

    private void calculateTCC(ClassMetrics classMetrics, ClassOrInterfaceDeclaration declaration) {
        List<MethodDeclaration> methods = declaration.getMethods();
        int totalMethodPairs = methods.size() * (methods.size() - 1) / 2;
        int mutualCalls = 0;

        for (MethodDeclaration method : methods) {
            for (MethodDeclaration otherMethod : methods) {
                if (!method.equals(otherMethod) && callsMutually(method, otherMethod)) {
                    mutualCalls++;
                }
            }
        }

        double tcc = totalMethodPairs > 0 ? (double) mutualCalls / totalMethodPairs : 0.0;
        classMetrics.setTcc(tcc);
    }

    private boolean callsMutually(MethodDeclaration method1, MethodDeclaration method2) {
        List<MethodCallExpr> calls1 = method1.findAll(MethodCallExpr.class);
        List<MethodCallExpr> calls2 = method2.findAll(MethodCallExpr.class);

        for (MethodCallExpr call1 : calls1) {
            for (MethodCallExpr call2 : calls2) {
                if (call1.getNameAsString().equals(method2.getNameAsString()) &&
                        call2.getNameAsString().equals(method1.getNameAsString())) {
                    return true;
                }
            }
        }

        return false;
    }

    private static int setAndGetRange() {
        return ++range;
    }
}

class ClassMetrics {
    private String packageName;
    private String className;
    private double tcc;
    private List<String> methods;

    public ClassMetrics() {
        this.methods = new ArrayList<>();
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setTcc(double tcc) {
        this.tcc = tcc;
    }

    public void addMethod(String methodName) {
        this.methods.add(methodName);
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public double getTcc() {
        return tcc;
    }
}
```
