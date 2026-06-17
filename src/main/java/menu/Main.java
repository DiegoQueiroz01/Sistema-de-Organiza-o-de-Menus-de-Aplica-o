package menu;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MenuManager manager = new MenuManager("Sistema Acadêmico");
        manager.seedExampleStructure();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printHeader();
            printOptions();
            System.out.print("Escolha uma opção: ");
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1" -> criarItem(manager, scanner);
                case "2" -> associarSubmenu(manager, scanner);
                case "3" -> renomearItem(manager, scanner);
                case "4" -> removerItem(manager, scanner);
                case "5" -> exibirEstrutura(manager);
                case "6" -> buscarItem(manager, scanner);
                case "7" -> exibirCaminho(manager, scanner);
                case "8" -> listarItensFinais(manager);
                case "9" -> informarQuantidade(manager);
                case "10" -> exibirArvore(manager);
                case "0" -> {
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

    private static void printHeader() {
        System.out.println("=======================================");
        System.out.println("Sistema de Organização de Menus de Aplicação");
        System.out.println("=======================================");
    }

    private static void printOptions() {
        System.out.println("1. Criar item de menu");
        System.out.println("2. Associar submenu a um item");
        System.out.println("3. Renomear item de menu");
        System.out.println("4. Remover menu e todos os seus submenus");
        System.out.println("5. Exibir toda a estrutura de menus");
        System.out.println("6. Buscar item pelo nome");
        System.out.println("7. Exibir o caminho completo até um item");
        System.out.println("8. Listar todos os itens finais de navegação");
        System.out.println("9. Informar a quantidade total de menus e submenus");
        System.out.println("10. Exibir a árvore de menus");
        System.out.println("0. Sair");
    }

    private static void criarItem(MenuManager manager, Scanner scanner) {
        System.out.print("Nome do novo item: ");
        String nome = scanner.nextLine().trim();
        if (nome.isBlank()) {
            System.out.println("Nome inválido.");
            return;
        }
        MenuItem novoItem = manager.createMenuItem(nome);
        manager.getRoot().addChild(novoItem);
        System.out.println("Item criado sob o nó raiz: " + novoItem.getName());
    }

    private static void associarSubmenu(MenuManager manager, Scanner scanner) {
        System.out.print("Nome do item pai: ");
        String nomePai = scanner.nextLine().trim();
        List<MenuItem> encontrado = manager.searchMenuItems(nomePai);
        if (encontrado.isEmpty()) {
            System.out.println("Item pai não encontrado.");
            return;
        }
        MenuItem pai = encontrado.get(0);
        System.out.print("Nome do submenu: ");
        String nomeFilho = scanner.nextLine().trim();
        boolean sucesso = manager.addSubmenu(pai, nomeFilho);
        System.out.println(sucesso ? "Submenu associado com sucesso." : "Falha ao associar submenu.");
    }

    private static void renomearItem(MenuManager manager, Scanner scanner) {
        System.out.print("Nome do item a ser renomeado: ");
        String nome = scanner.nextLine().trim();
        List<MenuItem> encontrados = manager.searchMenuItems(nome);
        if (encontrados.isEmpty()) {
            System.out.println("Item não encontrado.");
            return;
        }
        System.out.print("Novo nome: ");
        String novoNome = scanner.nextLine().trim();
        boolean sucesso = manager.renameItem(encontrados.get(0), novoNome);
        System.out.println(sucesso ? "Item renomeado com sucesso." : "Falha ao renomear item.");
    }

    private static void removerItem(MenuManager manager, Scanner scanner) {
        System.out.print("Nome do item a ser removido: ");
        String nome = scanner.nextLine().trim();
        List<MenuItem> encontrados = manager.searchMenuItems(nome);
        if (encontrados.isEmpty()) {
            System.out.println("Item não encontrado.");
            return;
        }
        boolean sucesso = manager.removeItem(encontrados.get(0));
        System.out.println(sucesso ? "Item removido com sucesso." : "Falha ao remover item.");
    }

    private static void exibirEstrutura(MenuManager manager) {
        System.out.println("Estrutura de menus:");
        manager.printMenuStructure();
    }

    private static void buscarItem(MenuManager manager, Scanner scanner) {
        System.out.print("Nome do item a buscar: ");
        String nome = scanner.nextLine().trim();
        if (nome.isBlank()) {
            System.out.println("Busca inválida. O nome não pode estar em branco.");
            return;
        }
        List<MenuItem> encontrados = manager.searchMenuItems(nome);
        if (encontrados.isEmpty()) {
            System.out.println("Nenhum item encontrado com o nome: " + nome);
            return;
        }
        System.out.println("\n Itens encontrados (" + encontrados.size() + "):");
        for (int i = 0; i < encontrados.size(); i++) {
            MenuItem item = encontrados.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " [ Localização: " + item.getFullPath() + "]");
        }
    }

    private static void exibirCaminho(MenuManager manager, Scanner scanner) {
        System.out.print("Nome do item: ");
        String nome = scanner.nextLine().trim();
        List<MenuItem> encontrados = manager.searchMenuItems(nome);
        if (encontrados.isEmpty() || nome.isBlank()) {
            System.out.println("Item não encontrado ou busca inválida.");
            return;
        }
        if (encontrados.size() == 1) {
            System.out.println("\n Caminho completo:\n" + encontrados.get(0).getFullPath());
        } else {
            System.out.println("\n Múltiplos itens encontrados com esse nome:");
            encontrados.forEach(item -> System.out.println("- " + item.getFullPath()));
        }
    }

    private static void listarItensFinais(MenuManager manager) {
        List<MenuItem> finais = manager.listLeafItems();
        if (finais.isEmpty()) {
            System.out.println("Não há itens finais.");
            return;
        }
        System.out.println("Itens finais de navegação:");
        finais.forEach(item -> System.out.println("- " + item.getFullPath()));
    }

    private static void informarQuantidade(MenuManager manager) {
        manager.showMenuStatistics();
    }

    private static void exibirArvore(MenuManager manager) {
        System.out.println("Árvore de menus:");
        manager.printMenuStructure();
    }
}
