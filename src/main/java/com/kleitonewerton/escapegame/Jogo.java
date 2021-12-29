/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kleitonewerton.escapegame;

import static com.kleitonewerton.escapegame.Utils.*;

/**
 *
 * @author KleitonEwerton
 */
public class Jogo {
    
    
    /**
    *@brief                                 função princiapal do programa 
    */
    public static void main(String[] args){
        
        mensagemBemVindo();
        
        Tabuleiro jogo  = new Tabuleiro();
        jogo.jogar();
        
    }
}
