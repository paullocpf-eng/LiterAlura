package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.*;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.ConverteDados;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
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
                    listarLivros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
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

    private LivroRepository livroRepositorio;
    private AutorRepository autorRepositorio;

    public Principal(LivroRepository livroRepositorio, AutorRepository autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    private void buscarLivroWeb() {
        System.out.println("Digite o nome do livro para busca: ");
        var nomeLivro = leitura.nextLine();

        //Monta a URL (ex: ...search=dom+casmurro)
        var json = consummo.obterDados(ENDERECO + nomeLivro.replace(" ", "+")); //No lugar dos espaços troca para o +
        var dados = conversor.obterDados(json, DadosGutendex.class);

        //Filtrando e pegando o primeiro resultado
        Optional<DadosLivro> livroBuscado = dados.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nomeLivro.toUpperCase()))
                .findFirst();
        if (livroBuscado.isPresent()) {
            DadosLivro dadosLivro = livroBuscado.get();

            //Lógica para não duplicar o autor
            DadosAutor dadosAutor = dadosLivro.autores().get(0);

            //Buscando no banco se o autor já existe
            Autor autor = autorRepositorio.findByNomeContainingIgnoreCase(dadosAutor.nome())
                    .orElseGet(() -> {
                        //Se não econtrar, (orElseGet) cria um novo e salva
                        Autor novoAutor = new Autor(dadosAutor);
                        return autorRepositorio.save(novoAutor);
                    });

            //Salvando o livro vinculado ao autor correto
            Livro livro = new Livro(dadosLivro);
            livro.setAutor(autor);
            livroRepositorio.save(livro);

            System.out.println("Livro salvo com sucesso.");
        } else {
            System.out.println("Livro não encontrado.");
        }

        System.out.println("--- RESULTADO DA API ---");

        if (dados.resultados().isEmpty()) {
            System.out.println("Nenhum livro encontrado com esse nome.");
        } else {
            DadosLivro livroEncontrado = dados.resultados().get(0);
            System.out.println(livroEncontrado);
        }
    }

    private void listarLivros() {
        //Busca todos os livros no banco
        List<Livro> livros = livroRepositorio.findAll();

        //Ordena por padrão do banco
        livros.forEach(System.out::println);
    }

    private void listarAutores() {
        List<Autor> autores = autorRepositorio.findAll();

        autores.stream().forEach(a -> {
            System.out.println("Autor: " + a.getNome());
            System.out.println("Ano de nascimento: " + a.getAnoNascimento());
            System.out.println("Ano de falecimento: " + a.getAnoFalecimento());

            //Aqui usamos stream para pegar apenas os títulos dos livros desse autor
            List<String> livrosDoAutor = a.getLivros().stream()
                    .map(l -> l.getTitulo())
                    .toList();
            System.out.println("Livros: " + livrosDoAutor);
            System.out.println("----------------------------------------------");
        });
    }

    private void listarAutoresVivos() {
        System.out.println("Digite o ano que deseja pesquisar:");
        var ano = leitura.nextInt();
        leitura.nextLine(); //Limpeza de Buffer depois de ler número

        List<Autor> autoresVivos = autorRepositorio.obterAutoresVivosNoAno(ano);

        if (autoresVivos.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado nesse ano.");
        } else {
            System.out.println("--- AUTORES VIVOS EM " + ano + " ---");
            autoresVivos.forEach(System.out::println);
            System.out.println("-----------------------------------------");
        }
    }

}











