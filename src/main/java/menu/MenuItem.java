/**
 * Representa um item de menu da aplicação.
 * Contém apenas o nome do item, servindo como dado ({@code T})
 * armazenado dentro de um {@link Node}.
 */
public class MenuItem {
    private String nome;

    /**
     * Cria um novo item de menu com o nome informado.
     *
     * @param nome o nome do item de menu
     */
    public MenuItem(String nome) {
        this.nome = nome.trim();
    }

    /**
     * Retorna o nome do item de menu.
     *
     * @return o nome do item
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define um novo nome para o item de menu.
     *
     * @param nome o novo nome do item
     */
    public void setNome(String nome) {
        this.nome = nome.trim();
    }

    /**
     * Retorna a representação textual do item de menu.
     *
     * @return o nome do item
     */
    @Override
    public String toString() {
        return nome;
    }
}
