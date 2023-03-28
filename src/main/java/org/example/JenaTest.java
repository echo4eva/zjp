package org.example;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.DCTypes;

import javax.xml.crypto.dsig.keyinfo.PGPData;
import java.io.*;
import java.util.ArrayList;

public class JenaTest {
    public static void main(String[] args) {

        // SETUP
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

        // Initializing and Assigning Variables of things we want from the .rdf file
        // public.books
        int indexEndOfID = inputFileName.indexOf(".");
        int indexStartOfID = inputFileName.indexOf("g") + 1;
        int id = Integer.parseInt(inputFileName.substring(indexStartOfID, indexEndOfID));
        int auth_id = 0;
        int subj_id = 0;
        String title = "";
        String release_date = "";
        String language = "";
        String copyright_status = "";
        // added
        ArrayList<String> subjs = new ArrayList<String>();
        String publisher = "";

        // public.author
        String name = "";
        String entry = "";
        // added
        int death_date = 0;
        int birth_date = 0;

        Property rdfType = model.getProperty("http://www.w3.org/2001/XMLSchema#integer");

        // START OF PARSING

        // EVERYTHING
        StmtIterator it1 = model.listStatements();
        while (it1.hasNext()) {
            Statement stmt = it1.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode object = stmt.getObject();

            System.out.print("[IT1] ");
            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object.isResource()) {
                System.out.print("isRes() " + object.toString());
            } else {
                System.out.print(" \"" + object.toString() + "\"");
            }

            if (predicate.toString().equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#value")) {
                if (object.toString().length() > 32) {
                    if (object.toString().substring(object.toString().length() - 32, object.toString().length()).equals(
                            "http://purl.org/dc/terms/RFC4646")) {
                        language =  object.toString().substring(0,2);
                    }
                }
            }

            System.out.println(" .");
        }

        System.out.println("");
        System.out.println("=====END OF EVERYTHING=====");
        System.out.println("");

        // SUBJECTS
        Property p = model.getProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#value");
        StmtIterator it2 = model.listStatements(
                new SimpleSelector(null, p, (RDFNode) null));
        while (it2.hasNext()) {
            Statement stmt = it2.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode object = stmt.getObject();

            System.out.print("[IT2] ");
            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object.isResource()) {
                System.out.print("isRes() " + object.toString());
            } else {
                String s = object.toString();
                char c = s.charAt(0);
                if (Character.isUpperCase(c)) {
                    String tempSubj = object.toString();
                    System.out.print("\"" + tempSubj + "\"");
                    subjs.add(tempSubj);
                }
            }
            System.out.println(" .");
        }

        System.out.println("");
        System.out.println("=====END OF SUBJECTS=====");
        System.out.println("");

        // OTHER
        Resource book = model.getResource(base + "ebooks/6");
        StmtIterator it3 = model.listStatements(
                new SimpleSelector(book, null, (RDFNode) null));
        while (it3.hasNext()) {
            Statement stmt = it3.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode object = stmt.getObject();

            System.out.print("[IT3] ");
            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object.isResource()) {
                System.out.print("isRes() " + object.toString());
            } else {
                System.out.print(" \"" + object.toString() + "\"");
            }
            System.out.println(" .");

            if (predicate.toString().equals("http://purl.org/dc/terms/title")) {
                title = object.toString();
            } else if (predicate.toString().equals("http://purl.org/dc/terms/issued")) {
                release_date = object.toString().substring(0, 10);
            } else if (predicate.toString().equals("http://purl.org/dc/terms/rights")) {
                copyright_status = object.toString();
            } else if (predicate.toString().equals("http://purl.org/dc/terms/publisher")) {
                publisher = object.toString();
            }
        }

        System.out.println("");
        System.out.println("=====END OF OTHER=====");
        System.out.println("");

        // AUTHOR
        Resource auth = model.getResource("http://www.gutenberg.org/2009/agents/4");
        StmtIterator it4 = model.listStatements(
                new SimpleSelector(auth, null, (RDFNode) null));
        while (it4.hasNext()) {
            Statement stmt = it4.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode object = stmt.getObject();

            System.out.print("[IT4] ");
            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object.isResource()) {
                System.out.print("isRes() " + object.toString());
            } else {
                System.out.print(" \"" + object.toString() + "\"");
            }
            System.out.println(" .");

            if (predicate.toString().equals("http://www.gutenberg.org/2009/pgterms/deathdate")) {
                death_date = Integer.parseInt(object.toString().substring(0, 4));
            } else if (predicate.toString().equals("http://www.gutenberg.org/2009/pgterms/birthdate")) {
                birth_date = Integer.parseInt(object.toString().substring(0, 4));
            } else if (predicate.toString().equals("http://www.gutenberg.org/2009/pgterms/name")) {
                name = object.toString();
            }
        }

        System.out.println("");
        System.out.println("=====END OF AUTHOR=====");
        System.out.println("");

        System.out.println("");
        System.out.println("=====RESULTS=====");
        System.out.println("");
        System.out.println("Author's Name: " + name);
        System.out.println("Author's Birth Date: " + birth_date);
        System.out.println("Author's Death Date: " + death_date);
        System.out.println("Books ID: " + id);
        System.out.println("Book's Title: " + title);
        System.out.println("Book's Release: " + release_date);
        System.out.println("Book's Language: " + language);
        System.out.println("Book's Copyright Status: " + copyright_status);
        System.out.println("Book's Publisher: " + publisher);
        System.out.println("Book's Subjects: " + subjs);
    }
}