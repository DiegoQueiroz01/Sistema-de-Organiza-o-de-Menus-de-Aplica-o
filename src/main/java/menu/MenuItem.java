package menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MenuItem {
    private String name;
    private MenuItem parent;
    private final List<MenuItem> children;

    public MenuItem(String name) {
        this.name = name.trim();
        this.children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public MenuItem getParent() {
        return parent;
    }

    public List<MenuItem> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public void addChild(MenuItem child) {
        if (child != null && !children.contains(child)) {
            child.parent = this;
            children.add(child);
        }
    }

    public boolean removeChild(MenuItem child) {
        if (child == null) {
            return false;
        }
        boolean removed = children.remove(child);
        if (removed) {
            child.parent = null;
        }
        return removed;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public String getFullPath() {
        List<String> path = new ArrayList<>();
        MenuItem current = this;
        while (current != null) {
            path.add(current.getName());
            current = current.parent;
        }
        Collections.reverse(path);
        return String.join(" > ", path);
    }

    public void printTree(String prefix, boolean isTail) {
        System.out.println(prefix + (prefix.isEmpty() ? "" : (isTail ? "└── " : "├── ")) + name);
        for (int i = 0; i < children.size(); i++) {
            children.get(i).printTree(prefix + (prefix.isEmpty() ? "" : (isTail ? "    " : "│   ")), i == children.size() - 1);
        }
    }

    public List<MenuItem> search(String name) {
        List<MenuItem> result = new ArrayList<>();
        if (this.name.equalsIgnoreCase(name)) {
            result.add(this);
        }
        for (MenuItem child : children) {
            result.addAll(child.search(name));
        }
        return result;
    }

    public int countTotalItems() {
        int count = 1;
        for (MenuItem child : children) {
            count += child.countTotalItems();
        }
        return count;
    }

    public int countTotalSubmenus() {
        int count = children.size();
        for (MenuItem child : children) {
            count += child.countTotalSubmenus();
        }
        return count;
    }

    public List<MenuItem> getLeafItems() {
        List<MenuItem> leaves = new ArrayList<>();
        if (isLeaf()) {
            leaves.add(this);
        } else {
            for (MenuItem child : children) {
                leaves.addAll(child.getLeafItems());
            }
        }
        return leaves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem)) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(name, menuItem.name) && Objects.equals(parent, menuItem.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parent);
    }
}
