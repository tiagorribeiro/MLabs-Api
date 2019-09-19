package br.com.trrsolution.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import br.com.trrsolution.dao.ClientDao;
import br.com.trrsolution.dto.ClientDto;
import br.com.trrsolution.dto.ProductDto;
import br.com.trrsolution.dto.ProductResponseAPI;
import br.com.trrsolution.exceptions.ValidationClientException;
import br.com.trrsolution.model.ClientEntity;
import br.com.trrsolution.model.ProductEntity;
import br.com.trrsolution.util.APIUtils;

public class ClientService {

	ClientDao repo = new ClientDao();

	/**
	 * Metodo que Salva o cliente na base de dados.
	 * 
	 * @param ClientEntity Object a ser salvo com Nome e Email. 
	 * @return Object ClientEntity salvo.
	 */
	public ClientEntity save(ClientEntity client) throws ValidationClientException {
		
		if(findClient(client).getId() != null){
			throw new ValidationClientException("Email já utilizado");
		};
		
		return repo.save(client);
	}

	/**
	 * Metodo que busca o cliente na base de dados por EMAIL
	 * 
	 * @param ClientEntity Object com Nome e Email. 
	 * @return List<ClientEntity> Objects salvos baseados no email..
	 */
	public ClientEntity findClient(ClientEntity client) {

		List<ClientEntity> listClient = repo.findClient(client);
		
		if(!listClient.isEmpty()) {
			return listClient.get(0);
		}
				
		return client;
	}
	
	/**
	 * Metodo que busca o cliente na base de dados por EMAIL
	 * 
	 * @param ClientEntity Object com Nome e Email. 
	 * @return List<ClientEntity> Objects salvos baseados no email e sua lista de produtos favoritos.
	 */
	public ClientDto findClientAndValidFavoriteList(ClientEntity client) throws ParseException, IOException {

		List<ClientEntity> listClient = repo.findClient(client);
		ClientDto clientDto = new ClientDto();
		
		if(!listClient.isEmpty()) {
			
			client = listClient.get(0);
			
			clientDto.setName(client.getName());
			clientDto.setEmail(client.getEmail());
			
			clientDto.setFavoriteProducts(new ArrayList<ProductDto>());
			
			for (ProductEntity productEntity : client.getProductsIds()) {
				ProductResponseAPI responseApi = APIUtils.getProductsAPIById(productEntity.getIdApi());
				
				ProductDto productDto = new ProductDto();
				productDto.setIdApi(responseApi.getId());
				productDto.setTitle(responseApi.getTitle());
				productDto.setPrice(responseApi.getPrice());
				productDto.setImage(responseApi.getImage());
				
				clientDto.getFavoriteProducts().add(productDto);
			}
		}
				
		return clientDto;
	}


	/**
	 * Metodo que deleta o cliente na base de dados.
	 * 
	 * @param ClientEntity Object com Nome e Email. 
	 * 
	 */
	public void delete(ClientEntity client){
		List<ClientEntity> listClient = repo.findClient(client);
		if(!listClient.isEmpty()) {
			repo.delete(listClient.get(0));
		}
    }

	/**
	 * Metodo que altera o cliente e sua lista de produtos favoritos na base de dados.
	 *
	 * @param ClientEntity Object com Nome e Email. 
	 */
	public ClientEntity updateClient(ClientEntity client, String[] products) throws ParseException, IOException{
		
		List<ClientEntity> listClient = repo.findClient(client);

		if (!listClient.isEmpty()) {
			
			client = listClient.get(0);
			client.getProductsIds().clear();

			List<ProductResponseAPI> productsFoundAPI = APIUtils.verifyProductExistInReponseAPI(products);
			
			for (ProductResponseAPI productResponseAPI : productsFoundAPI) {
				
				ProductEntity productToList = new ProductEntity();
				productToList.setName(productResponseAPI.getTitle());
				productToList.setIdApi(productResponseAPI.getId());
				productToList.setCliente(client);

				if (!client.getProductsIds().contains(productToList))
					client.getProductsIds().add(productToList);
				
			}

			repo.update(client);
		}	
			
		return client;
    }

}
