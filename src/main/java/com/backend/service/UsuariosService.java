package com.backend.service;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

//Autor: Jaime Naranjo
//última Modificación : 19-11-2022

public class UsuariosService {

	private static Logger log = LoggerFactory.getLogger(UsuariosService.class);

	public String llamadaApi(String contexto) {
		String base = "https://my.api.mockaroo.com/test-tecnico/search/";

		WebClient webClient = WebClient.builder()
				.baseUrl(base)
				.defaultHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader("X-API-Key", "f2f719e0")
				.build();
		String response = webClient
				.get()
				.uri(contexto)
				.retrieve()
				.bodyToMono(String.class)
				.block(Duration.ofSeconds(3));
		log.info(response);
		return response;
	}

}
