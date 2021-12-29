/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kleitonewerton.escapegame;

import static com.kleitonewerton.escapegame.Utils.entradaOrganizada;
import static com.kleitonewerton.escapegame.Utils.leituraEntrada;
import static com.kleitonewerton.escapegame.Utils.perguntaJogarNovamente;
import static com.kleitonewerton.escapegame.Utils.perguntaNumero;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;


/**
 *
 * @author KleitonEwerton
 */
public class Tabuleiro {
    
    static public int linhas = 10;
    static public int colunas = 10;
    static Random gerador = new Random();
    
    private final Campo[][] tabuleiro;
    private int qntBombas = 0;
    
    private int posX;
    private int posY;
    private int xChegada;
    private int yChegada;
    
    private boolean encontrouSaida;
    private boolean gameOver;
    ArrayList<String> listMovimentacoes = new ArrayList<>();
    
    public Tabuleiro() {
        
        tabuleiro = new Campo[linhas][colunas];
        reiniciarTabuleiro();
   
    }
    
    private void reiniciarTabuleiro(){
        
        listMovimentacoes.clear();
        this.posX = 0;
        this.posY = 0;
        this.xChegada = 0;
        this.yChegada = 0;
        this.encontrouSaida = false;
        this.gameOver = false;
        this.qntBombas = 0;
        iniciaTabuleiro();
        JOptionPane.showMessageDialog(null, "INICIANDO UM NOVO TABULEIRO", "INICIANDO",JOptionPane.DEFAULT_OPTION);
        printTabuleiro();      
    }

    public void jogar(){
       
        while(true){
            
            
            if(isEncontrouSaida())
                if(finalizarJogo("GAME OVER - PARABÉNS, VOCÊ VENCEU!!!\nDESEJA JOGAR NOVAMENTE?","GAME OVER"))return;
            
            if(!isEncontrouSaida() && this.gameOver)
                if(finalizarJogo("GAME OVER - VOCÊ PERDEU!!!\nDESEJA JOGAR NOVAMENTE?","GAME OVER"))return;
            
            try{
                String entrada = leituraEntrada("JOGADOR, DIGITE SUA MOVIMENTAÇÃO EX: (B, 5) - direções (d, e, c, b)");
                direcaoMovimento(entradaOrganizada(entrada)[0].toUpperCase().charAt(0), Integer.parseInt(entradaOrganizada(entrada)[1]));
                listMovimentacoes.add(entrada);}
            
            catch (Exception e){
                JOptionPane.showMessageDialog(null, "ENTRADA INVÁLIDA!", "ERRO",JOptionPane.ERROR_MESSAGE);}
            printTabuleiro();
            
        }  
    }
    
    private boolean finalizarJogo(String mensage, String title){
        
        printTabuleiro();
        System.out.println("SUAS MOVIMENTAÇÕES: ");
        for(String item: listMovimentacoes)
            System.out.println(item + " ");

        if(perguntaJogarNovamente(mensage,"GAME OVER") == 0)reiniciarTabuleiro();
        else return true;
        
        return false;
        
    }
    
    private void iniciaTabuleiro(){
        for(int i  = 0;i<linhas;i++){
            for(int j  = 0; j < colunas;j++)
                tabuleiro[i][j] = new Campo(' ');            
        }
        
        setValor(this.posY,this.posX, 'P');
        setCaminhoPosicao(this.posY,this.posX,true);
        sorteioBombas(perguntaNumero("DIGITE O NÚMERO DE BOMBAS A SER GERADAS, SERÁ GERADO UM MAXÍMO DE 50"));
    }
    
