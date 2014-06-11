/**
 * 
 */
package com.brighthalo.webservices;
import java.io.BufferedReader;
import com.brighthalo.model.BrightHaloUserProfile;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
/**
 * @author vlad
 */

public class BrightHaloWebServices{
	public static final String ServerAccessUrl = "http://overwatch.brighthalo.com/api/v1";
	public static final String SubscriberUrl = ServerAccessUrl + "/subscriber/"; 
	public static final String Subscriber_Session_LoginUrl = ServerAccessUrl + "/subscriber_session";
	public static final String SignUpUrl = ServerAccessUrl + "/subscriber_signup";
	public static final String ApplicationJSON = "application/json";
	public static final String jSubscriber = "{\"subscriber\": ";
	
	public static JSONObject json = new JSONObject();
	public 		  HttpClient client = new DefaultHttpClient();
	public static HttpPut put = new HttpPut();
	public static HttpPost post = new HttpPost();
	public static URI url;
	public static StringEntity se;
	public static int 	  responseCode;
	public static JSONObject obj = null;
	public Context mContext;
	public static String USERID;
	public static BrightHaloUserProfile usrProfile;

	public BrightHaloWebServices (Context context){
		this.mContext = context;
		usrProfile = new BrightHaloUserProfile(this.mContext);
	}
	
	public int userLogin (String username, String password, String deviceToken){
    	try {
    		JSONObject json = new JSONObject();
    		json.put("password", password); json.put("email", username ); json.put("device_token", deviceToken);
    		
    		StringEntity se = new StringEntity( "{\"subscriber\": " + json.toString() +"}");
    		HttpClient client = new DefaultHttpClient();
    		URI url = new URI(Subscriber_Session_LoginUrl);

    		HttpPost post = new HttpPost(url);
            post.setEntity(se);
            post.setHeader("Accept", ApplicationJSON);
            post.setHeader("Content-type", ApplicationJSON);
            
            HttpResponse response = client.execute(post);
            String statusLine = response.getStatusLine().toString();
            responseCode =  response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();

          //Lets capture User's data from JSON package
          //usrProfile = new OverwatchUserProfile(mContext);
            String jsonString = EntityUtils.toString(entity);
            usrProfile.storeInfoFromSvr(jsonString);
            // If the response does not enclose an entity, there is no need
            if (entity != null) {
               InputStream instream = entity.getContent();  //A Simple JSON Response Read
               String entityStr = entity.toString();
               BufferedReader rd = new BufferedReader(new InputStreamReader(instream));
               String line;
               StringBuilder sb =  new StringBuilder();
               while ((line = rd.readLine()) != null) {
               		sb.append(line);
               }
               rd.close();
               instream.close();						                      
           }
    	}
    	catch (Throwable t) {
          //  Toast.makeText(this, "Request failed: " + t.toString(),
          //  Toast.LENGTH_LONG).show();
        }
    	return responseCode;
	}

	public void entitystream(HttpEntity entity){
		try{
	        if (entity != null) {
			        InputStream instream = entity.getContent();
			        String entityStr = entity.toString();
			        BufferedReader rd = new BufferedReader(new InputStreamReader(instream));
			        String line;
			        StringBuilder sb =  new StringBuilder();
			        while ((line = rd.readLine()) != null) {
			          		sb.append(line);
			         }
			        rd.close();
			        instream.close();
	        }
	      }
    	catch (Throwable t) {
        }
	}
	
	public int register(String email, String password, String firstname, String lastname, String phone){
		try {
			JSONObject json = new JSONObject();
			json.put("email", email); 		 json.put("password", password);
			json.put("last_name", lastname); json.put("first_name", firstname);	json.put("phone", phone);

    		StringEntity se = new StringEntity( jSubscriber + json.toString() +"}");
    		HttpClient client = new DefaultHttpClient();
    		URI url = new URI(SignUpUrl);
    		
    		setJSONPostHeader(url, se);  		
    		
            HttpResponse response = client.execute(post);
            String statusLine = response.getStatusLine().toString();
            responseCode =  response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
    		
            String jsonString = EntityUtils.toString(entity);
            usrProfile.storeInfoFromSvr(jsonString);
            
       	 // A Simple JSON Response Read
         // If the response does not enclose an entity, there is no need
            if (entity != null) {
	            InputStream instream = entity.getContent();
	            String entityStr = entity.toString();
	            BufferedReader rd = new BufferedReader(new InputStreamReader(instream));
	            String line;
	            StringBuilder sb =  new StringBuilder();
	            while ((line = rd.readLine()) != null) {
	            		sb.append(line);
	            }
	            rd.close();
	            instream.close();						                      
            }
		} catch (Throwable t) { }
		return responseCode;
	}

	public static void registerDeviceToken(String device_token){
		try{
			JSONObject json = new JSONObject();
			json.put("PushDeviceToken", device_token);

			StringEntity se = new StringEntity( "{\"subscriber\": " + json.toString() +"}");
    		HttpClient client = new DefaultHttpClient();
    		URI url = new URI("http://overwatch.brighthalo.com/api/v1/subscriber/" + USERID);
    		
    		HttpPut put = new HttpPut(url);
            put.setEntity(se);
            put.setHeader("Accept", "application/json");
            put.setHeader("Content-type", "application/json");
            put.setEntity(se);
			
            HttpResponse response = client.execute(put);
            String statusLine = response.getStatusLine().toString();
            int responseCode =  response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            if (entity != null) {
               // InputStream instream = entity.getContent();
            	// A Simple JSON Response Read
	            InputStream instream = entity.getContent();
	            String entityStr = entity.toString();
	            BufferedReader rd = new BufferedReader(new InputStreamReader(instream));
	            String line;
	            StringBuilder sb =  new StringBuilder();
	            while ((line = rd.readLine()) != null) {
	            		sb.append(line);
	            }
	            rd.close();
	            instream.close();						                      
            }
		}catch(Throwable t) {
      }
	}

