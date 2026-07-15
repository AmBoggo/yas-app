package com.yas;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Produces;
import java.util.List;

@Path("/api/v2/entries/en")
@RegisterRestClient(configKey = "dictionary-api")
public interface DictionaryClient {

    @GET
    @Path("/{word}")
    @Produces(MediaType.APPLICATION_JSON)
    List<WordResponse> search(@PathParam("word") String word);
}
