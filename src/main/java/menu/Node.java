import java.util.ArrayList;
import java.util.List;

/**
 * Representa um nó genérico de uma árvore n-ária.
 * Cada nó possui um dado do tipo {@code T}, uma referência ao nó pai
 * e uma lista de nós filhos.
 *
 * @param <T> o tipo de dado armazenado no nó
 */
class Node<T> {
    private T data;
    private Node<T> parent;
    private List<Node<T>> children;

    /**
     * Cria um novo nó com o dado informado.
     *
     * @param data o dado a ser armazenado no nó
     */
    public Node(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    /**
     * Retorna o dado armazenado neste nó.
     *
     * @return o dado do nó
     */
    public T getData() {
        return data;
    }

    /**
     * Define o dado armazenado neste nó.
     *
     * @param data o novo dado do nó
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Retorna o nó pai deste nó.
     *
     * @return o nó pai, ou {@code null} se for a raiz
     */
    public Node<T> getParent() {
        return parent;
    }

    /**
     * Define o nó pai deste nó.
     *
     * @param parent o nó pai
     */
    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    /**
     * Retorna a lista de nós filhos deste nó.
     *
     * @return a lista de filhos
     */
    public List<Node<T>> getChildren() {
        return children;
    }

    /**
     * Adiciona um nó filho a este nó, definindo este nó como pai do filho.
     *
     * @param child o nó filho a ser adicionado
     */
    public void addChild(Node<T> child) {
        child.setParent(this);
        this.children.add(child);
    }

    /**
     * Remove um nó filho deste nó, desvinculando a referência de pai.
     *
     * @param child o nó filho a ser removido
     */
    public void removeChild(Node<T> child) {
        this.children.remove(child);
        child.setParent(null);
    }

    /**
     * Verifica se este nó é uma folha (não possui filhos).
     *
     * @return {@code true} se o nó não possui filhos, {@code false} caso contrário
     */
    public boolean isLeaf() {
        return this.children.isEmpty();
    }
}
