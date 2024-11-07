package ru.artemiyandstepan.model;


import java.io.Serializable;

public class Coordinates implements Serializable {
    private long x;
    private Long y;

    public Coordinates(){}
    public Coordinates(long x, Long y){
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }
}
