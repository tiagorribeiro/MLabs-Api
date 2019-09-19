package br.com.trrsolution.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Clientes")
public class ClientEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2501147367979051934L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
    @Column(name = "name")
	private String name;
	
    @Column(name = "email")
	private String email;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "cliente", fetch= FetchType.EAGER, orphanRemoval=true)
	private Set<ProductEntity> productsIds;
	
	public ClientEntity() {
		super();
	}

	public ClientEntity(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	
	public ClientEntity(String name, String email, Set<ProductEntity> productsIds) {
		super();
		this.name = name;
		this.email = email;
		this.productsIds = productsIds;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Set<ProductEntity> getProductsIds() {
		return productsIds;
	}

	public void setProductsIds(Set<ProductEntity> productsIds) {
		this.productsIds = productsIds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientEntity other = (ClientEntity) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
