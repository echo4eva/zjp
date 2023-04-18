package com.example.pg_data_ingestion.repo;
import com.example.pg_data_ingestion.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CatalogRepo  extends JpaRepository<Book, Integer>{

    @Query("SELECT b from Book b WHERE b.bookId = :bookId")
    Book findBookByID(Long bookId);
    /**
     * Returns a list of books that contains the matching Subject ID
     * @param subId a unique subject ID to search
     * @return A list of books with a matching subject ID
     */
    @Query("SELECT b from Book b JOIN b.subjects s WHERE s.subject_id = :subId")
    List<Book> findBookBySubjectID(Long subId);

    /**
     * Returns a list of books that contains the matching Subject Name
     * @param subName a subject name to search
     * @return A list of books with a matching subject Name
     */
    @Query("SELECT b from Book b JOIN b.subjects s WHERE s.name= :subName")
    List<Book> findBookBySubjectName(String subName);

    /**
     * Returns a list of books that contains the author of the matching ID
     * @param authorId unique author ID to search
     * @return A list of books with a matching author ID
     */
    @Query("SELECT b from Book b JOIN b.authors a where a.authorId = :authorId")
    List<Book> findBookByAuthorID(Long authorId);


    /**
     * Returns a list of books that contains the author of the matching name
     * @param authorName author name to search
     * @return A list of books with a matching author Name
     */
    @Query("SELECT b from Book b JOIN b.authors a where a.name = :authorName")
    List<Book> findBookByAuthorName(String authorName);
}
