package com.szparag.fem;

import sun.awt.image.ImageWatched;

import java.util.LinkedList;

class Main {
/*
    private static double      radiusMin = 0;         //meters
    private static double      radiusMax = 0.002;      //meters
    private static double      deltaRadius = 0.0002;

    private static float       alphaAir = 5;         //for natural convection: 5-12, for forced convection: 20-300
    private static float       temperatureAir = 150;  //oven temperature
    private static float       temperatureStart = 23; //body temperature

    private static float       timeMax = 200;       //seconds
    private static float       deltaTime = 50;      //seconds

    private static float       c = 1250;            //specific heat for PVC = 1000-1500
    private static float       k = 0.15f;           //thermal conductivity for PVC = 0.12-0.25 @23C
    private static float       ro = 1800;           //for PVC - 1000 up to 1800
*/

    private static double      radiusMin = 0;         //meters
    private static double      radiusMax = 0.08;      //meters
    private static double      deltaRadius = 0.01;

    private static float       alphaAir = 300;
    private static float       temperatureAir = 200;
    private static float       temperatureStart = 100;

    private static float       timeMax = 2000;       //seconds
    private static float       deltaTime = 50;      //seconds

    private static float       c = 700;
    private static float       k = 25;
    private static float       ro = 7800;



    public static void main(String[] args) {

        /**
         * creating nodes
         */
        LinkedList<Node> nodes = new LinkedList<Node>();
        for (float radius = (float)radiusMin; radius < radiusMax; radius += deltaRadius )
            nodes.add(new Node(temperatureStart));

        /**
         * creating elements, based on nodes
         * (here: 1d simplex 2-node elements list
         */
        LinkedList<FiniteElement> elements = new LinkedList<FiniteElement>();
        for (int i =0; i < nodes.size()-1; ++i)
            elements.add(new FiniteElement(nodes.get(i), nodes.get(i+1)));

        /**
         * populate Finite Element Grid with elements and nodes
         */
        FiniteElementsGrid grid = new FiniteElementsGrid(elements, nodes, (float)radiusMax, (float)deltaRadius);
        grid.printTemperatures("starting temps:");

//
//        System.out.println("TIMESTEPS:--------------------------------------------------------");
//        System.out.println("STARTING TIMESTEP:--------------------------------------------------------");
//        grid.calculateLocalMatrixes((float)radiusMin, (float)deltaRadius, (float)radiusMax, k, c, ro, deltaTime, alphaAir);
//        grid.calculateLocalVectors((float)radiusMin, (float)deltaRadius, (float)radiusMax, c, ro,
//                        deltaTime, alphaAir, temperatureAir);
//
//        grid.generateGlobalMatrix();
//        grid.generateGlobalVector();
//        grid.calculateTemperatures();


        /**
         * optimal timestep calculation
         */

        /*
        float deltaOfTime = (float)((deltaRadius * deltaRadius) / (0.5 * (k/(c*ro))));
        float timeSteps = (timeMax / deltaOfTime) +1;
        deltaOfTime = timeMax / timeSteps;
        */

        System.out.println("TIMESTEPS:--------------------------------------------------------");
       // for (int count = 1; count <300; ++count) {
        for (int time = (int)(deltaTime); time <= timeMax; time += deltaTime) {
            System.out.println("TIMESTEP " + time + " sec. -------------------------------------------------------------");
            grid.calculateLocalMatrixes((float)radiusMin, (float)deltaRadius, (float)radiusMax, k, c, ro, deltaTime, alphaAir);
            grid.calculateLocalVectors((float)radiusMin, (float)deltaRadius, (float)radiusMax, c, ro,
                    deltaTime, alphaAir, temperatureAir);
            grid.generateGlobalMatrix();
            grid.generateGlobalVector();
            grid.calculateTemperatures();

        }


    }


}














