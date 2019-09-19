package br.com.trrsolution.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.trrsolution.dto.ListProductResponseAPI;
import br.com.trrsolution.dto.ProductResponseAPI;

public class APIUtils {
	
	private static final String API_ENDPOINT = "http://challenge-api.luizalabs.com/api/product/";
	private static final String PAGE = "?page=" ;
	
	
	/**
	 * Chamada que Busca as informacoes do Produto na API baseado no ID.
	 * 
	 * @param ID do produto cadastrado na API. 
	 * @return
	 */
	public static ProductResponseAPI getProductsAPIById(String idProductApi) throws ParseException, IOException {
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(API_ENDPOINT + idProductApi);
		getRequest.addHeader("accept", "application/json");

		HttpResponse response = httpClient.execute(getRequest);

		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			throw new RuntimeException("Failed with HTTP error code : " + statusCode);
		}

		HttpEntity httpEntity = response.getEntity();

		ProductResponseAPI productResponse = new ObjectMapper().readValue(EntityUtils.toString(httpEntity),ProductResponseAPI.class);

		return productResponse;

	}

	/**
	 * Chamada que busca todos os Produtos na API baseado na PAGINA.
	 * 
	 * @param indicativo de qual PAGINA da API deve retornar os produtos.
	 * @return
	 */
	public static HttpResponse getAllProductsByPageAPI(int page) throws ParseException, IOException {
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(API_ENDPOINT + PAGE + page);
		getRequest.addHeader("accept", "application/json");

		return httpClient.execute(getRequest);
	}
	
	/**
	 * Metodo que verifica se os produtos enviados por parametro existe no servico de API.
	 * 
	 * @param lista dos nomes dos produtos a ser verificado se existe na API. 
	 * @return
	 */
	public static List<ProductResponseAPI> verifyProductExistInReponseAPI(String[] products) throws ParseException, IOException {

		long tempoInicial = System.currentTimeMillis();

		int page = 1;
		
		Set<String> productsFound = new HashSet<String>();
		List<ProductResponseAPI> productsFoundAPI = new ArrayList<ProductResponseAPI>();
		ListProductResponseAPI listProductResponse = new ListProductResponseAPI();
		
		int statusCode;
		HttpResponse response;
		
		do {
			
			response = getAllProductsByPageAPI(page);
			statusCode = response.getStatusLine().getStatusCode();
			
			if(statusCode == 200) {
				listProductResponse = (ListProductResponseAPI) new ObjectMapper().readValue(EntityUtils.toString(response.getEntity()), ListProductResponseAPI.class);
				
				for (String productsToInsert : products) {
					
					ProductResponseAPI productInApiResponse = listProductResponse.getProducts().stream().filter(line -> line.getTitle().equals(productsToInsert.trim())).findFirst().orElse(null);

					if (productInApiResponse != null) {
						productsFoundAPI.add(productInApiResponse);
						productsFound.add(productInApiResponse.getTitle());
					}
				}
			}
			
			page++;

		} while (statusCode == 200 && productsFound.size() < products.length);
		
		long tempoFinal = System.currentTimeMillis();
		
		System.out.println("Produtos a ser adicionados no favorito: " + String.valueOf(products));
		System.out.println("Paginas Pesquisadas: " + String.valueOf(page));
		System.out.printf("%.3f segundos%n",(tempoFinal-tempoInicial)/1000d);
		System.out.println("Produtos adicionados no favoritos: " + String.valueOf(productsFoundAPI.toString()));
		System.out.println("Produtos a ser adicionados no favorito: ");
		

		return productsFoundAPI;

	}

}
