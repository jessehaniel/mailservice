package br.com.novio.mailservice.mailservice.exception.error;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class APIError {
	@JsonProperty("erro")
	private List<ErrorItem> items = new ArrayList<>();
	
	public APIError() {
		super();
	}

	public List<ErrorItem> getItems() {
		return items;
	}

	public void setItems(List<ErrorItem> items) {
		this.items = items;
	}
}
