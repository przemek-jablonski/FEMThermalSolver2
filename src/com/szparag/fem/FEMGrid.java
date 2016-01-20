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
    private float       temperatureStart;
    private float       alpha;
    private double      radiusMax;
    private float       temperatureAir;

    /**
     * LocalMatrix of given element
     */
    private double[][]   kElementMatrix;
    private double[][]   fElementVector;

    /**
     * parameters for Gaussian 2-point integral
     */
    private float       ksi1;
    private float       ksi2;
    private float       w1;
    private float       w2;
    private double      rp1;
    private double      rp2;

    /**
     *  shape functions
     *  for both points, where i is first and j is second
     */
    private float       Ni1;
    private float       Ni2;
    private float       Nj1;
    private float       Nj2;


    public FEMGrid(int noe, float radiusStart, double deltaRadius, float deltaTime,
                   float k, float ro, float c, float temperatureStart, float alpha,
                   double radiusMax, float temperatureAir) {

        this.numberOfElements = noe;
        this.radiusStart = radiusStart;
        this.deltaRadius = deltaRadius;
        this.deltaTime = deltaTime;
        this.k = k;
        this.ro = ro;
        this.c = c;
        this.temperatureStart = temperatureStart;
        this.temperatureAir = temperatureAir;
        this.alpha = alpha;
        this.radiusMax = radiusMax;

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

    public void instantiateLocalVector() {
        fElementVector = new double[2][1];

        for(double[] row : fElementVector)
            for (double element : row)
                element = 0;
    }


    public void calculateLocalMatrix() {
        printArray(kElementMatrix);


        /** for p=1 (point i) */
        rp1 = Ni1 * radiusStart + Nj1 * (radiusStart + deltaRadius);

        /** for p=2 (point j) */
        rp2 = Ni2 * radiusStart + Nj2 * (radiusStart + deltaRadius);

        /** calculating cells in local matrix for K-element (kElementMatrix) */
        kElementMatrix[0][0] = ((k/deltaRadius) * ((rp1*w1)+(rp2*w2)))
                + (((c*ro*deltaRadius)/deltaTime) * ((Ni1*Ni1*rp1*w1)+(Ni2*Ni2*rp2*w2)));

        kElementMatrix[0][1] = (-(k/deltaRadius) * ((rp1*w1)+(rp2*w2)) )
                + ( (c*ro*deltaRadius/deltaTime) * ((Ni1*Nj1*rp1*w1)+(Ni2*Nj2*rp2*w2)));

        kElementMatrix[1][0] = (-(k/deltaRadius) * ((rp1*w1)+(rp2*w2)) )
                + ( (c*ro*deltaRadius/deltaTime) * ((Ni1*Nj1*rp1*w1)+(Ni2*Nj2*rp2*w2)));

        kElementMatrix[1][1] = ((k/deltaRadius) * ((rp1*w1)+(rp2*w2)))
                + ( (c*ro*deltaRadius/deltaTime) * ((Nj1*Nj1*rp1*w1)+(Nj2*Nj2*rp2*w2)));

        printArray(kElementMatrix);


    }

    public void calculateLocalVector() {
        printArray(fElementVector);

        fElementVector[0][0] = - (c*ro*deltaRadius/deltaTime) * (
                            ((Ni1*temperatureStart+Nj1*temperatureStart)*Ni1*rp1*w1) +
                            (Ni2*temperatureStart+Nj2*temperatureStart)*Ni2*rp2*w2);

        fElementVector[1][0] = - (c*ro*deltaRadius/deltaTime) * (
                            ((Ni1*temperatureStart+Nj1*temperatureStart)*Nj1*rp1*w1) +
                            (Ni2*temperatureStart+Nj2*temperatureStart)*Nj2*rp2*w2);


       // fElementVector[1][0] -= (2*alpha*radiusMax*temperatureAir);

        printArray(fElementVector);
    }



    public void instantiateGlobalMatrix() {
        // TODO
    }



    public void printArray(double[][] array) {
        System.out.println("Solutions:");
        for(double[] row : array) {
            System.out.print("|");
            for (double element : row)
                System.out.print("[" + element + "]");
            System.out.println("|");
        }
        System.out.println("i elo\n");
    }



   /*
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
*/

}
