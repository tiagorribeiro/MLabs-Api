package br.com.trrsolution.dto;

import java.util.List;

public class ListProductResponseAPI {
	
	private List<ProductResponseAPI> products;
	
	private MetaResponseAPI meta;

	public List<ProductResponseAPI> getProducts() {
		return products;
	}

	public void setProducts(List<ProductResponseAPI> products) {
		this.products = products;
	}

	public MetaResponseAPI getMeta() {
		return meta;
	}

	public void setMeta(MetaResponseAPI meta) {
		this.meta = meta;
	}

}
