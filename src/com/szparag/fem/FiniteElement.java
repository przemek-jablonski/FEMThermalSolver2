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
     * parameters for varying element material
     */
    private float       c;
    private float       k;
    private float       ro;

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


    public FiniteElement(Node node1, Node node2, float c, float k, float ro) {
        this.node1 = node1;
        this.node2 = node2;
        matrixesInstantiation();
        generateCalculationParameters();
        this.c = c;
        this.k = k;
        this.ro = ro;
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

    public void instantiateMatrix() {
        kLocalMatrix = new float[2][2];

        for (float[] row: kLocalMatrix)
            for (float element: row)
                element = 0;

    }

    public void instantiateVector() {
        fLocalVector = new float[2][1];

        for (float[] row: fLocalVector)
            for (float element: row)
                element = 0;

    }

    /**
     * calculation methods:
     */

    public void generateCalculationParameters() {
        ksi1 = -0.57735f;
        ksi2 =  0.57735f;

        w1 = 1;
        w2 = 1;

        Ni1 =  (float)(0.5 * (1-ksi1));
        Ni2 =  (float)(0.5 * (1-ksi2));

        Nj1 =  (float)(0.5 * (1+ksi1));
        Nj2 =  (float)(0.5 * (1+ksi2));
    }


    public void calculateLocalMatrix(float radiusStart, float deltaRadius, float deltaTime) {
       instantiateMatrix();
        /** for p=1 (point i) */
        rp1 = Ni1 * radiusStart + Nj1 * (radiusStart + deltaRadius);

        /** for p=2 (point j) */
        rp2 = Ni2 * radiusStart + Nj2 * (radiusStart + deltaRadius);

        /** calculating cells in local matrix for K-element (kLocalMatrix) */
        kLocalMatrix[0][0] = ((k/deltaRadius) * ((rp1*w1)+(rp2*w2)))
                + (((c*ro*deltaRadius)/deltaTime) * ((Ni1*Ni1*rp1*w1)+(Ni2*Ni2*rp2*w2)));

        kLocalMatrix[0][1] = (-(k/deltaRadius) * ((rp1*w1)+(rp2*w2)) )
                + ( (c*ro*deltaRadius/deltaTime) * ((Ni1*Nj1*rp1*w1)+(Ni2*Nj2*rp2*w2)));

        kLocalMatrix[1][0] = (-(k/deltaRadius) * ((rp1*w1)+(rp2*w2)) )
                + ( (c*ro*deltaRadius/deltaTime) * ((Ni1*Nj1*rp1*w1)+(Ni2*Nj2*rp2*w2)));

        kLocalMatrix[1][1] = ((k/deltaRadius) * ((rp1*w1)+(rp2*w2)))
                + ( (c*ro*deltaRadius/deltaTime) * ((Nj1*Nj1*rp1*w1)+(Nj2*Nj2*rp2*w2)));

    }


    public void calculateLocalVector(float radiusStart, float deltaRadius, float deltaTime) {
        instantiateVector();
        /** for p=1 (point i) */
        rp1 = Ni1 * radiusStart + Nj1 * (radiusStart + deltaRadius);

        /** for p=2 (point j) */
        rp2 = Ni2 * radiusStart + Nj2 * (radiusStart + deltaRadius);

        fLocalVector[0][0] = - (c*ro*deltaRadius/deltaTime) * (
                ((Ni1*node1.getTemperature()+Nj1*node2.getTemperature())*Ni1*rp1*w1) +
                        (Ni2*node1.getTemperature()+Nj2*node1.getTemperature())*Ni2*rp2*w2);

        fLocalVector[1][0] = - (c*ro*deltaRadius/deltaTime) * (
                ((Ni1*node1.getTemperature()+Nj1*node2.getTemperature())*Nj1*rp1*w1) +
                        (Ni2*node2.getTemperature()+Nj2*node2.getTemperature())*Nj2*rp2*w2);


//        fLocalVector[0][0] += (Ni1*node1.getTemperature()+Nj1*node2.getTemperature()) * c*ro*deltaRadius*rp1*w1*Ni1/deltaTime;
//        fLocalVector[0][0] += (Ni2*node1.getTemperature()+Nj2*node2.getTemperature()) * c*ro*deltaRadius*rp2*w1*Ni1/deltaTime;
//
//        fLocalVector[1][0] += (Ni1*node1.getTemperature()+Nj1*node2.getTemperature()) * c*ro*deltaRadius*rp1*w1*Nj1/deltaTime;
//        fLocalVector[1][0] += (Ni2*node1.getTemperature()+Nj2*node2.getTemperature()) * c*ro*deltaRadius*rp2*w1*Nj1/deltaTime;


        if(fLocalVector[0][0] < 0)
            fLocalVector[0][0] = Math.abs(fLocalVector[0][0]);

        if(fLocalVector[1][0] < 0)
            fLocalVector[1][0] = Math.abs(fLocalVector[1][0]);

    }

    public void addBoundaryConditionsMatrix(float alpha, float radiusMax) {
        kLocalMatrix[1][1] += 2 * alpha * radiusMax;
    }

    public void addBoundaryConditionsVector(float alpha, float radiusMax, float temperatureAir){
        fLocalVector[1][0] += 2*alpha*radiusMax*temperatureAir;
       // System.out.println("BOUNDARY CONDITION VECTOR VALUE: " + (2*alpha*radiusMax*temperatureAir));
    }


    /**
     * printing methods
     */

    public void printMatrix() { print(kLocalMatrix, "kLocalMatrix"); }

    public void printVector() { print(fLocalVector, "fLocalVector"); }



    private void print(float[][] array, String id) {
        System.out.println("print (" + id + "): ");
        for(float[] row : array) {
            System.out.print("|");
            for (float element : row)
                System.out.print("[" + element + "]");
            System.out.println("|");
        }
        System.out.println("endprint\n");
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


