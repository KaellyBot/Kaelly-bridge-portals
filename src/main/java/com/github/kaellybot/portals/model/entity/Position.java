package com.github.kaellybot.portals.model.entity;
import lombok.*;

@Data
@Builder
public class Position {

    private Integer x;
    private Integer y;

    public static Position of(int x, int y){
        return Position.builder().x(x).y(y).build();
    }

    public double getDistance(Position position){
        return Math.hypot(getX() - position.getX(), getY() - position.getY());
    }

    @Override
    public String toString(){
        return "[" + x + ", " + y + "]";
    }
}
