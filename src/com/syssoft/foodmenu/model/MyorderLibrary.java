package com.syssoft.foodmenu.model;

import java.io.Serializable;
import java.util.List;

public class MyorderLibrary implements Serializable {
	private static final long serialVersionUID = 1L;
	// The username of the owner of the library
    private String user;
    // A list of lessons that the user owns
    private List<Myorder> foods;
     
    public MyorderLibrary(String user, List<Myorder> foods) {
        this.user = user;
        this.foods = foods;
    }
 
    /**
     * @return the user name
     */
    public String getUser() {
        return user;
    }
 
    /**
     * @return the lessons
     */
    public List<Myorder> getfoods() {
        return foods;
    }
}