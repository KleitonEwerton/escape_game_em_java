/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kleitonewerton.escapegame;

import javax.swing.JOptionPane;

/**
 *
 * @author KleitonEwerton
 * 
 * @brief                                   Classe usado para armazenar funções uteis para o projeto
 */
public class Utils {
    /**
    *@brief                                 Função que exibe uma mensagem de boas vindas ao usuário
    */
    public static void mensagemBemVindo(){
        
        JOptionPane.showMessageDialog(null, "BEM-VINDO JOGADOR AO ESCAPE-GAME", "BEM-VINDO",JOptionPane.DEFAULT_OPTION);
    }
    
    /**
    * @brief                                Função que lê um número do usuário 
    * @param mensagem                       Mensagem a ser exibida para o usuario
    * @return                               Número lido 
    */
    public static String leituraEntrada(String mensagem){
 
        String leitor =  JOptionPane.showInputDialog(mensagem, "(direção, quantidade)");
        
        return leitor;
    }
    
    /**
    * @brief                                Função que remove espaços, '(' e ')' de uma entrada e retorna um array formado por partes entre virgula
    * @param entrada                        String que será modificada e retornada
    * @return                               Array do entrada modificada, separado por ','
    */
    public static String[] entradaOrganizada(String entrada){
        /*ex: entrada ( b , 3 ) ex: saida[0]: b saida[1]: 3 */
        
        if(entrada.charAt(0) != '(' || entrada.charAt(entrada.length() - 1) != ')'){
            String[] saida = {"erro", "erro"};
            return saida;}
        //Substitui todos os caracteres não necessários para validar a entrada
        entrada = entrada.replaceAll("\\(","");
        entrada = entrada.replaceAll("\\)","");
        entrada = entrada.replaceAll(" ","");
        
        return entrada.split(",");
     
    }
    
    /**
    * @brief                            Função que lê um número do usuário 
    * @return                           Número lido 
    */
    public static int leituraNumero(String mensage){
           
            return Integer. parseInt(JOptionPane.showInputDialog(mensage, 30));

    }
    
    /**
    * @brief                            Função que pergunta o número ao usuário até que seja válido
    * @return                           Número lido
    */
    public static int perguntaNumero(String mensage){
        
        while(true)
            try{
                
                int numero = leituraNumero(mensage);
                if(numero > 50)numero = 50;
                return numero;
             
            }catch (Exception e){
                
               JOptionPane.showMessageDialog(null, "NÚMERO INVÁLIDO", "ERRO",JOptionPane.ERROR_MESSAGE);}
        
    }
    
    /**
    * @breif                            Método que pergunta a usuário se deseja jogar novamente
    * @param mensage                    Mensagem a ser exibido
    * @param title                      titulo da mensagem
    * @return                           retorna se deseja ou não jogar novamente
    */
    public static int perguntaJogarNovamente(String mensage,String title){
        
        return JOptionPane.showConfirmDialog(null,mensage,title,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        
    }
    
}
