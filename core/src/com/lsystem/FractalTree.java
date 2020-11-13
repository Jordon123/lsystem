package com.lsystem;
//https://en.wikipedia.org/wiki/L-system
public class FractalTree {
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
    public void setup(){
        String axiom = "0";

    }
    public void draw(String str){
        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if(c == '1'){

            }
            else if(c == '0'){

            }
            else if(c == '['){

            }
            else if(c == ']'){

            }
        }
    }
}
