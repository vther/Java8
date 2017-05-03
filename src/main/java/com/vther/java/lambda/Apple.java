package com.vther.java.lambda;


public class Apple {
    private long weight;
    private String color;

    public Apple() {

    }

    public Apple(long weight, String color) {
        this.weight = weight;
        this.color = color;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Apple{" + "weight=" + weight +
                ", color='" + color + '\'' +
                '}';
    }
}
