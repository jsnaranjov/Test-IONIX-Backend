package com.backend.controller;


//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

//Autor: Jaime Naranjo
//última Modificación : 19-11-2022

@Component
public class JsonController {
	
	private static Logger log = LoggerFactory.getLogger(JsonController.class);
	

	public String procesarData(String data, Long Tiempo) {
		try {
			
			JSONObject jsonCompleto= new JSONObject(data);	
			JSONObject result = new JSONObject();
			JSONObject resultFinal = new JSONObject();
			JSONArray items = new JSONArray();
			result = jsonCompleto.getJSONObject("result");
			items = result.getJSONArray("items");
			
			log.info(items.toString());
			
			JSONObject jsonModificado = new JSONObject();
			int responseCode = -100;			
			if (jsonCompleto.getInt("responseCode") == 200) {
				responseCode = 0;
			}
			jsonModificado.put("responseCode", responseCode);
			String description =  jsonCompleto.getString("description");
			jsonModificado.put("description", description);
			jsonModificado.put("elapsedTimpe", Tiempo);	
			resultFinal.put("registerCount", items.length());
			jsonModificado.put("result",resultFinal);	
			return jsonModificado.toString();
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error al transformar data : " + e.getMessage());
			return "Error al transformar data : "+ e.getMessage();
		}

	}
	
}
