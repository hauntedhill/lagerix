/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.BaseDTO;

/**
 *
 * @author zuch1000
 */
public class MovementDTO extends BaseDTO {
	private int articleID;			//ID of the scanned article
	private int yardID;			//ID of the scanned storage location
	private boolean bookedIn;		//true, if the article was booked int, false otherwise
	private int userID;				//ID of the user account which was logged in on the device that sent the request
	private long timestamp;			//Timestamp at which the booking request was sent

    /**
     * @return the articleID
     */
    public int getArticleID() {
        return articleID;
    }

    /**
     * @param articleID the articleID to set
     */
    public void setArticleID(int articleID) {
        this.articleID = articleID;
    }

    /**
     * @return the locationID
     */
    public int getYardID() {
        return yardID;
    }

    /**
     * @param locationID the locationID to set
     */
    public void setYardID(int locationID) {
        this.yardID = locationID;
    }

    /**
     * @return the bookedIn
     */
    public boolean isBookedIn() {
        return bookedIn;
    }

    /**
     * @param bookedIn the bookedIn to set
     */
    public void setBookedIn(boolean bookedIn) {
        this.bookedIn = bookedIn;
    }

    /**
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
