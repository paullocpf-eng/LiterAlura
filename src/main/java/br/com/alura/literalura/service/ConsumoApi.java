package br.com.alura.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {
    public String obterDados(String endereco) {
        //1. O CLIENTE: Quem vai fazer a chamada
        HttpClient client = HttpClient.newHttpClient();

        //2. A SOLICITAÇÃO (Request): Onde vamos e o que queremos (GET)
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();

        HttpResponse<String> response = null;
        try {
            //3. A RESPOSTA (Response): O que o servidor devolveu
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

        //Retornando o corpo (JSON) da resposta
        return response.body();
    }
}
