package br.com.trrsolution.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.trrsolution.dto.ClientDto;
import br.com.trrsolution.exceptions.ValidationClientException;
import br.com.trrsolution.model.ClientEntity;
import br.com.trrsolution.service.ClientService;

@WebServlet("/Cliente")
public class ClienteResource extends HttpServlet {
	
	ClientService service = new ClientService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		PrintWriter out = response.getWriter();
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			
			ClientDto clientSaved = service.findClientAndValidFavoriteList(new ClientEntity(name, email));
			out.println(mapper.writeValueAsString(clientSaved));
			
		} catch (ParseException e) {
			out.println(e.getMessage());
		}
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		PrintWriter out = response.getWriter();
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			
			ClientEntity clientSaved = service.save(new ClientEntity(name, email));
			out.println(mapper.writeValueAsString(clientSaved));
		
		} catch (ValidationClientException e) {
			out.println(e.getMessage());
		}
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String produtos = request.getParameter("produtos");
		
		PrintWriter out = response.getWriter();
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			
			ClientEntity clientSaved = service.updateClient(new ClientEntity(name, email), produtos.split(";"));
			out.println(mapper.writeValueAsString(clientSaved));
		
		} catch (ParseException e) {
			out.println(e.getMessage());
		}
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		PrintWriter out = response.getWriter();
		
		ObjectMapper mapper = new ObjectMapper();
			
		service.delete(new ClientEntity(name, email));
		out.println(mapper.writeValueAsString("Cliente Removido com SUCESSO"));
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
	}

}
