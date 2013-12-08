package de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.BaseDTO;

/**
 * DTO for the article data.
 *
 * @author zuch1000
 */
public class ArticleDTO extends BaseDTO {

    private int id;
    private int articleTypeID;

    @Override
    public String toString() {
        return "ArticleDTO{" + "id=" + id + ", articleTypeID=" + articleTypeID + ", yardID=" + yardID + '}';
    }
    private int yardID;			//where the article is stored in
    //Image barcodeImage; wirklich?

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the articleTypeID
     */
    public int getArticleTypeID() {
        return articleTypeID;
    }

    /**
     * @param articleTypeID the articleTypeID to set
     */
    public void setArticleTypeID(int articleTypeID) {
        this.articleTypeID = articleTypeID;
    }

    /**
     * @return the storageID
     */
    public int getYardID() {
        return yardID;
    }

    /**
     * @param storageID the storageID to set
     */
    public void setYardID(int storageID) {
        this.yardID = storageID;
    }
}
