package com.syssoft.foodmenu.model;

import java.io.Serializable;
import java.util.List;

public class FoodLibrary implements Serializable {
	private static final long serialVersionUID = 1L;
	// The username of the owner of the library
    private String user;
    // A list of lessons that the user owns
    private List<Food> foods;
     
    public FoodLibrary(String user, List<Food> foods) {
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
    public List<Food> getfoods() {
        return foods;
    }
}