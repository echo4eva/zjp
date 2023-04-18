package com.example.pg_data_ingestion.tools;
import com.example.pg_data_ingestion.repo.CatalogRepo;


import java.util.ArrayList;


public class VerifyDB {

    public VerifyDB() {}

    /**
     * Returns true if all the book id's contained in queryList exists in the connected database
     * @param catalog the book catalog referencing to the database
     * @param queryList a list of id's to check against the database
     * @return True if the IDs exist in the database, false if at least one of them doesn't exist
     */
    public boolean VerifyByBookID(CatalogRepo catalog, ArrayList<Integer> queryList) {
        for (Integer integer : queryList) {
            Long temp = Long.valueOf(integer);
            if (catalog.findBookByID(temp) == null) {
                return false;
            }
        }
        return true;
    }
}
