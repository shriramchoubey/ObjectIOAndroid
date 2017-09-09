package com.ExodiaSolutions.sunnynarang.itmexodia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

/**
 * it is a singleton class to hold Link and it is Serializable so that its object can be written 
 * Created by shriram chobuey on 1/8/2017.
 * this class is created to create the dynamic links
 */

public class Link implements Serializable{
    private static Link link = null;
    HashMap<String,String> hashMapLink = new HashMap<>();
    
	//constructor is private
	private Link(){
    }

	//this function is static so that once object is created can not be changed all through the lifecycle
    public static Link getInstance(){
        if(link == null){
            link = new Link();
            return link;
        }else{
            return link;
        }
    }

    //function to setInstance or refrence variable to the new one
	public static void setInstance(Link link1){
        link = link1;
    }

	
	//this set Hashmap object to the values
    public void setHashMapLink(JSONArray jsonArray) throws JSONException {
        for(int i=0;i<jsonArray.length();i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            hashMapLink.put(obj.getString("name"), obj.getString("link"));
        }
    }

	
	//this function is to getHashMapLink object
    public HashMap<String,String> getHashMapLink(){
        return hashMapLink;
    }

}
