package com.szparag.fem;

import java.util.LinkedList;

/**
 * Created by Ciemek on 20/01/16.
 */
public class FiniteElementsGrid {
    private LinkedList<FiniteElement> elements;
    

    public FiniteElementsGrid(LinkedList<FiniteElement> elements) {
        this.elements = elements;
    }

    public LinkedList<FiniteElement> getElements() {
        return elements;
    }
}
