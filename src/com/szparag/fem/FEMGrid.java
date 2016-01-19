package com.szparag.fem;

/**
 * Created by Ciemek on 14/01/16.
 */
class FEMGrid {


    private int         numberOfElements;
    private float       radiusStart;
    private double      deltaRadius;
    private float       deltaTime;
    private float       k;
    private float       ro;
    private float       c;

    /**
     * LocalMatrix of given element
     */
    private double[][]   kElementMatrix;

    /**
     * parameters for Gaussian 2-point integral
     */
    private float       ksi1;
    private float       ksi2;
    private float       w1;
    private float       w2;

    /**
     *  shape functions
     *  for both points, where i is first and j is second
     */
    private float       Ni1;
    private float       Ni2;
    private float       Nj1;
    private float       Nj2;


    public FEMGrid(int noe, float radiusStart, double deltaRadius, float deltaTime,
                   float k, float ro, float c) {

        this.numberOfElements = noe;
        this.radiusStart = radiusStart;
        this.deltaRadius = deltaRadius;
        this.deltaTime = deltaTime;
        this.k = k;
        this.ro = ro;
        this.c = c;

        generateCalculationParameters();
    }

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



    public void instantiateLocalMatrix() {
        kElementMatrix = new double[2][2];

        for(double[] row : kElementMatrix)
            for (double element : row)
                element = 0;
    }


    public void calculateLocalMatrix() {

        /** for p=1 (point i) */
        double rp1 = Ni1 * radiusStart + Nj1 * (radiusStart + deltaRadius);

        /** for p=2 (point j) */
        double rp2 = Ni2 * radiusStart + Nj2 * (radiusStart + deltaRadius);

        /** calculating cells in local matrix for K-element (kElementMatrix) */
        kElementMatrix[0][0] = ((k / deltaRadius) * ((rp1*w1)+(rp2*w2)))
                + (((c * ro * deltaRadius)/deltaTime) * ((Ni1*Ni1*rp1* w1)+(Ni2*Ni2*rp2* w2)));



        printLocalMatrix();


    }



    public void instantiateGlobalMatrix() {
        // TODO
    }


    public void printLocalMatrix() {
        System.out.println("Local Matrix:");
        for(double[] row : kElementMatrix) {
            System.out.print("|");
            for (double element : row)
                System.out.print("[" + element + "]");
            System.out.println("|");
        }
        System.out.println();
    }

    public void printGlobalMatrix() {

    }

}
