package com.syssoft.foodmenu.model;

import java.io.Serializable;
			
			/**
			 * This is the 'library' of all the users videos
			 *
			 * @author paul.blundell
			 */
			public  class Food implements Serializable {
				/**
				 * code, title, teacher, starttime, endtime, location,
				 */
				private static final long serialVersionUID = 1L;
				private String food_price;
			    private String food_name;
			    private String food_ingredients;
			    private String foodimage_url,status_image_url;
			    boolean selected;
			    
			     //order_id,product_id,clientname,phoneno,itemname,itemprice,itemtime,location,itemimage_url)
			    public Food(String food_name,String food_price,String food_ingredients,String foodimage_url,boolean selected) {
			        super();
			        this.food_price = food_price ;
			        this.food_name = food_name;
			        this.food_ingredients = food_ingredients ;
			        this.foodimage_url = foodimage_url;
			        this.selected = selected;
				    }
			 
			    /**
			     * public void setName(String name) {
 		this.name = name;
 	}
			     * @return the title of the video
			     */
			    public String getFoodname(){
			        return food_name;
			    }
			    
			
			    public String getFoodprice(){
			        return food_price;
			    }
			    
			    
			    public String getFoodimage_url(){
			        return foodimage_url;
			    }
			    
			    /**
			     * @return the title of the video
			     */
			    public String getFoodingredients(){
			        return food_ingredients;
			    }
			    /**
			     * @set the title of the video
			     */
			    
			    public void setFoodname(String food_name){
			        this.food_name=food_name;
			    }
			    
			
			    public void setFoodprice(String food_price){
			        this.food_price=food_price;
			    }
			    
			    
			    public boolean isSelected() {
			 		return selected;
			 	}
			   /* 
			    public String getStatusimage_url() {
			    	  return status_image_url;
			    	  
			    }*/
			    
			    public void setSelected(boolean selected) {
			    	  this.selected = selected;
			    	  
			    }
		
	
}
