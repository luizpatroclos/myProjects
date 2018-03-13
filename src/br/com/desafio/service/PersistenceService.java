package br.com.desafio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import br.com.desafio.bean.Planeta;



public class PersistenceService  {
	
	
	 public PersistenceService() {
		super();
		init();
	}

	
   public String PU = "desafiob2w";

   public EntityManagerFactory emf;
   
   public EntityManager em;

   private Session session;
 	
   private ConnectBD client;
   
   
public void save(Planeta planeta) {

		session.execute("INSERT INTO desafio.planeta (id, nome, clima, terreno) VALUES (uuid(), ?, ?, ?)",
				planeta.getNome(), planeta.getClima(), planeta.getTerreno());

		finish();
	}
	
   
	public List<Planeta> list() {

		ResultSet rs = session.execute("SELECT * FROM desafio.planeta");

		MappingManager manager = new MappingManager(session);

		Mapper<Planeta> mapper = manager.mapper(Planeta.class);

		Result<Planeta> planetas = mapper.map(rs);

		List<Planeta> listPlanetas = new ArrayList<>();

		for (Planeta u : planetas) {

			listPlanetas.add(u);

		}

		finish();

		return listPlanetas;
	}

	public Planeta findId(UUID id) {

		MappingManager manager = new MappingManager(session);

		Mapper<Planeta> mapper = manager.mapper(Planeta.class);

		Planeta planeta = mapper.get(id);

		finish();

		return planeta;
	}
   
	public Planeta findNome(String nome) {

		ResultSet rs = session.execute("SELECT * FROM desafio.planeta where nome='" + nome + "' ALLOW FILTERING");

		MappingManager manager = new MappingManager(session);

		Mapper<Planeta> mapper = manager.mapper(Planeta.class);

		Result<Planeta> planetas = mapper.map(rs);

		Planeta pp = planetas.one();

		finish();

		return pp;
	}
   
   
	public String removeByID(UUID id) {

		MappingManager manager = new MappingManager(session);

		Mapper<Planeta> mapper = manager.mapper(Planeta.class);

		mapper.delete(id);

		finish();

		return "";
	}

	public void init() {

		if (session == null) {

			client = new ConnectBD();

			session = client.connect("localhost", 9042);

		}
	}

	public void finish() {

		session.close();
		client.close();

	}
	
}