    private void moverPersonagem(int x, int y){
        
        if(isForaTabuleiro(getPosX(),getPosY(), x, y)){
            JOptionPane.showMessageDialog(null, "NÚMERO DE MOVIMENTAÇÕES INVÁLIDO, VOCÊ NÃO PODE SAIR DO TABULEIRO", "ERRO",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(validaMovimentos(getPosX(), getPosY(),x,y))modificarPosicao(x,y);
         
    }
    
    private void modificarPosicao(int x, int y){
        
        setValor(this.posY,this.posX,' ');
        
        if(this.encontrouSaida){
            setPosX(this.xChegada);
            setPosY(this.yChegada);
            setValor(this.yChegada,this.xChegada,'P');
        }else{
            setPosX(this.posX + x);
            setPosY(this.posY + y);
            setValor(this.posY,this.posX,'P');
        }
         
    }
    
    private boolean validaMovimentos(int posicaoEmX, int posicaoEmY, int x, int y){ 
        
        if(x > 0)
            for(int i = 0;i < x;i++)
                if(!isMovimentoValido(posicaoEmY,posicaoEmX + i + 1))
                    return false;
                
        if(y > 0)
            for(int i = 0;i < y;i++)
                if(!isMovimentoValido(posicaoEmY + i + 1,posicaoEmX))
                    return false;      
           
        if(x < 0)
            for(int i = 0;i < -x;i++)
                if(!isMovimentoValido(posicaoEmY,posicaoEmX - i - 1))
                    return false;
        
        if(y < 0)
            for(int i = 0;i < -y;i++)
                if(!isMovimentoValido(posicaoEmY - i - 1,posicaoEmX))
                    return false;
        
        return true;  
    }

    public void direcaoMovimento(char direcao, int quantidade){
     
        switch(direcao){
            case 'D' -> moverPersonagem(quantidade, 0);
            case 'L' -> moverPersonagem(-quantidade, 0);
            case 'U' -> moverPersonagem(0,-quantidade);
            case 'B' -> moverPersonagem(0,quantidade);
            default -> JOptionPane.showMessageDialog(null, "DIREÇÃO INVÁLIDA", "ERRO",JOptionPane.ERROR_MESSAGE);
            
        }
    }
    
    private void sorteioBombas(int qBombas){
        caminhoDisponivel();
        while(this.qntBombas < qBombas){
            
            int x = gerador.nextInt(linhas);
            int y = gerador.nextInt(colunas);
            
            if(!isCaminhoPosicao(x, y)){
                setValor(x,y, 'B');
                this.qntBombas += 1;
            }  
        }
    }
    private void caminhoDisponivel(){
        
        //Sorteio do caminho sem bombas até um local aleatorio
        char[] direcoes = {'D','E', 'B'};
        
        int qntMovimentos = 0;
        int interacoes = 0;
        int maxMovimentos = 5 + gerador.nextInt(21);
        
        while(qntMovimentos < maxMovimentos && interacoes < 1000){
            interacoes += 1;
            char direc = direcoes[gerador.nextInt(direcoes.length)];
   
            switch(direc){
                case 'D' -> { 
                    if(addCaminho(1, 0))
                        qntMovimentos += 1;
                }
                case 'E' -> {
                    if(addCaminho(-1, 0))
                        qntMovimentos += 1;
                }
                case 'B' -> {
                    if(addCaminho(0, 1))
                        qntMovimentos += 1;
                }
            }
        }
        
        setValor(this.yChegada,this.xChegada, 'S');
        setCaminhoPosicao(yChegada,xChegada, true);
    }
    
    private boolean addCaminho(int x,int y){
        if(!isForaTabuleiro(this.xChegada,this.yChegada,x, y) && !isCaminhoPosicao(this.yChegada + y,this.xChegada + x)){
            this.xChegada += x;
            this.yChegada += y;
            setCaminhoPosicao(this.yChegada,this.xChegada, true);
            return true;
        }  
        return false;
    }
    
    public void printTabuleiro(){
        System.out.println("\n\n  E     S     C     A     P     E           G     A     M     E\n");
        for(int i  = 0;i<linhas;i++){
            System.out.println("  |-----|-----|-----|-----|-----|-----|-----|-----|-----|-----|");
            for(int j  = 0; j < colunas;j++)
                
                if(isEncontrouSaida() || gameOver)
                    tabuleiro[i][j].printCampo();
            
                else
                    if(tabuleiro[i][j].isIsBomba())
                        System.out.print("  |  ");
                    else
                        tabuleiro[i][j].printCampo();
                    
            System.out.print("  |\n");
        } 
        System.out.println("  |-----|-----|-----|-----|-----|-----|-----|-----|-----|-----|\n\n");
    }
    
   
    
    /*SETS*/
    
    private void setValor(int linha, int coluna, char valor){
       this.tabuleiro[linha][coluna].setValor(valor);
       if(valor == 'B')
           setBombaPosicao(linha,coluna,true);
       else
           setBombaPosicao(linha,coluna,false);
    }
    
    private void setCaminhoPosicao(int x, int y, boolean isCaminho){
        this.tabuleiro[x][y].setIsCaminho(isCaminho);
    }
    
    private void setBombaPosicao(int x, int y, boolean isBomba){
        this.tabuleiro[x][y].setIsBomba(isBomba);
    }
    private void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    private void setPosY(int posY) {
        this.posY = posY;
    }
    
    
    /*GETS*/
    public int getxChegada() {
        return xChegada;
    }

    public int getyChegada() {
        return yChegada;
    }

    public int getPosX() {
        return posX;
    }
    
    public static boolean isForaTabuleiro(int posicaoEmX, int posicaoEmY, int x, int y){
        return (posicaoEmX + x > 9 || posicaoEmX + x < 0 || posicaoEmY + y > 9 || posicaoEmY + y < 0);
    }
    
    private boolean isCaminhoPosicao(int x, int y){
        return this.tabuleiro[x][y].isIsCaminho();
    }
    
    public boolean isEncontrouSaida() {
        return encontrouSaida;
    }
    private boolean isBombaPosicao(int x, int y){
       return this.tabuleiro[x][y].isIsBomba();
    }
    
    /**
     
    */
    private boolean isMovimentoValido(int posicaoEmX, int posicaoEmY){
        if(isBombaPosicao(posicaoEmX,posicaoEmY)){
            this.gameOver = true;
            return false;
        }
        if(posicaoEmX == getyChegada() && posicaoEmY == getxChegada())encontrouSaida = true;
          
        return true;

    }

    
}
