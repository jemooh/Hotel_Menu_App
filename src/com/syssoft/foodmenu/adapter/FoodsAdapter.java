package com.syssoft.foodmenu.adapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.syssoft.foodmenu.util.Log;
import com.syssoft.foodmenu.widget.FileCache;
import com.syssoft.foodmenu.widget.ImageLoader;
import com.syssoft.foodmenu.widget.MemoryCache;
import com.syssoft.foodmenu.R;
import com.syssoft.foodmenu.model.Food;

		/**
		 * This adapter is used to show our items objects in a ListView
		 * It hasn't got many memory optimisations, if your list is getting bigger or more complex
		 * you may want to look at better using your view resources: http://developer.android.com/resources/samples/ApiDemos/src/com/example/android/apis/view/List14.html
		 * @author paul.blundell
		 */
		public class FoodsAdapter extends BaseAdapter {
			public List<Food> Items;
			DecimalFormat df = new DecimalFormat("#.##");
		    // An inflator to use when creating rows
		    private LayoutInflater mInflater;
		    Context mContext;
		    public ImageLoader imageLoader; 
		    FileCache fileCache;
		    MemoryCache memoryCache=new MemoryCache(); 
		   //public String posy =SingleItemActivity.positionDel;
		    /**
		     * @param context this is the context that the list will be shown in - used to create new list rows
		     * @param items this is a list of items to display
		     */
		    public FoodsAdapter(Context context, List<Food> Items) {
		        this.Items = Items;
		        this.mInflater = LayoutInflater.from(context);
		        this.mContext = context;
		        imageLoader=new ImageLoader(context.getApplicationContext());
		    }
		 
		    
		    public void setListData(List<Food> ListItems){
		         Items= ListItems;
		        
		      }
		    
		    //@Override
		    public int getCount() {
		        return Items.size();
		    }
		 
		    //@Override
		    public Object getItem(int position) {
		        return Items.get(position);
		    }
		    /*
		    public Object set(int index, Item newValue){
		    	
		    	/*if((index > list.length-1)  || (index < 0))
		    		throw new ArrayIndexOutOfBoundsException(index);
		  
		    	E oldValue = item[index]
		    	list[index] = newValue;
		    	return newValue;  	
		    	
		    }
		    */
		    
		    //@Override
		    public long getItemId(int position) {
		        return position;
		    }
		    
		   
			
			static class ViewHolder {
				public TextView food_name;		
				public TextView price;		
				public ImageView Itemimage;		
				public TextView ingredients;
				public CheckBox checkorder;
			}
		 
		    //@Override
		    public View getView(int position, View convertView, ViewGroup parent) {
		        View view = null; // If convertView wasn't null it means we have already set it to our list_item_user_video so no need to do it again
				if(convertView == null) {
		            // This is the layout we are using for each row in our list anything you declare in this layout can then be referenced below
		            convertView = mInflater.inflate(R.layout.single_row_food, parent, false);
					
					final ViewHolder viewHolder = new ViewHolder();
					viewHolder.food_name = (TextView) convertView.findViewById(R.id.txtFood_name); 
					viewHolder.price = (TextView) convertView.findViewById(R.id.txtprice);
					viewHolder.Itemimage = (ImageView) convertView.findViewById(R.id.itemsImage);
					viewHolder.checkorder = (CheckBox) convertView.findViewById(R.id.checkBoxStatus);
					viewHolder.ingredients = (TextView) convertView.findViewById(R.id.txtingredients_name);
					viewHolder.checkorder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
						@Override
			            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
							Items.get(getPosition).setSelected(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
		                }
		            });
					convertView.setTag(viewHolder);
					convertView.setTag(R.id.txtFood_name, viewHolder.food_name);
		            convertView.setTag(R.id.txtprice, viewHolder.price);
		            
		            //convertView.setTag(R.id.checkBoxStatus, viewHolder.checkorder);
				} 
				
				ViewHolder holder = (ViewHolder) convertView.getTag();
			        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
			        Typeface font_a = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Medium.ttf");
			        Typeface font_b = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf");
			        Typeface font_c = Typeface.createFromAsset(mContext.getAssets(), "fonts/DistProTh.otf");
			        Typeface font_d = Typeface.createFromAsset(mContext.getAssets(), "fonts/DroidSans.ttf");
			       
			        holder.price.setTypeface(font_b);
			        holder.food_name.setTypeface(font_a);
			        holder.ingredients.setTypeface(font_b);
				
		        // Get a single video from our list
		        Food Item = Items.get(position);
		      
		        holder.checkorder.setTag(position); // This line is important.
		        
		      	imageLoader.DisplayImage(Item.getFoodimage_url(), holder.Itemimage);
		      
		        holder.food_name.setText(Item.getFoodname());
		        holder.ingredients.setText(Item.getFoodingredients());
		        holder.price.setText("Kshs"+":"+Item.getFoodprice());
		        //holder.checkorder.setChecked(Items.get(position).isSelected());
		        holder.checkorder.setChecked(Item.isSelected());
		        
		        
                 /*String uri = Item.getStatusimage_url();
		        
		        //imageView.setImageBitmap(bmImg);
		        int imageResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());
		        Drawable res = mContext.getResources().getDrawable(imageResource);
		        holder.checkorder.setImageDrawable(res);
		        holder.checkorder.setChecked(Item.isSelected());
		      
               
		        holder.checkorder.setOnClickListener( new View.OnClickListener() {  
				     public void onClick(View v) {  
				      CheckBox cb = (CheckBox) v ;  
				      Food Item = (Food) cb.getTag();  
				      Toast.makeText(mContext,
				       "Clicked on Checkbox: " + cb.getText() +
				       " is " + cb.isChecked(), 
				       Toast.LENGTH_LONG).show();
				      Item.setSelected(cb.isChecked());
				     }  
				    });  */
		        
				
		        
		        
     
		        return convertView;
		    }
		    
		   
		}
