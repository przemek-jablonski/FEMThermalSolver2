package com.szparag.fem;

import sun.awt.image.ImageWatched;

import java.util.LinkedList;

class Main {

    private static double      radiusMin = 0;
    private static double      radiusMax = 0.08;
    private static double      deltaRadius = 0.01;
    private static float       alphaAir = 300;
    private static float       temperatureStart = 100;
    private static float       temperatureAir = 200;
    private static float       timeMax = 200;
    private static float       deltaTime = 50;
    private static float       c = 700;
    private static float       k = 25;
    private static float       ro = 7800;



    public static void main(String[] args) {

        /**
         * creating nodes
         */
        LinkedList<Node> nodes = new LinkedList<Node>();
        for (float radius = (float)radiusMin; radius < radiusMax; radius += deltaRadius )
            nodes.add(new Node());

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
        FiniteElementsGrid grid = new FiniteElementsGrid(elements, (float)radiusMax, (float)deltaRadius);



        grid.calculateLocalMatrixes((float)radiusMin, (float)deltaRadius, k, c, ro, deltaTime, alphaAir);
        grid.calculateLocalVectors((float)radiusMin, (float)deltaRadius, c, ro,
                        deltaTime, temperatureStart, alphaAir, temperatureAir);

        grid.generateGlobalMatrix();
        grid.generateGlobalVector();
        grid.calculateTemperatures();


        float deltaOfTime = (float)((deltaRadius * deltaRadius) / (0.5 * (k/(c*ro))));
        float timeSteps = (timeMax / deltaOfTime) +1;
        deltaOfTime = timeMax / timeSteps;


        System.out.println("TIMESTEPS:-----------------");
        for (int count = 1; count <timeSteps; ++count) {
            System.out.println("TIMESTEP " + count + ". ------------------------------");
            grid.calculateLocalMatrixes((float)radiusMin, (float)deltaRadius, k, c, ro, deltaOfTime, alphaAir);
            grid.calculateLocalVectors((float)radiusMin, (float)deltaRadius, c, ro,
                    deltaOfTime, temperatureStart, alphaAir, temperatureAir);
            grid.generateGlobalMatrix();
            grid.generateGlobalVector();
            grid.calculateTemperatures();

        }


    }


}
