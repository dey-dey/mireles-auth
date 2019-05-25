package com.deydey.dto;


import lombok.Data;

@Data
public class GenericResponseDto {
	private String message;
	private String error;

	public GenericResponseDto(String message) {
		super();
		this.message = message;
	}

	public GenericResponseDto(String message, String error) {
		super();
		this.message = message;
		this.error = error;
	}
}