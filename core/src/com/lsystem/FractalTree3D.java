package com.lsystem;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.math.MathUtils.round;
import static com.badlogic.gdx.math.MathUtils.sinDeg;
import static com.badlogic.gdx.math.MathUtils.cosDeg;

import java.util.ArrayList;
import java.util.Stack;

//https://en.wikipedia.org/wiki/L-system
public class FractalTree3D {
    /* variables: 0, 1
       constants: [, ]
        axiom: 0
        rules:
            1 -> 11
            0 -> 1[0]0
     */
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
        for(int i = 0; i < iterations; i++){
            tree = generate(tree);
        }
        return tree;
    }
    public ModelInstance draw(String str, MeshBuilder meshBuilder, ModelBuilder modelBuilder){
        Stack<ArrayList<Integer>> stack = new Stack<>();
        //start location
        int x = 0;
        int y = 0;
        int angle = 90;
        int lineLength = 1;
        meshBuilder.begin(VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, GL20.GL_LINES);
        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if(c == '1'){
                int x2 = round(x + lineLength*cosDeg(angle));
                int y2 = round(y + lineLength*sinDeg(angle));
                meshBuilder.line(x, y, 0, x2, y2,0);
                x = x2;
                y = y2;
            }
            else if(c == '0'){
                int x2 = round(x + lineLength*cosDeg(angle));
                int y2 = round(y + lineLength*sinDeg(angle));
                meshBuilder.line(x, y, 0, x2, y2,0);
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
        Mesh m = meshBuilder.end();
        modelBuilder.begin();
        modelBuilder.part("box", m, GL20.GL_LINES, new Material(ColorAttribute.createDiffuse(Color.GREEN)));
        return new ModelInstance(modelBuilder.end());
    }
}

