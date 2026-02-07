package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

//Esse record coleta os dados dos Livros que estão dentro de "results" no JSON da API Gutendex
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosGutendex(
        @JsonAlias("results") List<DadosLivro> resultados
) {
}
