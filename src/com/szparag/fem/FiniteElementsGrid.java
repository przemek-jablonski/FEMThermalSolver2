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

    /**
     *  globally gathered matrixes from elements
     */
    private float[][]   kGlobalMatrix;
    private float[][]   fLocalVector;

    private Main core;


    public FiniteElementsGrid(LinkedList<FiniteElement> list) {
        this.elements = list;
    }


    public void calculateLocalMatrixes(float radiusStart, float deltaRadius, float k, float c, float ro, float deltaTime) {
        float localRadiusStart = radiusStart;
        for (FiniteElement element : elements) {
            element.calculateLocalMatrix(localRadiusStart, deltaRadius, k, c, ro, deltaTime);
            localRadiusStart +=deltaRadius;
        }
/*
        for (int i=0; i < elements.size(); ++i) {
            elements.get(i).calculateLocalMatrix(localRadiusStart, deltaRadius, k, c, ro, deltaTime);
            localRadiusStart +=deltaRadius;
        }
        */
    }


    public void calculateLocalVectors(float radiusStart, float deltaRadius, float c, float ro, float deltaTime, float temperatureStart) {
        float localRadiusStart = radiusStart;
        for (FiniteElement element : elements) {
            element.calculateLocalVector(localRadiusStart, deltaRadius, c, ro, deltaTime, temperatureStart);
            localRadiusStart += deltaRadius;
        }
    }



    public LinkedList<FiniteElement> getElements() {
        return elements;
    }

}
