package com.payment.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.payment.Exception.ExternalAPIException;

@Service
public class HttpRequestService {

	public HttpURLConnection getConnection(String Url) {
		URL url = null;
		HttpURLConnection conn = null;
		try {
			url = new URL(Url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5 * 1000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization",
					"Basic B-qa2-0-5f031cdd-0-302d0214496be84732a01f690268d3b8eb72e5b8ccf94e2202150085913117f2e1a8531505ee8ccfc8e98df3cf1748");
		} catch (Exception e) {
			return null;
		}
		return conn;
	}

	public boolean writeData(HttpURLConnection conn, String obj) throws ExternalAPIException, IOException {
		OutputStream wr = null;
		boolean result = false;
		wr = conn.getOutputStream();
		wr.write(obj.toString().getBytes("UTF-8"));
		wr.flush();
		result = true;
		return result;
	}
	
	public static JSONArray getReturnLinks() {
		JSONArray array = new JSONArray();
		JSONObject json1 = new JSONObject();
		json1.put("rel", "on_completed");
		json1.put("href", "https://US_commerce_site/payment/return/success");
		json1.put("method", "GET");
		array.put(json1);
		JSONObject json2 = new JSONObject();
		json2.put("rel", "on_failed");
		json2.put("href", "https://US_commerce_site/payment/return/failed");
		json2.put("method", "GET");
		array.put(json2);
		return array;
	}

	public JSONObject readResponse(HttpURLConnection connection) {
		JSONObject response = new JSONObject();
		try {
			int code = connection.getResponseCode();
			
			BufferedReader in=null;
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuffer responsedata = new StringBuffer();
            String c;
            while ((c = in.readLine()) != null)
                responsedata.append((String) c); 
            
            response = new JSONObject(responsedata);
            response.put("responseCode", code);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
}
