import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gerenciador da árvore de menus da aplicação.
 * Utiliza a estrutura genérica {@link Node}{@code <}{@link MenuItem}{@code >}
 * para organizar os itens de menu de forma hierárquica.
 * Implementa todas as operações de criação, busca, remoção,
 * renomeação, exibição e contagem de menus e submenus.
 */
public class MenuManager {
    private final Node<MenuItem> raiz;

    /**
     * Cria um novo gerenciador de menus com o nome raiz informado.
     *
     * @param nomeRaiz o nome do item raiz da árvore de menus
     */
    public MenuManager(String nomeRaiz) {
        this.raiz = new Node<>(new MenuItem(nomeRaiz));
    }

    /**
     * Retorna o nó raiz da árvore de menus.
     *
     * @return o nó raiz
     */
    public Node<MenuItem> getRaiz() {
        return raiz;
    }

    // =====================================================================
    // 1. Criar item de menu
    // =====================================================================

    /**
     * Cria um novo nó de menu com o nome informado e o adiciona como
     * filho do nó raiz.
     *
     * @param nome o nome do novo item de menu
     * @return o nó criado, ou {@code null} se o nome for inválido
     */
    public Node<MenuItem> criarItem(String nome) {
        if (nome == null || nome.isBlank()) {
            return null;
        }
        Node<MenuItem> novoNodo = new Node<>(new MenuItem(nome));
        raiz.addChild(novoNodo);
        return novoNodo;
    }

    // =====================================================================
    // 2. Associar submenu a um item existente
    // =====================================================================

    /**
     * Cria um novo submenu e o associa como filho do nó pai informado.
     *
     * @param pai       o nó pai ao qual o submenu será associado
     * @param nomeFilho o nome do submenu a ser criado
     * @return {@code true} se o submenu foi associado com sucesso
     */
    public boolean adicionarSubmenu(Node<MenuItem> pai, String nomeFilho) {
        if (pai == null || nomeFilho == null || nomeFilho.isBlank()) {
            return false;
        }
        Node<MenuItem> filho = new Node<>(new MenuItem(nomeFilho));
        pai.addChild(filho);
        return true;
    }

    // =====================================================================
    // 3. Renomear item de menu
    // =====================================================================

    /**
     * Renomeia o item de menu contido no nó informado.
     *
     * @param nodo     o nó cujo item será renomeado
     * @param novoNome o novo nome para o item
     * @return {@code true} se o item foi renomeado com sucesso
     */
    public boolean renomearItem(Node<MenuItem> nodo, String novoNome) {
        if (nodo == null || novoNome == null || novoNome.isBlank()) {
            return false;
        }
        nodo.getData().setNome(novoNome);
        return true;
    }

    // =====================================================================
    // 4. Remover menu e todos os seus submenus
    // =====================================================================

    /**
     * Remove o nó informado e todos os seus submenus da árvore.
     * Não permite remover o nó raiz.
     *
     * @param nodo o nó a ser removido
     * @return {@code true} se o nó foi removido com sucesso
     */
    public boolean removerItem(Node<MenuItem> nodo) {
        if (nodo == null || nodo == raiz) {
            return false;
        }
        Node<MenuItem> pai = nodo.getParent();
        if (pai != null) {
            pai.removeChild(nodo);
            return true;
        }
        return false;
    }

    // =====================================================================
    // 5. Exibir toda a estrutura de menus
    // =====================================================================

    /**
     * Exibe toda a estrutura de menus no console em formato
     * hierárquico com caracteres ASCII de árvore.
     */
    public void exibirEstrutura() {
        System.out.println(raiz.getData().getNome());
        List<Node<MenuItem>> filhos = raiz.getChildren();
        for (int i = 0; i < filhos.size(); i++) {
            imprimirArvoreRecursivo(filhos.get(i), "", i == filhos.size() - 1);
        }
    }

    // =====================================================================
    // 6. Buscar item pelo nome
    // =====================================================================

