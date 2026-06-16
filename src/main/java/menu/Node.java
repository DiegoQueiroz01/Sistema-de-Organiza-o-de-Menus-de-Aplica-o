package menu;

import java.util.ArrayList;
import java.util.List;

class Node<T> {
    private T data;
    private Node<T> parent;
    private List<Node<T>> children;

    public Node(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public void addChild(Node<T> child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void removeChild(Node<T> child) {
        this.children.remove(child);
        child.setParent(null);
    }

    public boolean isLeaf() {
        return this.children.isEmpty();
    }
}
