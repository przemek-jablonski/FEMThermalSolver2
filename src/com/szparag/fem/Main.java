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
    private static float       numberOfElements = 3;
    private static float       numberOfNodes = 4;   // (noe+1)



    public static void main(String[] args) {

        System.out.println("input data fill:");

        FEMGrid FEMGrid = new FEMGrid((int)numberOfElements, (float)radiusMin, deltaRadius, deltaTime,
                                k, ro, c);


        FEMGrid.instantiateLocalMatrix();
        FEMGrid.printLocalMatrix();
        FEMGrid.calculateLocalMatrix();
        FEMGrid.printLocalMatrix();

    }


}
