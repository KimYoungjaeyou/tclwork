package com.tcl.es.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class tclworkServiceImpl implements tclworkService {
	
	@Autowired
	private RestClient client;
	
    @Autowired
    public tclworkServiceImpl(RestClient client) {
        this.client = client;
    }

	@Override
	public String esAnalysis(String analyzer,String text) {
		String url = "nori_sample/_analyze";
		String querydsl = "{ " +
							" \"analyzer\" : \"" + analyzer + "\" ," +
							 " \"text\" : \"" + text + "\"  ,"+
							  " \"attributes\" : [\"leftPOS\"]," +
							  " \"explain\" : true }";
		//System.out.println(querydsl);
		Request request = new Request("GET", url);
		request.setJsonEntity(querydsl);
		JsonObject result = null ;
		try {
			Response response = client.performRequest(request);
			String responseBody = EntityUtils.toString(response.getEntity());
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(responseBody);
			result = element.getAsJsonObject();
			//System.out.println("element!!! " + element.toString());
			getGeyValue(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return result.toString();

	}
	
	public void getGeyValue(JsonObject obj) {
		
		Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();//will return members of your object
		for (Map.Entry<String, JsonElement> entry: entries) {
		    Iterator<Entry<String, JsonElement>> itr = entries.iterator();
		    while(itr.hasNext()) {
		    	
		    	if ( entry.getValue() instanceof JsonObject) {
		    		getGeyValue((JsonObject)entry.getValue());
		    		System.out.println("여기는 재호출은 한 곳이다. #####\n"+entry.getValue());
		    		System.out.println(entry.getValue() instanceof JsonObject);
		    	}else {
		    		System.out.println("여기는 재호출을 안했따...\n");
		    		System.out.println(entry.getValue() instanceof JsonObject);
		    		itr.remove();
		    		//itr.remove();
		    	}
		    	
		    }
		}
		
//		System.out.println(i);
//		while(i.hasNext()) {
//			String b = i.next().toString();
//			
//		}
			
	}
}
