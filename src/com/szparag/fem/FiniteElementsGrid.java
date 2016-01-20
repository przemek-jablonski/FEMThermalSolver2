package com.szparag.fem;

import java.util.LinkedList;

/**
 * Created by Ciemek on 20/01/16.
 */
public class FiniteElementsGrid {

    /**
     * list of elements inside the grid
     */
    private LinkedList<FiniteElement> elements;


    private float       radiusMax;
    private float       deltaRadius;

    /**
     *  globally gathered matrixes from elements
     */
    private float[][]   kGlobalMatrix;
    private float[][]   fLocalVector;


    public FiniteElementsGrid(LinkedList<FiniteElement> list, float radiusMax, float deltaRadius) {
        this.elements = list;
    }


    public void calculateLocalMatrixes(float radiusStart, float deltaRadius, float k, float c, float ro,
                                       float deltaTime, float alpha) {
        float localRadiusStart = radiusStart;
        for (FiniteElement element : elements) {
            element.calculateLocalMatrix(localRadiusStart, deltaRadius, k, c, ro, deltaTime);
            if(elements.getLast() == element) element.addBoundaryConditionsMatrix(alpha, radiusMax);

            element.printMatrix();
            localRadiusStart +=deltaRadius;
        }
    }


    public void calculateLocalVectors(float radiusStart, float deltaRadius, float c, float ro,
                                      float deltaTime, float temperatureStart, float alpha, float temperatureAir) {
        float localRadiusStart = radiusStart;
        for (FiniteElement element : elements) {
            element.calculateLocalVector(localRadiusStart, deltaRadius, c, ro, deltaTime, temperatureStart);
            if (elements.getLast() == element) element.addBoundaryConditionsVector(alpha, radiusMax, temperatureAir);

            element.printVector();
            localRadiusStart += deltaRadius;
        }
    }

//    public void generateGlobal



    public LinkedList<FiniteElement> getElements() {
        return elements;
    }

}
