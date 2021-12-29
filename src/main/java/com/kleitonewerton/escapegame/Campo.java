/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kleitonewerton.escapegame;

/**
 *
 * @author KleitonEwerton
 */
public class Campo {
    
    private char valor;
    private boolean isBomba;
    private boolean isCaminho;

    
    public Campo(char valor) {
        this.valor = valor;
        this.isBomba = false;
        this.isCaminho = false;
    }

    public boolean isIsCaminho() {
        return isCaminho;
    }

    public void setIsCaminho(boolean isCaminho) {
        this.isCaminho = isCaminho;
    }

    public boolean isIsBomba() {
        return isBomba;
    }

    public void setIsBomba(boolean isBomba) {
        this.isBomba = isBomba;
    }

    public char getValor() {
        return valor;
    }

    public void setValor(char valor) {
        this.valor = valor;
    }
    
    public void printCampo(){
        System.out.print("  |  " + getValor());
    }
    
}
