package com.myorganization.demo;

import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {
    
    public static void main( String[] args ) throws IOException {

        if (args.length != 1) {
            //File project_directory = new File("/Users/zie/github-projects/apache-commons-collections");
            System.out.println("Usage: java -jar demo.jar <project_directory>");
            return;
        }
        File project_directory = new File(args[0]);

        NoGetter.listeClasses(project_directory);
    }
}
