/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kleitonewerton.escapegame;

/**
 *
 * @author KleitonEwerton
 * https://github.com/KleitonEwerton
 */
public class Campo {
    
    private char valor;         //Valor do campo
    private boolean isBomba;    //Se é uma bomba
    private boolean isCaminho;  //Se é parte do caminho que garanta a saída do jogador

    /**
    *@brief                     Construtor
    *@param valor               Valor char do campo
    */
    public Campo(char valor) {
        this.valor = valor;
        this.isBomba = false;
        this.isCaminho = false;
    }

    /**
    * @breif                    Método que retorna se o campo pertence ao caminho ou não
    * @return                   Se pertence ao caminho ou não
    */
    public boolean isIsCaminho() {
        return isCaminho;
    }

    /**
    * @breif                    Método que set se o campo será caminho ou não
    * @param isCaminho          Se é caminho ou não
    */
    public void setIsCaminho(boolean isCaminho) {
        this.isCaminho = isCaminho;
    }
    
    /**
    * @breif                    Método que retorna se o campo é uma bomba ou não
    * @return                   Se é uma bomba
    */
    public boolean isIsBomba() {
        return isBomba;
    }
    /**
    * @breif                    Método que set se o campo será uma bomba
    * @param isBomba            Se é uma bomba
    */
    public void setIsBomba(boolean isBomba) {
        this.isBomba = isBomba;
    }

    /**
    * @brief                    Método que retorna o valor do campo
    * @return                   Char do valor do campo
    */
    public char getValor() {
        return this.valor;
    }

    /**
    * @breif                    Método que altera o valor do campo
    * @param valor              Valor a ser utilizado
    */
    public void setValor(char valor) {
        this.valor = valor;
    }
    
    /**
    * @brief                    imprime o campo 
    */
    public void printCampo(){
        System.out.print("  |  " + getValor());
    }
    
}
