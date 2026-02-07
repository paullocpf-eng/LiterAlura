package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

//Ignora tudo que vier no JSON que não esteja listado abaixo
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DadosAutor> autores, //Será criada uma classe DadosAutor
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Double numeroDowloads
) {
}
