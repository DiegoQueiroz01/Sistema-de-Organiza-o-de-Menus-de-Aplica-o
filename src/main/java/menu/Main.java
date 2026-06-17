import java.util.List;
import java.util.Scanner;

/**
 * Classe principal do sistema de Organização de Menus de Aplicação.
 * Implementa um menu textual interativo no console que permite
 * ao usuário executar todas as operações sobre a árvore de menus.
 */

public class Main {

    /**
     * Método principal que inicia o sistema.
     *
     * @param args argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        MenuManager gerenciador = new MenuManager("Sistema Acadêmico");
        gerenciador.carregarExemplo();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            imprimirCabecalho();
            imprimirOpcoes();
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1"  -> criarItem(gerenciador, scanner);
                case "2"  -> associarSubmenu(gerenciador, scanner);
                case "3"  -> renomearItem(gerenciador, scanner);
                case "4"  -> removerItem(gerenciador, scanner);
                case "5"  -> exibirEstrutura(gerenciador);
                case "6"  -> buscarItem(gerenciador, scanner);
                case "7"  -> exibirCaminho(gerenciador, scanner);
                case "8"  -> listarItensFinais(gerenciador);
                case "9"  -> informarQuantidade(gerenciador);
                case "10" -> exibirArvore(gerenciador);
                case "0"  -> {
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }

            System.out.println("\nPressione Enter para continuar...");
            scanner.nextLine();
        }
    }

    /**
     * Imprime o cabeçalho do sistema no console.
     */
    private static void imprimirCabecalho() {
        System.out.println("=======================================");
        System.out.println("Sistema de Organização de Menus de Aplicação");
        System.out.println("=======================================");
    }

    /**
     * Imprime as opções do menu interativo no console.
     */
    private static void imprimirOpcoes() {
        System.out.println("1.  Criar item de menu");
        System.out.println("2.  Associar submenu a um item");
        System.out.println("3.  Renomear item de menu");
        System.out.println("4.  Remover menu e todos os seus submenus");
        System.out.println("5.  Exibir toda a estrutura de menus");
        System.out.println("6.  Buscar item pelo nome");
        System.out.println("7.  Exibir o caminho completo até um item");
        System.out.println("8.  Listar todos os itens finais de navegação");
        System.out.println("9.  Informar a quantidade total de menus e submenus");
        System.out.println("10. Exibir a árvore de menus");
        System.out.println("0.  Sair");
    }

    /**
     * Opção 1: Cria um novo item de menu sob o nó raiz.
     *
     * @param gerenciador o gerenciador de menus
     * @param scanner     o scanner para leitura de entrada
     */
    private static void criarItem(MenuManager gerenciador, Scanner scanner) {
        System.out.print("Nome do novo item: ");
        String nome = scanner.nextLine().trim();
        if (nome.isBlank()) {
            System.out.println("Erro: nome inválido.");
            return;
        }
        Node<MenuItem> novoNodo = gerenciador.criarItem(nome);
        if (novoNodo != null) {
            System.out.println("Item criado sob o nó raiz: " + novoNodo.getData().getNome());
        } else {
            System.out.println("Erro: falha ao criar item.");
        }
    }

    /**
     * Opção 2: Associa um submenu a um item existente.
     *
     * @param gerenciador o gerenciador de menus
     * @param scanner     o scanner para leitura de entrada
     */
    private static void associarSubmenu(MenuManager gerenciador, Scanner scanner) {
        System.out.print("Nome do item pai: ");
        String nomePai = scanner.nextLine().trim();
        List<Node<MenuItem>> encontrados = gerenciador.buscarPorNome(nomePai);
        if (encontrados.isEmpty()) {
            System.out.println("Erro: item pai não encontrado.");
            return;
        }
        Node<MenuItem> pai = encontrados.get(0);
        System.out.print("Nome do submenu: ");
        String nomeFilho = scanner.nextLine().trim();
        boolean sucesso = gerenciador.adicionarSubmenu(pai, nomeFilho);
        System.out.println(sucesso ? "Submenu associado com sucesso." : "Erro: falha ao associar submenu.");
    }

    /**
     * Opção 3: Renomeia um item de menu existente.
     *
     * @param gerenciador o gerenciador de menus
     * @param scanner     o scanner para leitura de entrada
     */
    private static void renomearItem(MenuManager gerenciador, Scanner scanner) {
        System.out.print("Nome do item a ser renomeado: ");
        String nome = scanner.nextLine().trim();
        List<Node<MenuItem>> encontrados = gerenciador.buscarPorNome(nome);
        if (encontrados.isEmpty()) {
            System.out.println("Erro: item não encontrado.");
            return;
        }
        System.out.print("Novo nome: ");
        String novoNome = scanner.nextLine().trim();
        boolean sucesso = gerenciador.renomearItem(encontrados.get(0), novoNome);
        System.out.println(sucesso ? "Item renomeado com sucesso." : "Erro: falha ao renomear item.");
    }

