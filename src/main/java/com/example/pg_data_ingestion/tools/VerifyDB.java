package com.example.pg_data_ingestion.tools;
import com.example.pg_data_ingestion.repo.CatalogRepo;

import java.util.ArrayList;


public class VerifyDB {


    CatalogRepo catalog;
    public VerifyDB(CatalogRepo catalog) { this.catalog = catalog;}

    /**
     * Returns true if all the book id's contained in queryList exists in the connected database
     * @param queryList a list of id's to check against the database
     * @return True if the IDs exist in the database, false if at least one of them doesn't exist
     */
    public boolean VerifyByBookID(ArrayList<Integer> queryList) {
        for (Integer integer : queryList) {
            Long temp = Long.valueOf(integer);
            if (this.catalog.findBookByID(temp) == null) {
                return false;
            }
        }
        return true;
    }
}
