package br.com.alura.literalura.service;

public interface IConverteDados {
    // O <T> significa "Genérico". Eu passo o JSON e a classe que queremos que ele vire.
    <T> T obterDados(String json, Class<T> classe);
}
