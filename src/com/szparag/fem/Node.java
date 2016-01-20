package com.szparag.fem;

/**
 * Created by Ciemek on 20/01/16.
 */
public class Node {
    private float temperature;
    private float boundaryCondition1;

    public Node(float temperature, float boundaryCondition1) {
        this.temperature = temperature;
        this.boundaryCondition1 = boundaryCondition1;
    }

    public Node(float temperature) {
        this.temperature = temperature;
        this.boundaryCondition1 = -1;
    }

    public Node() {
        this.temperature = -1;
        this.boundaryCondition1 = -1;
    }


    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getBoundaryCondition1() {
        return boundaryCondition1;
    }

    public float getBoundaryCondition2() {
        return boundaryCondition2;
    }

    private float boundaryCondition2;
}
