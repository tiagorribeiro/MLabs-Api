package br.com.trrsolution.dto;

import java.util.List;

public class ClientDto {
	
	private String name;
	
    private String email;
    
    private List<ProductDto> favoriteProducts;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<ProductDto> getFavoriteProducts() {
		return favoriteProducts;
	}

	public void setFavoriteProducts(List<ProductDto> favoriteProducts) {
		this.favoriteProducts = favoriteProducts;
	}
    
}
