package com.syssoft.foodmenu.model;

import java.io.Serializable;
			
			/**
			 * This is the 'library' of all the users videos
			 *
			 * @author paul.blundell
			 */
			public  class Myorder implements Serializable {
				/**
				 * code, title, teacher, starttime, endtime, location,
				 */
				private static final long serialVersionUID = 1L;
				private String food_price;
			    private String food_name;
			   // private String food_ingredients;
			    private String foodimage_url;
			    
			     //order_id,product_id,clientname,phoneno,itemname,itemprice,itemtime,location,itemimage_url)
			    public Myorder(String food_name,String food_price) {
			        super();
			        this.food_price = food_price ;
			        this.food_name = food_name;
			        //this.food_ingredients = food_ingredients ;
			        //this.foodimage_url = foodimage_url;
				    }
			 
			    /**
			     * @return the title of the video
			     */
			    public String getFoodname(){
			        return food_name;
			    }
			    
			
			    public String getFoodprice(){
			        return food_price;
			    }
			}