	public int userProfileUpdate(String firstname, String lastname, String phonenum, String address, String disability){
		try{
			if (firstname.length()>0) { json.put("firstname", firstname);	}
			if (lastname.length()>0)  { json.put("lastname", lastname);		}
			if (phonenum.length()>0)  { json.put("phonenum", phonenum);		}
			if (address.length()>0)   { json.put("address", address);		}
			if (disability.length()>0){ json.put("disability", disability);	}
			
    		se = new StringEntity( "{\"subscriber\": " + json.toString() +"}");
    		url = new URI(SubscriberUrl + USERID);
    		
	        setJSONPutHeader(url, se);
	        HttpResponse response = client.execute(put);
	        String statusLine = response.getStatusLine().toString();
	        int responseCode =  response.getStatusLine().getStatusCode();
	        HttpEntity entity = response.getEntity();
	            // If the response does not enclose an entity, there is no need
	        if (entity != null) {
	           // InputStream instream = entity.getContent();
	           // A Simple JSON Response Read
		        InputStream instream = entity.getContent();
		        String entityStr = entity.toString();
		        BufferedReader rd = new BufferedReader(new InputStreamReader(instream));
		        String line;
		        StringBuilder sb =  new StringBuilder();
		        while ((line = rd.readLine()) != null) {
		          		sb.append(line);
		         }
		        rd.close();
		        instream.close();						                      
	       }			
		}catch (Throwable t) {
	          //  Toast.makeText(this, "Request failed: " + t.toString(), Toast.LENGTH_LONG).show();
	     }
		return responseCode;
	}

	public int userRxUpdate(String rx1,  String rx2,  String rx3,  String rx4,
			   				String dos1, String dos2, String dos3, String dos4){
		USERID = usrProfile.fetchMyUserId();
		if((USERID =="0") & (USERID == null)) {
		try{
			String rx = rx1 + ":" + dos1;
			if (rx1.length()>0)  { json.put("rx", rx);     }/*if (rx2.length()>0) { json.put("rx2", rx2);	}
			if (rx3.length()>0) { json.put("rx3", rx3);	   }  if (rx4.length()>0) { json.put("rx4", rx4);	}
			if (dos1.length()>0) { json.put("dos1", dos1); }  if (dos2.length()>0) { json.put("dos2", dos2);}
			if (dos3.length()>0) { json.put("dos3", dos3); }  if (dos4.length()>0) { json.put("dos4", dos4);} */
			se = new StringEntity( "{\"subscriber\": " + json.toString() +"}");
    		url = new URI(SubscriberUrl + USERID);
    		Log.i("Overwatch Rx PUT", se.toString()); Log.i("Overwatch Rx URL", url.toString());
	        setJSONPutHeader(url, se);
	        HttpResponse response = client.execute(put);
	        String statusLine = response.getStatusLine().toString();
	        int responseCode  = response.getStatusLine().getStatusCode();
	        HttpEntity entity = response.getEntity();
	        if (entity != null) { //If the response does not enclose an entity, there is no need
		        InputStream instream = entity.getContent();
		        String entityStr = entity.toString();
		        BufferedReader rd = new BufferedReader(new InputStreamReader(instream));
		        String line;
		        StringBuilder sb =  new StringBuilder();
		        while ((line = rd.readLine()) != null) { sb.append(line); }
		        rd.close();
		        instream.close();						                      
	        }			
		 }catch (Throwable t) {
	          //  Toast.makeText(this, "Request failed: " + t.toString(), Toast.LENGTH_LONG).show();
	    }
		 return responseCode;
	   } else {
		 return 0;
	  }
	}

	public void postMyLocation(double latitude, double longitude) {
		USERID = usrProfile.fetchMyUserId();
		try{
			json.put("latitude",  latitude);
			json.put("longitude", longitude);
    		se = new StringEntity( "{\"subscriber\": " + json.toString() +"}");
    		url = new URI(SubscriberUrl + USERID);
    		
	        setJSONPutHeader(url, se);
	        Log.i("Overwatch","Posting Loc to URL " + url);
            HttpResponse response = client.execute(put);
            String statusLine = response.getStatusLine().toString();
            int responseCode =  response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            if (entity != null) {
               // InputStream instream = entity.getContent();
            	// A Simple JSON Response Read
	            InputStream instream = entity.getContent();
	            String entityStr = entity.toString();
	            BufferedReader rd = new BufferedReader(new InputStreamReader(instream));
	            String line;
	            StringBuilder sb =  new StringBuilder();
	            while ((line = rd.readLine()) != null) { sb.append(line); }
	            rd.close();
	            instream.close();						                      
            }
		} catch (Throwable t) {
          //  Toast.makeText(this, "Request failed: " + t.toString(), Toast.LENGTH_LONG).show();
          }
	}
	
	public void setJSONPutHeader(URI url, StringEntity se) {
		put = new HttpPut(url);
		put.setEntity(se);
        put.setHeader("Accept", "application/json");
        put.setHeader("Content-type", "application/json");
	}

	public static void setJSONPostHeader(URI url, StringEntity se) {
		post = new HttpPost(url);
		post.setEntity(se);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
	}
}