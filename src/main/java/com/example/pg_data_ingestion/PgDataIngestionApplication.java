package com.example.pg_data_ingestion;

import com.example.pg_data_ingestion.model.Author;
import com.example.pg_data_ingestion.model.Book;
import com.example.pg_data_ingestion.model.Subject;
import com.example.pg_data_ingestion.repo.AuthorRepo;
import com.example.pg_data_ingestion.repo.CatalogRepo;
import com.example.pg_data_ingestion.repo.SubjectRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class PgDataIngestionApplication implements CommandLineRunner {
	@Autowired
	CatalogRepo bookRepo;
	@Autowired
	SubjectRepo subjectRepo;
	@Autowired
	AuthorRepo authorRepo;
	public static void main(String[] args) {
		SpringApplication.run(PgDataIngestionApplication.class, args);
	}


	public void run(String... args) throws Exception {
		/*Subject subject_test = new Subject("Canadian History");

		//If the resulting list isn't empty, then there already exists some entry that matches the Subject name
		if (subjectRepo.findByName(subject_test.getName()).size() != 0) {
			System.out.println("Subject " + subject_test.getName() + " already exists" + " with ID " + subjectRepo.findByName(subject_test.getName()).get(0).getId());
		}
		else {
			System.out.println("Subject " + subject_test.getName() + " does not exist in the database");
		}*/

		//RDF Parser Test Injection ----------------------------------------------------- /
		Path catalogDirectory = Paths.get("D:\\Projects\\Assets\\PG_RDF_Files");
		List<Path> directories = parseFiles(catalogDirectory);

		System.out.println(directories);


		//Go through directories
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

			//Check if author exists in the database, if not add them to database
			Author author_object = new Author(name, "www.wikipedia.com");
			if (authorRepo.findByName(author_object.getName()).size() != 0) {
				System.out.println("Author " + author_object.getName() + " already exists" + " with ID " + authorRepo.findByName(author_object.getName()).get(0).getId());
			}
			else {
				System.out.println("Author " + author_object.getName() + " does not exist in the database. Adding Author");
				authorRepo.save(author_object);
			}

			//Check the subjects if they exist in the database, if not then add them to the database
			for (int s = 0; s < subjs.size(); s++) {
				//Create a subject object from the data
				Subject subject_object = new Subject(subjs.get(s));

				//Check if it already exists in the database
				if (subjectRepo.findByName(subject_object.getName()).size() != 0) {
					System.out.println("Subject " + subject_object.getName() + " already exists" + " with ID " + subjectRepo.findByName(subject_object.getName()).get(0).getId());
				}
				else {
					System.out.println("Subject " + subject_object.getName() + " does not exist in the database. Adding Subject");
					subjectRepo.save(subject_object);
				}
			}

			//Add the book to the Database
			System.out.println("-------- PARSING BOOK -----------");
			//Book Parser
			Book book_object = new Book(Long.valueOf(id), title, language, copyright_status);
			Author temp_author = authorRepo.findByName(name).get(0);
			book_object.addAuthor(temp_author);

			for (int s = 0; s < subjs.size(); s++) {
				Subject temp_subject = subjectRepo.findByName(subjs.get(s)).get(0);
				book_object.addSubject(temp_subject);
			}
			bookRepo.save(book_object);

			/*
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
			*/
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
