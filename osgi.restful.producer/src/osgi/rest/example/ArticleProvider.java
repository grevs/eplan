package osgi.rest.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;

@Provider
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class ArticleProvider<Article> implements MessageBodyReader<Article>, MessageBodyWriter<Article> {

  private final static String CODING = "utf-8";
  private Gson gson;

  public ArticleProvider() {
    gson = new Gson();
  }
  
  public Gson getGson() {
    return gson;
  }
  
  public void setGson( Gson gson ) {
    validateGson( gson );
    this.gson = gson;
  }

  private void validateGson( Gson gson ) {
    if( gson == null ) {
      throw new IllegalArgumentException( "GSON object cannot be null" );
    }
  }

  @Override
  public long getSize( Article t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType  ) {
    return -1;
  }

  @Override
  public boolean isWriteable( Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType ) {
    return true;
  }

  @Override
  public void writeTo( Article object,
                       Class<?> type,
                       Type genericType,
                       Annotation[] annotations,
                       MediaType mediaType,
                       MultivaluedMap<String, Object> httpHeaders,
                       OutputStream entityStream ) throws IOException, WebApplicationException
  {
    try( OutputStream stream = entityStream ) {
      entityStream.write( gson.toJson( object ).getBytes( CODING ) );
      entityStream.flush();
    }
  }

  @Override
  public boolean isReadable( Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType ) {
    return true;
  }

  @Override
  public Article readFrom( Class<Article> type,
                     Type gnericType,
                     Annotation[] annotations,
                     MediaType mediaType,
                     MultivaluedMap<String, String> httpHeaders,
                     InputStream entityStream ) throws IOException, WebApplicationException
  {
    try( InputStreamReader reader = new InputStreamReader( entityStream, CODING ) ) {
      return gson.fromJson( reader, type );
    }
  }
}
