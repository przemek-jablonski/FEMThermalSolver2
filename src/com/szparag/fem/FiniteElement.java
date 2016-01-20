package com.szparag.fem;

/**
 * Created by Ciemek on 20/01/16.
 */
public class FiniteElement {
    /**
     * nodes belonging to this element
     */
    private Node node1;
    private Node node2;

    /**
     * local matrix and local vector of particular element
     */
    private float[][]   kLocalMatrix;
    private float[][]   fLocalVector;

    /**
     * parameters for Gaussian 2-point integral
     */
    private float   ksi1;
    private float   ksi2;
    private float   w1;
    private float   w2;
    private float   rp1;
    private float   rp2;

    /**
     * shape functions for both nodes
     * where (i) is first one, and (j) is second
     */
    private float   Ni1, Ni2;
    private float   Nj1, Nj2;


    public FiniteElement(Node node1, Node node2) {
        this.node1 = node1;
        this.node2 = node2;
        matrixesInstantiation();
    }


    private void matrixesInstantiation() {
        kLocalMatrix = new float[2][2];
        fLocalVector = new float[2][1];

        for (float[] row: kLocalMatrix)
            for (float element: row)
                element = 0;

        for (float[] row: fLocalVector)
            for (float element: row)
                element = 0;

    }

    /**
     * calculation methods:
     */

    private void generateCalculationParameters() {
        ksi1 = -0.57735f;
        ksi2 =  0.57735f;

        w1 = 1;
        w2 = 1;


        Ni1 =  (float)(0.5 * (1-ksi1));
        Ni2 =  (float)(0.5 * (1-ksi2));

        Nj1 =  (float)(0.5 * (1+ksi1));
        Nj2 =  (float)(0.5 * (1+ksi2));
    }


    /**
     * accessors
     */
    public Node getNode1() {
        return node1;
    }

    public Node getNode2() {
        return node2;
    }

    public float[][] getfLocalVector() {
        return fLocalVector;
    }

    public float[][] getkLocalMatrix() {
        return kLocalMatrix;
    }
}


