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
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector3;

import static com.badlogic.gdx.math.MathUtils.sinDeg;
import static com.badlogic.gdx.math.MathUtils.cosDeg;

import java.util.ArrayList;
import java.util.Stack;

//https://en.wikipedia.org/wiki/L-system
public class FractalTree3D {
    /* variables: A, B
       constants: [, ] , / , /, F, "
        axiom: 0
        rules:
            A -> [B] //// [B] //// [B]
            B -> &FFFA
     */
    public String generate(String str){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if(c == 'A'){
                sb.append("\"[&FFFA]////[&FFFA]////[&FFFA]");
            }
            else{
                sb.append(c);
            }
        }
        return sb.toString();
    }
    public String setup(int iterations){
        String tree = "A";
        for(int i = 0; i < iterations; i++){
            tree = generate(tree);
        }
        return tree;
    }
    public void rotateX(float angle, Vector3 points){

    }
    public ModelInstance draw(String str, MeshBuilder meshBuilder, ModelBuilder modelBuilder){
        Stack<ArrayList<Float>> stack = new Stack<>();
        //start location
        float x = 0;
        float y = 0;
        float z = 0;
        Vector3 point = new Vector3();
        Vector3 point2 = new Vector3();
        //float angle = 90;
        float pitch = 0;
        float roll = 90;
        float lineLength = 2;
        float ratio = (float) 0.5;
        meshBuilder.begin(VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, GL20.GL_LINES);
        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if(c == 'F'){
                float x2 = x + lineLength*(cosDeg(roll));
                float y2 = y + lineLength*(sinDeg(pitch));
                float z2 = z + lineLength*(-sinDeg(roll)+cosDeg(pitch));
                meshBuilder.line(x, y, z, x2, y2, z2);
                x = x2;
                y = y2;
                z = z2;
            }
            else if(c == '"'){
                lineLength = 1 * ratio;
            }
            else if(c == '/'){
                roll = roll + 15;
            }
            else if (c == '&'){
                pitch = pitch + 45;
            }
            else if(c == '['){
                ArrayList<Float> posNAngle = new ArrayList<>();
                posNAngle.add(x);
                posNAngle.add(y);
                posNAngle.add(z);
                posNAngle.add(roll);
                posNAngle.add(lineLength);
                posNAngle.add(pitch);
                stack.push(posNAngle);
            }
            else if(c == ']'){
                ArrayList<Float> posNAngle = stack.pop();
                x = posNAngle.get(0);
                y = posNAngle.get(1);
                z = posNAngle.get(2);
                roll = posNAngle.get(3);
                lineLength = posNAngle.get(4);
                pitch = posNAngle.get(5);
            }
        }
        Mesh m = meshBuilder.end();
        modelBuilder.begin();
        modelBuilder.part("box", m, GL20.GL_LINES, new Material(ColorAttribute.createDiffuse(Color.GREEN)));
        return new ModelInstance(modelBuilder.end());
    }
}

