/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p02_calculadora;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author rubisco
 */
public class ProcesadorCalculadora {

    
    
    private void t(int i, String t) {
        
        System.out.println(" ## " + i + " " + t);
    }
    
    public ArrayList<String> procesar(String expr) {

        t(1, expr);
        
        expr = expr.replaceAll("\\s+", ""); //Elimina espacios en blanco
        expr = "(" + expr + ")";
        t(2, expr);
        String simbols = "+-*/()";
        String str = "";

        //Deja espacios entre operadores
        for (int i = 0; i < expr.length(); i++) {
            if (simbols.contains("" + expr.charAt(i))) {
                str += " " + expr.charAt(i) + " ";
                t(9, "hola");
            } else {
                str += expr.charAt(i);
            }
        }
        str = str.replaceAll("\\s+", " ").trim();

        t(3, str);
        
        String[] arrayInfix = str.split(" ");

        //Declaración de las pilas
        Stack< String> E = new Stack<>(); //Pila entrada
        Stack< String> P = new Stack<>(); //Pila temporal para operadores
        ArrayList< String> S = new ArrayList<>(); //Pila salida

        //Añadir la array a la Pila de entrada (E)
        for (int i = arrayInfix.length - 1; i >= 0; i--) {

            t(99, " > " + arrayInfix[i]);

            E.push(arrayInfix[i]);
        }

        try {
            //Algoritmo Infijo a Postfijo
            while (!E.isEmpty()) {
                switch (pref(E.peek())) {
                    case 1:
                        P.push(E.pop());
                        break;
                    case 3:
                    case 4:
                        while (pref(P.peek()) >= pref(E.peek())) {
                            S.add(P.pop());
                        }
                        P.push(E.pop());
                        break;
                    case 2:
                        while (!P.peek().equals("(")) {
                            S.add(P.pop());
                        }
                        P.pop();
                        E.pop();
                        break;
                    default:
                        S.add(E.pop());
                }
            }
            //Eliminación de impurezas en la expresión algebraica
            String infix = expr.replace(" ", "");
            String postfix = S.toString().replaceAll("[\\]\\[,]", "");

            //Mostrar resultados:
            System.out.println("Expresion Infija: " + infix);
            System.out.println("Expresion Postfija: " + postfix);

            return S;

        } catch (Exception ex) {
            System.out.println("Error en la expresión algebraica");
            System.err.println(ex);
        }

        return null;
    }

    public int calcular(ArrayList<String> s) {

        if (s == null) {
            System.out.println(" PILA NULA");
            return -1;
        } else {

            Stack<Integer> acumulador = new Stack<>();

            while (!s.isEmpty()) {

                if (esOperador(s.get(0))) {
                    
                    t(0, "pila vacia " + s.isEmpty() + " " + s.get(0));
                    t(0, "acum vacio " + acumulador.isEmpty() + " " + acumulador.peek());
                    
                    int op1 = acumulador.pop();
                    t(0, "pila vacia " + s.isEmpty() + " " + s.get(0)); 
                    t(0, "acum vacio " + acumulador.isEmpty() + " " + acumulador.peek());

                    int op2 = acumulador.pop();
                    int res = operar(op2, op1, s.remove(0));
                   
                    acumulador.push(res);

                } else {
                    acumulador.push(Integer.parseInt(s.remove(0)));
                }
            }

            return acumulador.pop();
        }
    }

    private boolean esOperador(String t) {

        if (t.equals("+")) {
            return true;
        }
        if (t.equals("-")) {
            return true;
        }
        if (t.equals("*")) {
            return true;
        }
        if (t.equals("/")) {
            return true;
        }
        return false;

    }
    
    private boolean esOperador(char c) {

        if (c == '+') {
            return true;
        }
        if (c == '-') {
            return true;
        }
        if (c == '*') {
            return true;
        }
        if (c == '/') {
            return true;
        }
        return false;

    }    

    private int operar(int op1, int op2, String operador) {

        if (operador.equals("+")) {
            return op1 + op2;
        }
        if (operador.equals("-")) {
            return op1 - op2;
        }
        if (operador.equals("*")) {
            return op1 * op2;
        }
        return Math.round(op1 / op2);
    }

    //Depurar expresión algebraica
    private static String depurar(String s) {
        s = s.replaceAll("\\s+", ""); //Elimina espacios en blanco
        s = "(" + s + ")";
        String simbols = "+-*/()";
        String str = "";

        //Deja espacios entre operadores
        for (int i = 0; i < s.length(); i++) {
            if (simbols.contains("" + s.charAt(i))) {
                str += " " + s.charAt(i) + " ";
            } else {
                str += s.charAt(i);
            }
        }
        return str.replaceAll("\\s+", " ").trim();
    }

    //Jerarquia de los operadores
    private static int pref(String op) {
        if (op.equals("*") || op.equals("/")) {
            return 4;
        }
        if (op.equals("+") || op.equals("-")) {
            return 3;
        }
        if (op.equals(")")) {
            return 2;
        }
        if (op.equals("(")) {
            return 1;
        }
        return 99;
    }

}
