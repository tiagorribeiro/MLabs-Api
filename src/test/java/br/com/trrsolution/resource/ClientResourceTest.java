package br.com.trrsolution.resource;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.trrsolution.dao.ClientDao;
import br.com.trrsolution.dto.ClientDto;
import br.com.trrsolution.dto.ProductResponseAPI;
import br.com.trrsolution.exceptions.ValidationClientException;
import br.com.trrsolution.model.ClientEntity;
import br.com.trrsolution.service.ClientService;
import br.com.trrsolution.util.APIUtils;
import br.com.trrsolution.util.HibernateUtil;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientResourceTest {

	@Mock
	ClientDao dao;
	
	ClientService service;

	@Before
	public void beforeEachTest() {
		service = new ClientService();
		MockitoAnnotations.initMocks(this);
	}
// 
//  @After
//  public void afterEachTest() {
//  	System.out.println("This is exceuted after each Test");
//  }

	@Test
	public void t1_databaseConection() {
		HibernateUtil.getSessionFactory();
	}
	
//	@Test
	public void t2_callApiAllProducts() throws ValidationClientException, ParseException, IOException {
		
		String products = "Cadeira para Auto Iseos Bébé Confort Earth Brown; Churrasqueira Elétrica Mondial 1800W";
		
		List<ProductResponseAPI> productsFoundAPI = APIUtils.verifyProductExistInReponseAPI(products.split(";"));
		assertTrue(!productsFoundAPI.isEmpty());
	}
	
	@Test
	public void t2_save() throws ValidationClientException {
		
//		dao = Mockito.mock(ClientDao.class);
//		Mockito.when(dao.findClient(Mockito.any())).thenReturn(null);
		
		ClientEntity clientSaved = service.save(new ClientEntity("Tiago","Email"));
		assertTrue(clientSaved.getId() != null);
	}

	@Test(expected = ValidationClientException.class)
	public void t3_saveWithException() throws ValidationClientException{
		ClientService service = new ClientService();
		ClientEntity clientSaved = service.save(new ClientEntity("Tiago","Email"));
	}
	
	@Test
	public void t4_addProductListLastPage() throws ParseException, IOException{
		
		ClientService service = new ClientService();
		
		String name = "Tiago Ribeiro";
		String email = "Email";
		String nomeProdutos = "Cadeira para Auto Iseos Bébé Confort Earth Brown; Teste; Churrasqueira Elétrica Mondial 1800W; Jurassic World Tyrannasaurus Rex";
		
		service.updateClient(new ClientEntity(name, email), nomeProdutos.split(";"));
	}
	
	@Test
	public void t4_addProductListFirstPage() throws ParseException, IOException{
		
		ClientService service = new ClientService();
		
		String name = "Tiago Ribeiro";
		String email = "Email";
		String nomeProdutos = "Cadeira para Auto Iseos Bébé Confort Earth Brown";
		
		service.updateClient(new ClientEntity(name, email), nomeProdutos.split(";"));
	}
	
	@Test
	public void t4_getClientAndList() throws ParseException, IOException{
		
		ClientService service = new ClientService();
		
		String name = "Tiago Ribeiro";
		String email = "Email";
		
		ClientDto clientDto = service.findClientAndValidFavoriteList(new ClientEntity(name,email));
		assertTrue(!clientDto.getEmail().isEmpty() && !clientDto.getFavoriteProducts().isEmpty());
	}
		
	@Test
	public void t5_deleteClient(){
		
		ClientService service = new ClientService();

		service.delete(new ClientEntity("Tiago Ribeiro", "Email"));
		
		ClientEntity clientSaved = service.findClient(new ClientEntity("Tiago Ribeiro", "Email"));
		
		assertTrue(clientSaved.getId() == null);
		
	}

}
