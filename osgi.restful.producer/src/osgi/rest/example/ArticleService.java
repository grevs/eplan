package osgi.rest.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path( "/Articles" )
public class ArticleService {
	
	private static int counter = 0;
	private static Map<Integer, Article> articles = new HashMap<Integer, Article>();
	  
	public ArticleService() {
		for(int i=0; i<3; i++) {
			counter++;
			articles.put( counter, new Article( counter, "Article " + counter ) );
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON) 
	public Collection<Article> getArticles() {
		System.out.println("GET function");
		return articles.values();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postArticle(Article data) {
		System.out.println("POST function");
		Article article = new Article(++counter, data.getName());
	    articles.put(counter, article);
	    return Response.status(201).entity(article).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putArticle(Article data) {
		System.out.println("PUT function");
		Article article = articles.get(data.getId());
		if(article != null) {
			article.setName(data.getName());
			return Response.status(200).entity(article).build();
		}
		
		return Response.status(404).entity(data).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteArticle(@PathParam("id") String id) {
		System.out.println("DELETE function");
		Article article = articles.get(new Integer(id));
		if(article != null) {
			articles.remove(new Integer(id));
			return Response.status(200).entity("Article with id: " + id + " was deleted.").build();
		}
		return Response.status(404).entity("Article with id: " + id + " does not  exist.").build();
	}
	
}
