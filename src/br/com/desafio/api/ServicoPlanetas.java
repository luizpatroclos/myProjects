package br.com.desafio.api;


import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.desafio.bean.Planeta;
import br.com.desafio.service.PersistenceService;





@Path("/planetasWS")
public class ServicoPlanetas {
	

	String MSG_SUCESSO_ADD = "Planeta Inserido com Sucesso !";
	
	String MSG_SUCESSO_RM = "Planeta Removido com Sucesso !";
   
     

private PersistenceService persistenceService = new PersistenceService();


@GET
@Path("/novoPlaneta/{nome}/{clima}/{terreno}")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML})
 public String novoPlaneta(@PathParam("nome")String nome, @PathParam("clima") String clima, @PathParam("terreno") String terreno) {
	
	
    Planeta p = new Planeta();
  
    p.setClima(clima);
    p.setNome(nome);
    p.setTerreno(terreno);
   
    persistenceService.save(p);

    return MSG_SUCESSO_ADD;
 
 }


@GET
@Path("/listaPlaneta")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML})
 public Response getPlanetas(@QueryParam("format") String format) {
	
    List<Planeta> list = persistenceService.list();
    
    GenericEntity<List<Planeta>> entity = new GenericEntity<List<Planeta>>(list) {};
    
    Response response = Response
            .ok(entity, "xml".equals(format) ? MediaType.APPLICATION_XML : MediaType.APPLICATION_JSON )
            .build();
    
    return response;
	
 }



@Path("/getPlanetaId/{id}")
@GET 
@Produces(MediaType.APPLICATION_JSON)
public Planeta getPlanetaId(@PathParam("id") UUID id) {
    return persistenceService.findId(id);
}




@Path("/getPlanetaNome/{nome}")
@GET 
@Produces(MediaType.APPLICATION_JSON)
public Planeta getPlanetaNome(@PathParam("nome") String nome) {
    return persistenceService.findNome(nome);
}



@Path("/rmPlanetaId/{id}")
@GET 
@Produces(MediaType.APPLICATION_JSON)
public String rmPlanetaId(@PathParam("id") UUID id) {
    
	persistenceService.removeByID(id);
	
	return MSG_SUCESSO_RM; 

 }
 
}
