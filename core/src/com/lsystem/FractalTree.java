package com.lsystem;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.math.MathUtils.round;
import static com.badlogic.gdx.math.MathUtils.sinDeg;
import static com.badlogic.gdx.math.MathUtils.cosDeg;

import java.util.ArrayList;
import java.util.Stack;

//https://en.wikipedia.org/wiki/L-system
public class FractalTree {
    /* variables: 0, 1
       constants: [, ]
        axiom: 0
        rules:
            1 -> 11
            0 -> 1[0]0
     */
    ShapeRenderer sr;
    public String generate(String str){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if(c == '1'){
                sb.append("11");
            }
            else if(c == '0'){
                sb.append("1[0]0");
            }
            else if(c == '['){
                sb.append('[');
            }
            else if(c == ']'){
                sb.append(']');
            }
        }
        return sb.toString();
    }
    public String setup(int iterations){
        String tree = "0";
        sr = new ShapeRenderer();
        for(int i = 0; i < iterations; i++){
            tree = generate(tree);
        }
        return tree;
    }
    public void draw(String str, ShapeRenderer sr){
        Stack<ArrayList<Integer>> stack = new Stack<>();
        //start location
        int x = 300;
        int y = 50;
        int angle = 90;
        int lineLength = 50;
        sr.begin(ShapeRenderer.ShapeType.Line);
        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if(c == '1'){
                int x2 = (int) round(x + lineLength*cosDeg(angle));
                int y2 = (int) round((y + lineLength*sinDeg(angle)));
                sr.line(x, y, x2, y2);
                x = x2;
                y = y2;
            }
            else if(c == '0'){
                int x2 = (int) round(x + lineLength*cosDeg(angle));
                int y2 = (int) round((y + lineLength*sinDeg(angle)));
                sr.line(x, y, x2, y2);
                x = x2;
                y = y2;
            }
            else if(c == '['){
                ArrayList<Integer> posNAngle = new ArrayList<>();
                posNAngle.add(x);
                posNAngle.add(y);
                posNAngle.add(angle);
                stack.push(posNAngle);
                angle = angle + 45;
            }
            else if(c == ']'){
                ArrayList<Integer> posNAngle = stack.pop();
                x = posNAngle.get(0);
                y = posNAngle.get(1);
                angle = posNAngle.get(2);
                angle = angle - 45;
            }
        }
        sr.end();
    }
}
