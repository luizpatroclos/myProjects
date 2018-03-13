package br.com.desafio.bean;

import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.Transient;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;



@Table(keyspace = "desafio", name = "planeta")
public class Planeta {

	
	@PartitionKey(0)
    @Column(name = "id")
	UUID id;
	
    @Column(name = "nome")
	String nome;
	
    @Column(name = "clima")
	String clima;
	
    @Column(name = "terreno")
	String terreno;
	
	@Transient
	int qtd;
	

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	public String getTerreno() {
		return terreno;
	}

	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}

	public int getQtd() {
		
		
		int qtd = 0;
		int p = 1;
		String nomePlaneta = null;
		boolean encontrou = false;
		
		try {

			Client client = Client.create();
			
			for (int t = 0; t < p; t++) {

			WebResource webResource = client.resource("https://swapi.co/api/planets/?page="+p);	
						
			ClientResponse response = webResource.accept("application/json").header("user-agent", "").get(ClientResponse.class);

			if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}

			String output = response.getEntity(String.class);

			JSONObject raiz = new JSONObject(output);
			
			JSONArray arrPlanetas = raiz.getJSONArray("results");
				
					for (int i=0; i < arrPlanetas.length(); i++) {
						
				 
						JSONObject f = arrPlanetas.getJSONObject(i);
						
						JSONArray filmes = f.getJSONArray("films");
						
						nomePlaneta = f.getString("name");
						
						if(nome.equals(nomePlaneta)){
							qtd = filmes.length();
							encontrou = true;
						}
					
					}
			
					p++;
					
					if(encontrou){
						break;
					}
			}

		  } catch (Exception e) {

			e.printStackTrace();

		  }
	
		return qtd;
	}
		

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
}
