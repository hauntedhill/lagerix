/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.services;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleType;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.BookEntry;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.StorageLocation;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author zuch1000
 */
@Stateless
public class ArticleManagerEJBean implements ArticleManagerEJBRemoteInterface{

    public int saveBookEntry(BookEntry entry) {
        return 1;
    }

    public List<BookEntry> getBookEntriesForArticleType(int articleTypeID) {
        
        List<BookEntry> entries = new ArrayList<BookEntry>();
        
        return entries;
    }

    public ArticleType getArticleTypeByID(int articleTypeID) {
        return new ArticleType();
    }

    public ArticleType getArticleTypeByName(String articleTypeName) {
        return new ArticleType();
    }

    public List<StorageLocation> getLocationsForArticleType(int articleTypeID) {
         List<StorageLocation> entries = new ArrayList<StorageLocation>();
        
        return entries;    }

    public List<StorageLocation> getAllStorageLocationsForStorage(int storageID) {
         List<StorageLocation> entries = new ArrayList<StorageLocation>();
        
        return entries;    }
    
}
