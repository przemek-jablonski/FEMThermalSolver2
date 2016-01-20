package com.szparag.fem;

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
    private static float       c = 700;             //efektywne cieplo wlasciwe
    private static float       k = 25;              //wsp. przewodzenia ciepla (thermalConductivity)
    private static float       ro = 7800;           //gestosc materialu
    private static float       numberOfElements = 3;
    private static float       numberOfNodes = 4;   // (noe+1)



    public static void main(String[] args) {

        System.out.println("input data fill:");

        FEMGrid FEMGrid = new FEMGrid((int)numberOfElements, (float)radiusMin, deltaRadius, deltaTime,
                                k, ro, c, temperatureStart,
                                alphaAir, radiusMax, temperatureAir);

/**
        FEMGrid.instantiateLocalMatrix();
        FEMGrid.instantiateLocalVector();
        FEMGrid.calculateLocalMatrix();
        FEMGrid.calculateLocalVector();
*/

        /**                 */

        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        Node node4 = new Node();

        FiniteElement element1 = new FiniteElement(node1, node2);
        FiniteElement element2 = new FiniteElement(node2, node3);
        FiniteElement element3 = new FiniteElement(node3, node4);

        LinkedList<FiniteElement> list = new LinkedList<FiniteElement>();
        list.add(element1);
        list.add(element2);
        list.add(element3);

        FiniteElementsGrid grid = new FiniteElementsGrid(list);
        grid.calculateLocalMatrixes((float)radiusMin, (float)deltaRadius, k, c, ro, deltaTime);
        grid.calculateLocalVectors((float)radiusMin, (float)deltaRadius, c, ro, deltaTime, temperatureStart);



    }


}
