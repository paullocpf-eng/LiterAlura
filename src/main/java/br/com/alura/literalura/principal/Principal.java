package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.DadosGutendex;
import br.com.alura.literalura.model.DadosLivro;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.ConverteDados;

import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consummo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                       ESCOLHA UMA OPÇÃO:
                       1 - Buscar livro pelo Título
                       2 - Listar livros registrados
                       3 - Listar autores registrados
                       4 - Listar autores vivos em determinado ano
                       5 - Listar livros em um determinado idioma
                       
                       0 - Sair
                       """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine(); //Limpa o buffer do teclado após ler um número

            switch (opcao) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    System.out.println("Ainda não pronto");
                    break;
                case 3:
                    System.out.println("Ainda não pronto");
                    break;
                case 4:
                    System.out.println("Ainda não pronto");
                    break;
                case 5:
                    System.out.println("Ainda não pronto");
                    break;
                case 0:
                    System.out.println("Saindo...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida");

            }
        }
    }

    private void buscarLivroWeb() {
        System.out.println("Digite o nome do livro para busca: ");
        var nomeLivro = leitura.nextLine();

        //Monta a URL (ex: ...search=dom+casmurro)
        var enderecoBusca = ENDERECO + nomeLivro.replace(" ", "+"); //No lugar dos espaços troca para o +

        var json = consummo.obterDados(enderecoBusca);
        var dados = conversor.obterDados(json, DadosGutendex.class);

        System.out.println("--- RESULTADO DA API ---");

        if (dados.resultados().isEmpty()) {
            System.out.println("Nenhum livro encontrado com esse nome.");
        } else {
            DadosLivro livroEncontrado = dados.resultados().get(0);
            System.out.println(livroEncontrado);
        }
    }
}











