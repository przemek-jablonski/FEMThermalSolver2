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
    private float[][]   fGlobalVector;
    private float[]     temperatures;


    public FiniteElementsGrid(LinkedList<FiniteElement> list, float radiusMax, float deltaRadius) {
        this.elements = list;
    }



    /**
     * calculation methods
     */

    public void calculateLocalMatrixes(float radiusStart, float deltaRadius, float k, float c, float ro,
                                       float deltaTime, float alpha) {
        float localRadiusStart = radiusStart;
        for (FiniteElement element : elements) {
            element.calculateLocalMatrix(localRadiusStart, deltaRadius, k, c, ro, deltaTime);
            if(elements.getLast() == element) element.addBoundaryConditionsMatrix(alpha, radiusMax);

           // element.printMatrix();
            localRadiusStart +=deltaRadius;
        }
    }


    public void calculateLocalVectors(float radiusStart, float deltaRadius, float c, float ro,
                                      float deltaTime, float temperatureStart, float alpha, float temperatureAir) {
        float localRadiusStart = radiusStart;
        for (FiniteElement element : elements) {
            element.calculateLocalVector(localRadiusStart, deltaRadius, c, ro, deltaTime, temperatureStart);
            if (elements.getLast() == element) element.addBoundaryConditionsVector(alpha, radiusMax, temperatureAir);

           // element.printVector();
            localRadiusStart += deltaRadius;
        }
    }


    public void generateGlobalMatrix() {
        instantiateGlobalMatrix();

        for (int e =0; e < elements.size(); ++e) {

            kGlobalMatrix[e][e] += elements.get(e).getkLocalMatrix()[0][0];
            kGlobalMatrix[e][e+1] += elements.get(e).getkLocalMatrix()[0][1];
            kGlobalMatrix[e+1][e] += elements.get(e).getkLocalMatrix()[1][0];
            kGlobalMatrix[e+1][e+1] += elements.get(e).getkLocalMatrix()[1][1];
        }

        printMatrix();
    }


    public void generateGlobalVector() {
        instantiateGlobalVector();

        for (int e =0; e < elements.size(); ++e) {
            fGlobalVector[e][0] += elements.get(e).getfLocalVector()[0][0];
            fGlobalVector[e+1][0] += elements.get(e).getfLocalVector()[1][0];
        }

       // printVector();

    }


    public void calculateTemperatures() {
        temperatures = new float[elements.size()+1];
        float[] tempValue = new float[elements.size()+1];
        int iterationCount = 100000;

        /**
         * calculation arrays instantiation
         */
        for (int i=0; i < elements.size()+1; ++i) {
            temperatures[i] = 0;
            tempValue[i] = 0;
        }


        /**
         * solving system of equations
         * Gauss-Riedel method
         */
        while(iterationCount > 0) {

            for (int i=0; i < elements.size()+1; ++i) {
                tempValue[i]=(fGlobalVector[i][0] / kGlobalMatrix[i][i]);
                for (int j=0; j < elements.size()+1; ++j) {
                    if (i == j) continue;
                    tempValue[i] = tempValue[i] - ((kGlobalMatrix[i][j] / kGlobalMatrix[i][i]) * temperatures[j]);
                    temperatures[i] = tempValue[i];
                }
            }

            --iterationCount;
        }

        printTemperatures(temperatures, "TEMPERATURES:");

    }





    private void instantiateGlobalMatrix() {
        kGlobalMatrix = new float[elements.size()+1][elements.size()+1];

        for (float[] row: kGlobalMatrix)
            for (float element: row)
                element = 0;
    }

    private void instantiateGlobalVector() {
        fGlobalVector = new float[elements.size()+1][1];

        for (float[] row: fGlobalVector)
            for (float element: row)
                element = 0;
    }


    /**
     * printing methods
     */

    public void printMatrix() { print(kGlobalMatrix, "GLOBAL MATRIX:"); }

    public void printVector() { print(fGlobalVector, "GLOBAL VECTOR:"); }

    private void printTemperatures(float[] array, String id) {
        System.out.println("\n" + id);
        System.out.print("|");

        for (float element : array)
            if(element == 0) System.out.print("[ " + "00.0000" + " ]");
            else System.out.print("[ " + element + " ]");

        System.out.println("|");
        System.out.println("endprint\n\n");
    }

    private void print(float[][] array, String id) {
        System.out.println("\n" + id);

        for(float[] row : array) {
            System.out.print("|");
            for (float element : row)
            if(element == 0) System.out.print("[ " + "00.0000" + " ]");
                else System.out.print("[ " + element + " ]");
            System.out.println("|");
        }

        System.out.println("endprint\n\n");
    }


    /**
     * accessors
     */

    public LinkedList<FiniteElement> getElements() {
        return elements;
    }

}
