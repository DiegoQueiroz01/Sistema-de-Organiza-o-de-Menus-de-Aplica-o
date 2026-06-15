package menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuManager {
    private final MenuItem root;

    public MenuManager(String rootName) {
        this.root = new MenuItem(rootName);
    }

    public MenuItem getRoot() {
        return root;
    }

    public MenuItem createMenuItem(String name) {
        return new MenuItem(name);
    }

    public boolean addSubmenu(MenuItem parent, String childName) {
        if (parent == null || childName == null || childName.isBlank()) {
            return false;
        }
        MenuItem child = new MenuItem(childName);
        parent.addChild(child);
        return true;
    }

    public Optional<MenuItem> findByName(String name) {
        List<MenuItem> items = root.search(name);
        return items.isEmpty() ? Optional.empty() : Optional.of(items.get(0));
    }

    public List<MenuItem> findAllByName(String name) {
        if (name == null || name.isBlank()) {
            return new ArrayList<>();
        }
        return root.search(name);
    }

    public boolean renameItem(MenuItem item, String newName) {
        if (item == null || newName == null || newName.isBlank()) {
            return false;
        }
        item.setName(newName);
        return true;
    }

    public boolean removeItem(MenuItem item) {
        if (item == null || item == root) {
            return false;
        }
        MenuItem parent = item.getParent();
        return parent != null && parent.removeChild(item);
    }

    public void printMenuStructure() {
        root.printTree("", true);
    }

    public List<MenuItem> listLeafItems() {
        return root.getLeafItems();
    }

    public int countMenus() {
        return root.countTotalItems();
    }

    public int countSubmenus() {
        return root.countTotalSubmenus();
    }

    public List<MenuItem> searchMenuItems(String name) {
        return findAllByName(name);
    }

    public void seedExampleStructure() {
        MenuItem cadastros = new MenuItem("Cadastros");
        MenuItem alunos = new MenuItem("Alunos");
        MenuItem novoAluno = new MenuItem("Novo Aluno");
        MenuItem consultarAluno = new MenuItem("Consultar Aluno");
        MenuItem professores = new MenuItem("Professores");
        MenuItem relatorios = new MenuItem("Relatórios");
        MenuItem frequencia = new MenuItem("Frequência");
        MenuItem desempenho = new MenuItem("Desempenho");
        MenuItem configuracoes = new MenuItem("Configurações");

        root.addChild(cadastros);
        cadastros.addChild(alunos);
        alunos.addChild(novoAluno);
        alunos.addChild(consultarAluno);
        cadastros.addChild(professores);
        root.addChild(relatorios);
        relatorios.addChild(frequencia);
        relatorios.addChild(desempenho);
        root.addChild(configuracoes);
    }
}
