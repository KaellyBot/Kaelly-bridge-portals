package com.github.kaellybot.portals.model.entity;
import lombok.*;

@Data
@Builder
public class Position {

    private int x;
    private int y;

    public static Position of(int x, int y){
        return Position.builder().x(x).y(y).build();
    }

    @Override
    public String toString(){
        return "[" + x + ", " + y + "]";
    }
}