    /**
     * Busca todos os nós cujo item de menu possui o nome informado
     * (busca sem distinção de maiúsculas/minúsculas).
     *
     * @param nome o nome a ser buscado
     * @return lista de nós encontrados (pode ser vazia)
     */
    public List<Node<MenuItem>> buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return new ArrayList<>();
        }
        List<Node<MenuItem>> resultados = new ArrayList<>();
        buscarRecursivo(raiz, nome, resultados);
        return resultados;
    }

    /**
     * Método recursivo auxiliar para buscar nós pelo nome.
     *
     * @param nodo       o nó atual da busca
     * @param nome       o nome buscado
     * @param resultados lista acumuladora de resultados
     */
    private void buscarRecursivo(Node<MenuItem> nodo, String nome, List<Node<MenuItem>> resultados) {
        if (nodo.getData().getNome().equalsIgnoreCase(nome)) {
            resultados.add(nodo);
        }
        for (Node<MenuItem> filho : nodo.getChildren()) {
            buscarRecursivo(filho, nome, resultados);
        }
    }

    // =====================================================================
    // 7. Exibir caminho completo até um item
    // =====================================================================

    /**
     * Retorna o caminho completo desde a raiz até o nó informado,
     * no formato "Raiz > Filho > Neto".
     *
     * @param nodo o nó de destino
     * @return o caminho completo como String
     */
    public String obterCaminhoCompleto(Node<MenuItem> nodo) {
        if (nodo == null) {
            return "";
        }
        List<String> caminho = new ArrayList<>();
        Node<MenuItem> atual = nodo;
        while (atual != null) {
            caminho.add(atual.getData().getNome());
            atual = atual.getParent();
        }
        Collections.reverse(caminho);
        return String.join(" > ", caminho);
    }

    // =====================================================================
    // 8. Listar itens finais (folhas)
    // =====================================================================

    /**
     * Retorna todos os nós folha da árvore de menus
     * (itens finais de navegação, sem submenus).
     *
     * @return lista de nós folha
     */
    public List<Node<MenuItem>> listarFolhas() {
        List<Node<MenuItem>> folhas = new ArrayList<>();
        coletarFolhasRecursivo(raiz, folhas);
        return folhas;
    }

    /**
     * Método recursivo auxiliar para coletar nós folha.
     *
     * @param nodo   o nó atual
     * @param folhas lista acumuladora de folhas
     */
    private void coletarFolhasRecursivo(Node<MenuItem> nodo, List<Node<MenuItem>> folhas) {
        if (nodo.isLeaf()) {
            folhas.add(nodo);
        } else {
            for (Node<MenuItem> filho : nodo.getChildren()) {
                coletarFolhasRecursivo(filho, folhas);
            }
        }
    }

    // =====================================================================
    // 9. Quantidade total de menus e submenus
    // =====================================================================

    /**
     * Retorna a quantidade total de itens na árvore (incluindo a raiz).
     *
     * @return o número total de itens
     */
    public int contarTotal() {
        return contarRecursivo(raiz);
    }

    /**
     * Retorna a quantidade total de submenus (filhos em todos os níveis).
     *
     * @return o número total de submenus
     */
    public int contarSubmenus() {
        return contarSubmenusRecursivo(raiz);
    }

    /**
     * Método recursivo auxiliar para contar todos os nós.
     *
     * @param nodo o nó atual
     * @return a quantidade de nós a partir deste nó (inclusive)
     */
    private int contarRecursivo(Node<MenuItem> nodo) {
        int contagem = 1;
        for (Node<MenuItem> filho : nodo.getChildren()) {
            contagem += contarRecursivo(filho);
        }
        return contagem;
    }

    /**
     * Método recursivo auxiliar para contar submenus.
     *
     * @param nodo o nó atual
     * @return a quantidade de submenus a partir deste nó
     */
    private int contarSubmenusRecursivo(Node<MenuItem> nodo) {
        int contagem = nodo.getChildren().size();
        for (Node<MenuItem> filho : nodo.getChildren()) {
            contagem += contarSubmenusRecursivo(filho);
        }
        return contagem;
    }

    /**
     * Exibe no console as estatísticas de quantidade total de menus e submenus.
     */
    public void exibirEstatisticas() {
        System.out.println("Quantidade total de menus: " + contarTotal());
        System.out.println("Quantidade total de submenus: " + contarSubmenus());
    }

    // =====================================================================
    // 10. Exibir árvore em formato hierárquico ASCII
    // =====================================================================

    /**
     * Método recursivo auxiliar para imprimir a árvore com caracteres ASCII.
     * Produz saída no formato:
     * <pre>
     * Sistema Acadêmico
     * ├── Cadastros
     * │   ├── Alunos
     * │   └── Professores
     * └── Configurações
     * </pre>
     *
     * @param nodo      o nó atual a ser impresso
     * @param prefixo   o prefixo de indentação acumulado
     * @param ehUltimo  indica se o nó é o último filho do seu pai
     */
    private void imprimirArvoreRecursivo(Node<MenuItem> nodo, String prefixo, boolean ehUltimo) {
        String conector = ehUltimo ? "└── " : "├── ";
        System.out.println(prefixo + conector + nodo.getData().getNome());

        String novoPrefixo = prefixo + (ehUltimo ? "    " : "│   ");
        List<Node<MenuItem>> filhos = nodo.getChildren();
        for (int i = 0; i < filhos.size(); i++) {
            imprimirArvoreRecursivo(filhos.get(i), novoPrefixo, i == filhos.size() - 1);
        }
    }

    // =====================================================================
    // Estrutura de exemplo
    // =====================================================================

    /**
     * Popula a árvore com a estrutura de exemplo do Sistema Acadêmico
     * para fins de demonstração e testes.
     */
    public void carregarExemplo() {
        Node<MenuItem> cadastros = new Node<>(new MenuItem("Cadastros"));
        Node<MenuItem> alunos = new Node<>(new MenuItem("Alunos"));
        Node<MenuItem> novoAluno = new Node<>(new MenuItem("Novo Aluno"));
        Node<MenuItem> consultarAluno = new Node<>(new MenuItem("Consultar Aluno"));
        Node<MenuItem> professores = new Node<>(new MenuItem("Professores"));
        Node<MenuItem> relatorios = new Node<>(new MenuItem("Relatórios"));
        Node<MenuItem> frequencia = new Node<>(new MenuItem("Frequência"));
        Node<MenuItem> desempenho = new Node<>(new MenuItem("Desempenho"));
        Node<MenuItem> configuracoes = new Node<>(new MenuItem("Configurações"));

        raiz.addChild(cadastros);
        cadastros.addChild(alunos);
        alunos.addChild(novoAluno);
        alunos.addChild(consultarAluno);
        cadastros.addChild(professores);
        raiz.addChild(relatorios);
        relatorios.addChild(frequencia);
        relatorios.addChild(desempenho);
        raiz.addChild(configuracoes);
    }
}
