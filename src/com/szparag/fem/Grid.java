package com.szparag.fem;

/**
 * Created by Ciemek on 14/01/16.
 */
class Grid {

    private float[][]   localMatrix;
    private float[][]   globalMatrix;

    private int         numberOfElements;
    private float       radiusStart;
    private double      deltaRadius;
    private float       deltaTime;
    private float       k;
    private float       ro;
    private float       c;

    public Grid(int noe, float radiusStart, double deltaRadius, float deltaTime,
                float k, float ro, float c) {
        this.numberOfElements = noe;
        this.radiusStart = radiusStart;
        this.deltaRadius = deltaRadius;
        this.deltaTime = deltaTime;
        this.k = k;
        this.ro = ro;
        this.c = c;
    }

    public void instantiateLocalMatrix() {
        localMatrix = new float[2][2];

        for(float[] row : localMatrix)
            for (float element : row)
                element = 0;
    }

    public void instantiateGlobalMatrix() {
        // TODO
    }

    public void calculateLocalMatrix() {
        // na razie niech to bedzie element 0 (nodesy 0 i 1)

        int weight1 = 1;
        int weight2 = weight1;
        float ksi1 = -0.57735f;
        float ksi2 =  0.57735f;

        float Ni1 = 1 - ksi1;
        float Ni2 = 1 - ksi2;
        float Nj1 = 1 + ksi1;
        float Nj2 = 1 + ksi2;

        double rp = (ksi1 * radiusStart) * weight1 + (ksi2 * (radiusStart + deltaRadius)) * weight2;

        localMatrix[0][0] = (float)((k / deltaRadius) * rp
                + (c * ro * deltaRadius / deltaTime) *
                (Ni1 * rp + Ni2 * rp) * weight1 + (Ni1 * rp + Ni2 * rp) * weight2);
        /*
        localMatrix[0][0] = (float)((k / deltaRadius) * rp
                + (c * ro * deltaRadius / deltaTime) *
                (Ni1 * rp + Ni2 * rp) * weight1 + (Ni1 * rp + Ni2 * rp) * weight2);
*/


        printLocalMatrix();

    }

    public void printLocalMatrix() {
        System.out.println("Local Matrix:");
        for(float[] row : localMatrix) {
            System.out.print("|");
            for (float element : row)
                System.out.print("[" + element + "]");
            System.out.println("|");
        }
        System.out.println();
    }

    public void printGlobalMatrix() {

    }

}
