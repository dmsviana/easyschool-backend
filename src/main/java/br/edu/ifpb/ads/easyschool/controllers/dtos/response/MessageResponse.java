package br.edu.ifpb.ads.easyschool.controllers.dtos.response;

import lombok.Data;

@Data
public class MessageResponse {
	private String message;

	public MessageResponse(final String message) {
	    this.message = message;
	  }
}