package com.szparag.fem;

class Main {

    private static double      radiusMin = 0;
    private static double      radiusMax = 0.08;
    private static double      deltaRadius = 0.01;
    private static float       alphaAir = 300;
    private static float       temperatureStart = 100;
    private static float       temperatureAir = 200;
    private static float       timeMax = 200;
    private static float       deltaTime = 50;
    private static float       c = 700;             //efektywne cieplo wlasciwe
    private static float       k = 25;              //wsp. przewodzenia ciepla (thermalConductivity)
    private static float       ro = 7800;           //gestosc materialu
    private static float       numberOfElements = 5;
    private static float       numberOfNodes = 6;   // (noe+1)



    public static void main(String[] args) {
        System.out.println("input data fill:");
        Grid grid = new Grid((int)numberOfElements, (float)radiusMin, deltaRadius, deltaTime,
                                k, ro, c);
        grid.instantiateLocalMatrix();
        grid.printLocalMatrix();
        grid.calculateLocalMatrix();
        grid.printLocalMatrix();
    }

    /*
    private static void inputDataFill(double rmin, double rmax, double deltar,
                               float aAir, float tStart, float tAir,
                               float timeMax, float deltaTime,
                               float c, float k, float ro, float noe, float non) {
        radiusMin = rmin;
        radiusMax = rmax;
        deltaRadius = deltar;
        alphaAir = aAir;
        temperatureStart = tStart;
        temperatureAir = tAir;
        this.timeMax = timeMax;
        this.deltaTime = deltaTime;
        this.c = c;
        this.k = k;
        this.ro = ro;
        this.numberOfElements = noe;
        this.numberOfNodes = non;

    }
    */

}
