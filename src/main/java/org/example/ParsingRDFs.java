package org.example;

import com.google.common.base.CharMatcher;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.DCTerms;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParsingRDFs {

    public static void main(String args[]) throws IOException {
        // The directory where the catalog is saved in after unzipping/extracting
        Path catalogDirectory = Paths.get("C:\\Users\\peril\\Downloads\\test");
        // Gets a list of all the .rdf file directories
        List<Path> directories = parseFiles(catalogDirectory);

        for (int i = 0; i < directories.size(); i++) {
            String givenPath = directories.get(i).toString();
            String fileName = givenPath.substring(givenPath.lastIndexOf("\\") + 1);
            Model model = ModelFactory.createDefaultModel();

            System.out.println("=====[BOOK " + i + "]=====");

            InputStream in = FileManager.get().open(givenPath);
            if (in == null) {
                throw new IllegalArgumentException("File: " + givenPath + " not found.");
            } else {
                System.out.println("File " + fileName + " found");
            }

            String base = "http://www.gutenberg.org/";
            model.read(in, base);

            // Initializing and Assigning Variables of things we want from the .rdf file
            // public.books
            int indexEndOfID = givenPath.indexOf(".");
            int indexStartOfID = givenPath.indexOf("g") + 1;
            int id = Integer.parseInt(givenPath.substring(indexStartOfID, indexEndOfID));
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

            // Parsing through the whole .rdf file to get property/predicate values
            StmtIterator it = model.listStatements();
            while (it.hasNext()) {
                Statement stmt = it.nextStatement();
                Resource subject = stmt.getSubject();
                Property predicate = stmt.getPredicate();
                RDFNode object = stmt.getObject();

                String predicateString = predicate.toString();
                String objectString = object.toString();

                // testing purposes
                /*
                System.out.print("[IT] ");
                System.out.print(subject.toString());
                System.out.print(" " + predicate.toString() + " ");
                if (object.isResource()) {
                    System.out.print("isRes() " + object.toString());
                } else {
                    System.out.print(" \"" + object.toString() + "\"");
                }
                System.out.println(".");
                */

                // Getting property/predicate values depending on current statement's property/predicate
                switch (predicateString) {
                    // Author's name
                    case "http://www.gutenberg.org/2009/pgterms/name":
                        name = object.toString();
                        break;
                    // Author's death date
                    case "http://www.gutenberg.org/2009/pgterms/deathdate":
                        death_date = Integer.parseInt(objectString.substring(0, objectString.indexOf('^')));
                        break;
                    // Author's birthdate
                    case "http://www.gutenberg.org/2009/pgterms/birthdate":
                        birth_date = Integer.parseInt(objectString.substring(0, objectString.indexOf('^')));
                        break;
                    // Book's title
                    case "http://purl.org/dc/terms/title":
                        title = objectString;
                        break;
                    // Book's release date
                    case "http://purl.org/dc/terms/issued":
                        release_date = objectString.substring(0, 10);
                        break;
                    // Book's subject or language
                    case "http://www.w3.org/1999/02/22-rdf-syntax-ns#value":
                        // subject
                        char firstChar = objectString.charAt(0);
                        if (Character.isUpperCase(firstChar)) {
                            subjs.add(objectString);
                        }
                        // language
                        else if (objectString.length() > 32) {
                            if (objectString.substring(objectString.length() - 32, objectString.length()).equals(
                                    "http://purl.org/dc/terms/RFC4646")) {
                                language = objectString.substring(0, 2);
                            }
                        }
                        break;
                    // Book's copyright status
                    case "http://purl.org/dc/terms/rights":
                        copyright_status = objectString;
                        break;
                    // Book's publisher
                    case "http://purl.org/dc/terms/publisher":
                        publisher = objectString;
                        break;
                    default:
                        break;
                }
            }

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

            Book testBook = new Book(id, title, release_date, language, copyright_status, publisher);
            System.out.println(testBook.getJSONObj().toJSONString());
        }
    }

    private static List<Path> parseFiles(Path path) throws IOException{
        List<Path> result;
        try (Stream<Path> paths = Files.walk(path)) {
            result = paths.filter(Files::isRegularFile).collect(Collectors.toList());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
