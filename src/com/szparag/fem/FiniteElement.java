package com.szparag.fem;

/**
 * Created by Ciemek on 20/01/16.
 */
public class FiniteElement {
    private Node node1;
    private Node node2;

    public FiniteElement(Node node1, Node node2) {
        this.node1 = node1;
        this.node2 = node2;
    }

    public Node getNode1() {
        return node1;
    }

    public Node getNode2() {
        return node2;
    }
}
