package com.szparag.fem;

import java.util.LinkedList;

/**
 * Created by Ciemek on 20/01/16.
 */
public class FiniteElementsGrid {

    /**
     * list of elements inside the grid
     */
    private LinkedList<FiniteElement> elements;

    /**
     *  globally gathered matrixes from elements
     */
    private float[][]   kGlobalMatrix;
    private float[][]   fLocalVector;

    private Main core;


    public FiniteElementsGrid(LinkedList<FiniteElement> nodes, Main main) {

        this.core = main;
    }

    public LinkedList<FiniteElement> getElements() {
        return elements;
    }
}
