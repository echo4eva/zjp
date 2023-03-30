package org.example;

import org.apache.jena.Jena;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.DCTypes;

import javax.xml.crypto.dsig.keyinfo.PGPData;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        String inputFileName = "pg6.rdf";
        Model model = ModelFactory.createDefaultModel();

        InputStream in = FileManager.get().open(inputFileName);
        if (in == null) {
            throw new IllegalArgumentException("File: " + inputFileName + " not found.");
        }
        else {
            System.out.println("File found");
        }

        String base = "http://www.gutenberg.org/";
        model.read(in, base);
//        model.write(System.out);

        Resource book = model.getResource(base + "ebooks/6");
//        System.out.println(book.getRequiredProperty(DCTerms.publisher));
//        model.getRequiredProperty(book, DCTerms.publisher);
//        System.out.println(book.getURI());
//        StmtIterator test = book.listProperties();
//        if (test.hasNext()) {
//            System.out.println("hello");
//        }

        System.out.println("");

        Statement rights = book.getProperty(DCTerms.rights);
        System.out.println("Statement the Property, Rights: " + rights);
        System.out.println("Subject: " + rights.getSubject());
        System.out.println("Predicate: " + rights.getPredicate());
        System.out.println("Object: " + rights.getObject());

        System.out.println("");

        Statement creator = book.getProperty(DCTerms.language);
        System.out.println("Statement of the Property, Creator: " + creator);
        System.out.println("Subject: " + creator.getSubject());
        System.out.println("Predicate: " + creator.getPredicate());
        System.out.println("Object: " + creator.getObject());
        if (creator.getObject().isResource()) {
            System.out.println("Yep it is a resource!");
//            Resource creatorObj = creator.getObject();
//            System.out.println(creatorObj.isResource());
//            Resource creatorObjectResource = creator.getResource();
//            System.out.println(creatorObjectResource.getProperty(DCTerms.Agent));

        }

        System.out.println("");



        ResIterator iter = model.listResourcesWithProperty(DCTerms.publisher);
        if (iter.hasNext()) {
            System.out.println("The database contains vcards for:");
            while (iter.hasNext()) {
                Resource testResource = iter.nextResource();
                System.out.println(testResource.getURI());
                System.out.println("  " + testResource
                        .getRequiredProperty(DCTerms.publisher)
                        .getString() );
            }
        } else {
            System.out.println("No vcards were found in the database");
        }

//        ResIterator iter = model.listSubjectsWithProperty(DCTerms.creator);
//        if (iter.hasNext()) {
//            System.out.println("The database contains vcards for:");
//            while (iter.hasNext()) {
//                System.out.println("  " + iter.nextResource()
//                        .getProperty(DCTerms.creator)
//                        .getString());
//            }
//        } else {
//            System.out.println("No vcards were found in the database");
//        }

        JenaTest test = new JenaTest();
    }

}