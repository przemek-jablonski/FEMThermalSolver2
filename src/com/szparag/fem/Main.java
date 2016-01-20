package com.szparag.fem;

import sun.awt.image.ImageWatched;

import java.util.LinkedList;

class Main {

    private static double      radiusMin = 0;         //meters
    private static double      radiusMax = 0.01;      //meters
    private static double      deltaRadius = 0.002;

    private static float       alphaAir = 12;         //for natural convection: 5-12, for forced convection: 20-300
    private static float       temperatureAir = 150;  //oven temperature
    private static float       temperatureStart = 23; //body temperature

    private static float       timeMax = 1000;       //seconds
    private static float       deltaTime = 50;      //seconds

    private static float       cPVC = 1250;            //specific heat for PVC = 900-1500  // J/(kg*K);
    private static float       kPVC = 0.15f;           //thermal conductivity for PVC = 0.12-0.25 @23C  // W/mK
    private static float       roPVC = 1800;           //for PVC - 1000 up to 1800    // kg/m^3

    private static float       cPCB = 385;            //specific heat
    private static float       kPCB = 401;           //thermal conductivity
    private static float       roPCB = 8690;

    private static float       cAlu = 900;
    private static float       kAlu = 205;
    private static float       roAlu = 8700;


//    private static double      radiusMin = 0;         //meters
//    private static double      radiusMax = 0.08;      //meters
//    private static double      deltaRadius = 0.01;
//
//    private static float       alphaAir = 300;
//    private static float       temperatureAir = 200;
//    private static float       temperatureStart = 100;
//
//    private static float       timeMax = 2000;       //seconds
//    private static float       deltaTime = 50;      //seconds
//
//    private static float       c = 700;
//    private static float       k = 25;
//    private static float       ro = 7800;



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
//        for (int i =0; i < nodes.size()-1; ++i)
//            elements.add(new FiniteElement(nodes.get(i), nodes.get(i+1)));
//        elements.add(new FiniteElement(nodes.get(0), nodes.get(1), cPCB, kPCB, roPCB));     //this is core of object
//        elements.add(new FiniteElement(nodes.get(1), nodes.get(2), cPCB, kPCB, roPCB));
//        elements.add(new FiniteElement(nodes.get(2), nodes.get(3), cPVC, kPVC, roPVC));
//        elements.add(new FiniteElement(nodes.get(3), nodes.get(4), cPVC, kPVC, roPVC));
  //      elements.add(new FiniteElement(nodes.get(4), nodes.get(5), cPVC, kPVC, roPVC));
  //      elements.add(new FiniteElement(nodes.get(5), nodes.get(6), cPVC, kPVC, roPVC));
//        elements.add(new FiniteElement(nodes.get(6), nodes.get(7), cPVC, kPVC, roPVC));
//        elements.add(new FiniteElement(nodes.get(7), nodes.get(8), cPVC, kPVC, roPVC));
//        elements.add(new FiniteElement(nodes.get(8), nodes.get(9), cPVC, kPVC, roPVC));
//        elements.add(new FiniteElement(nodes.get(9), nodes.get(10), cPVC, kPVC, roPVC));    //this is the outside

        elements.add(new FiniteElement(nodes.get(0), nodes.get(1), cPCB, kPCB, roPCB));     //this is core of object
        elements.add(new FiniteElement(nodes.get(1), nodes.get(2), cAlu, kAlu, roAlu));
        elements.add(new FiniteElement(nodes.get(2), nodes.get(3), cPVC, kPVC, roPVC));
        elements.add(new FiniteElement(nodes.get(3), nodes.get(4), cPVC, kPVC, roPVC));
     //   elements.add(new FiniteElement(nodes.get(4), nodes.get(5), cPVC, kPVC, roPVC));

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
            grid.calculateLocalMatrixes((float)radiusMin, (float)deltaRadius, (float)radiusMax, deltaTime, alphaAir);
            grid.calculateLocalVectors((float)radiusMin, (float)deltaRadius, (float)radiusMax, deltaTime, alphaAir, temperatureAir);
            grid.generateGlobalMatrix();
            grid.generateGlobalVector();
            grid.calculateTemperatures();

        }


    }


}














