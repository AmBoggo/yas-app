package com.yas;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.QueryParam;
import java.util.List;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class WordResource {

    @Inject
    @RestClient
    DictionaryClient dictionaryClient;

    @GET
    @Path("/palavra-do-dia")
    public WordResponse palavraDoDia() {
        var result = dictionaryClient.search("serendipity");
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return new WordResponse("serendipity", 
            List.of(new WordResponse.Meaning("noun", 
                List.of(new WordResponse.Definition(
                    "The occurrence of events by chance in a happy or beneficial way.",
                    "Finding that bookshop was pure serendipity."
                )))),
            "/ˌserənˈdɪpɪti/");
    }

    @GET
    @Path("/buscar")
    public List<WordResponse> buscar(@QueryParam("q") String palavra) {
        return dictionaryClient.search(palavra);
    }
}