    /**
     * Opção 4: Remove um item e todos os seus submenus.
     *
     * @param gerenciador o gerenciador de menus
     * @param scanner     o scanner para leitura de entrada
     */
    private static void removerItem(MenuManager gerenciador, Scanner scanner) {
        System.out.print("Nome do item a ser removido: ");
        String nome = scanner.nextLine().trim();
        List<Node<MenuItem>> encontrados = gerenciador.buscarPorNome(nome);
        if (encontrados.isEmpty()) {
            System.out.println("Erro: item não encontrado.");
            return;
        }
        boolean sucesso = gerenciador.removerItem(encontrados.get(0));
        System.out.println(sucesso ? "Item removido com sucesso." : "Erro: não é possível remover o item raiz.");
    }

    /**
     * Opção 5: Exibe toda a estrutura de menus.
     *
     * @param gerenciador o gerenciador de menus
     */
    private static void exibirEstrutura(MenuManager gerenciador) {
        System.out.println("Estrutura de menus:");
        gerenciador.exibirEstrutura();
    }

    /**
     * Opção 6: Busca itens pelo nome.
     *
     * @param gerenciador o gerenciador de menus
     * @param scanner     o scanner para leitura de entrada
     */
    private static void buscarItem(MenuManager gerenciador, Scanner scanner) {
        System.out.print("Nome do item a buscar: ");
        String nome = scanner.nextLine().trim();
        if (nome.isBlank()) {
            System.out.println("Erro: busca inválida. O nome não pode estar em branco.");
            return;
        }
        List<Node<MenuItem>> encontrados = gerenciador.buscarPorNome(nome);
        if (encontrados.isEmpty()) {
            System.out.println("Nenhum item encontrado com o nome: " + nome);
            return;
        }
        System.out.println("\nItens encontrados (" + encontrados.size() + "):");
        for (int i = 0; i < encontrados.size(); i++) {
            Node<MenuItem> nodo = encontrados.get(i);
            System.out.println((i + 1) + ". " + nodo.getData().getNome()
                    + " [Localização: " + gerenciador.obterCaminhoCompleto(nodo) + "]");
        }
    }

    /**
     * Opção 7: Exibe o caminho completo até um item.
     *
     * @param gerenciador o gerenciador de menus
     * @param scanner     o scanner para leitura de entrada
     */
    private static void exibirCaminho(MenuManager gerenciador, Scanner scanner) {
        System.out.print("Nome do item: ");
        String nome = scanner.nextLine().trim();
        if (nome.isBlank()) {
            System.out.println("Erro: busca inválida.");
            return;
        }
        List<Node<MenuItem>> encontrados = gerenciador.buscarPorNome(nome);
        if (encontrados.isEmpty()) {
            System.out.println("Erro: item não encontrado.");
            return;
        }
        if (encontrados.size() == 1) {
            System.out.println("\nCaminho completo:\n" + gerenciador.obterCaminhoCompleto(encontrados.get(0)));
        } else {
            System.out.println("\nMúltiplos itens encontrados com esse nome:");
            for (Node<MenuItem> nodo : encontrados) {
                System.out.println("- " + gerenciador.obterCaminhoCompleto(nodo));
            }
        }
    }

    /**
     * Opção 8: Lista todos os itens finais de navegação (folhas).
     *
     * @param gerenciador o gerenciador de menus
     */
    private static void listarItensFinais(MenuManager gerenciador) {
        List<Node<MenuItem>> folhas = gerenciador.listarFolhas();
        if (folhas.isEmpty()) {
            System.out.println("Não há itens finais.");
            return;
        }
        System.out.println("Itens finais de navegação:");
        for (Node<MenuItem> folha : folhas) {
            System.out.println("- " + gerenciador.obterCaminhoCompleto(folha));
        }
    }

    /**
     * Opção 9: Informa a quantidade total de menus e submenus.
     *
     * @param gerenciador o gerenciador de menus
     */
    private static void informarQuantidade(MenuManager gerenciador) {
        gerenciador.exibirEstatisticas();
    }

    /**
     * Opção 10: Exibe a árvore de menus em formato hierárquico ASCII.
     *
     * @param gerenciador o gerenciador de menus
     */
    private static void exibirArvore(MenuManager gerenciador) {
        System.out.println("Árvore de menus:");
        gerenciador.exibirEstrutura();
    }
}
