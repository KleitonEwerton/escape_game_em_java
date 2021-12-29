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
    
    /*Sobre o tabuleiro*/
    
    /**
    * @brief                Método para reiniciar o tabuleiro e todas as variáveis usadas 
    */
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
    
    /**
    * @brief                Método para iniciar um novo tabuleiro, 
    *                       adicionar pelo menos uma caminho sem as bombas, além das proprias bombas 
    */
    private void iniciaTabuleiro(){
        for(int i  = 0;i<linhas;i++){
            for(int j  = 0; j < colunas;j++)
                tabuleiro[i][j] = new Campo(' ');            
        }
        
        setValor(this.posY,this.posX, 'P');
        setCaminhoPosicao(this.posY,this.posX,true);
        sorteioBombas(perguntaNumero("DIGITE O NÚMERO DE BOMBAS A SER GERADAS, SERÁ GERADO UM MAXÍMO DE 50"));
    }
    
    /**
    * @brief                Imprime todos os campos do tabuleiro 
    */
    public void printTabuleiro(){
        System.out.println("\n\n  E     S     C     A     P     E           G     A     M     E\n");
        for(int i  = 0;i<linhas;i++){
            System.out.println("  |-----|-----|-----|-----|-----|-----|-----|-----|-----|-----|");
            for(int j  = 0; j < colunas;j++)
                
                if(isEncontrouSaida() || gameOver)
                    tabuleiro[i][j].printCampo();
            
                else
                    if(tabuleiro[i][j].isIsBomba())
                        System.out.print("  |   ");
                    else
                        tabuleiro[i][j].printCampo();
                    
            System.out.print("  |\n");
        } 
        System.out.println("  |-----|-----|-----|-----|-----|-----|-----|-----|-----|-----|\n\n");
    }
    
    /*Sobre os caminho valido*/
    
    /**
    * @brief                Método que seleciona um caminho(onde não poderá ter bombas) até uma chegada
    *                       que será definida de forma aleatória durante movimentoções para cima, baixo e esquerda
    *                       OBS: Para ver o caminho minimo, basta descomentar uma linha na função 'addCaminho', ela está comentada
    */
    private void caminhoDisponivel(){
        //Sorteio do caminho sem bombas até um local aleatorio
        char[] direcoes = {'D','E', 'B'};
        int qntMovimentos = 0;
        int interacoes = 0; //Para caso ocorra uma situação sem movimentações disponiveis
        //O maximo de movimentos aleatórios, para garantir que qualquer posição possa ser um caminho valido, exceto as proximas a origem
        int maxMovimentos = 5 + gerador.nextInt(21);
        //Laço para adicionar uma celula como parte do caminho
        while(qntMovimentos < maxMovimentos && interacoes < 1000){
            interacoes += 1;
            char direc = direcoes[gerador.nextInt(direcoes.length)];
            switch(direc){
                case 'D': 
                    if(addCaminho(1, 0))
                        qntMovimentos += 1;break;
                        
                case 'E':
                    if(addCaminho(-1, 0))
                        qntMovimentos += 1;break;
                        
                case 'B':
                    if(addCaminho(0, 1))
                        qntMovimentos += 1;break;
            }
        }
        //Set a saida
        setValor(this.yChegada,this.xChegada, 'S');
        setCaminhoPosicao(yChegada,xChegada, true);
    }
    
    /**
    * @brief                Método que adiciona um novo caminho, caso for válido
    */
    private boolean addCaminho(int x,int y){
        if(!isForaTabuleiro(this.xChegada,this.yChegada,x, y) && !isCaminhoPosicao(this.yChegada + y,this.xChegada + x)){
            this.xChegada += x;
            this.yChegada += y;
            setCaminhoPosicao(this.yChegada,this.xChegada, true);
            //Caso queria ver o caminho basta descomentar a linha abaixo
            //setValor(this.yChegada, this.xChegada, 'C');
            return true;
        }  
        return false;
    }
    
    /*Sobre as bombas*/
    
    /**
    * @brief                Método que sorteia as bombas, não adicionando bombas em um campo caminho 
    */
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
    
    /*Sobre o jogo*/
    
    /**
    * @breif                Método publico para jogar o jogo, permitindo comandar o personagem e jogar novamente 
    */
    public void jogar(){
        while(true){
            int auxPosX = getPosX();
            int auxPosY = getPosY();
            if(isEncontrouSaida())
                if(finalizarJogo("PARABÉNS, VOCÊ VENCEU!!!\nDESEJA JOGAR NOVAMENTE?"))break;
            if(!isEncontrouSaida() && this.gameOver)
                if(finalizarJogo("GAME OVER, VOCÊ PERDEU!!!\nDESEJA JOGAR NOVAMENTE?"))break;
            try{
                String entrada = leituraEntrada("JOGADOR, DIGITE SUA MOVIMENTAÇÃO EX: (B, 5) - direções(d, e, c, b)");
                direcaoMovimento(entradaOrganizada(entrada)[0].toUpperCase().charAt(0), Integer.parseInt(entradaOrganizada(entrada)[1]));
                if(auxPosX!= getPosX() || auxPosY != getPosY())listMovimentacoes.add(entrada);
                printTabuleiro();
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, "ENTRADA INVÁLIDA!", "ERRO",JOptionPane.ERROR_MESSAGE);}
        }  
    }
    
    /**
    * @brief                    Método que imprime as jogadas do jogador e pergunta se deseja jogar novamente
    * @return                   se o jogo será recomeçado
    */
    private boolean finalizarJogo(String mensage){
        
        
        System.out.println("SUAS MOVIMENTAÇÕES: ");
        listMovimentacoes.forEach(item -> {
            System.out.println(item + " ");
        });

        if(perguntaJogarNovamente(mensage,"GAME OVER") == 0)reiniciarTabuleiro();
        else return true;
        
        return false;
        
    }
    
    /*Sobre o jogador*/
    
    /**
    * @breif                Método move o personagem, caso for válido
    * @param x              posição de x
    * @param y              posição em y
    */
    private void moverPersonagem(int x, int y){
        if(isForaTabuleiro(getPosX(),getPosY(), x, y)){
            JOptionPane.showMessageDialog(null, "NÚMERO DE MOVIMENTAÇÕES INVÁLIDO, VOCÊ NÃO PODE SAIR DO TABULEIRO\nPOR FAVOR JOGUE NOVAMENTE", "ERRO",JOptionPane.ERROR_MESSAGE);return;}
        if(validaMovimentos(getPosX(), getPosY(),x,y))modificarPosicao(x,y); 
    }
    
    /**
    * @breif                Método modifica a posição do personagem, caso for válido
    * @param x              posição de x
    * @param y              posição em y
    */
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
    
    /**
    * @breif                Método modifica a posição do personagem, caso for válido
    * @param posicaoEmX     posição de x
    * @param posicaoEmY     posição em y
    * @param x              movimento em x
    * @param y              movimento em y
    */
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
    
    
    /**
    * @brief                Método que recebe a direção e quantidade de movimento e o faz
    * @param direcao        Direção dita pelo usuário
    * @param quantidade     Quantidade de casas a ser movidos
    */
    public void direcaoMovimento(char direcao, int quantidade){
        if(quantidade <=0){
            JOptionPane.showMessageDialog(null, "MOVIMENTAÇÃO INVÁLIDA", "ERRO",JOptionPane.ERROR_MESSAGE);return;}
        
        //e - esquerda, d - direita, c - cima e b - baixo
        switch(direcao){
            case 'D': moverPersonagem(quantidade, 0);break;
            case 'E': moverPersonagem(-quantidade, 0);break;
            case 'C': moverPersonagem(0,-quantidade);break;
            case 'B': moverPersonagem(0,quantidade);break;
            default :JOptionPane.showMessageDialog(null, "DIREÇÃO INVÁLIDA", "ERRO",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*SETS*/
    
    /**
    * @breif                Método que modifica o valor em uma linhas e coluna
    * @param x              Linha a ser mudada
    * @param y              Coluna a ser mudade
    */
    private void setValor(int x, int y, char valor){
       this.tabuleiro[x][y].setValor(valor);
       if(valor == 'B')
           setBombaPosicao(x,y,true);
       else
           setBombaPosicao(x,y,false);
    }
    
    /**
    * @breif                Método que modifica se é caminho ou não em uma linhas e coluna
    * @param x              Linha a ser mudada
    * @param y              Coluna a ser mudade
    * @param isCaminho      Se é um caminho
    */
    private void setCaminhoPosicao(int x, int y, boolean isCaminho){
        this.tabuleiro[x][y].setIsCaminho(isCaminho);
    }
    
    /**
    * @breif                Método que modifica se é uma bomba ou não em uma linhas e coluna
    * @param x              Linha a ser mudada
    * @param y              Coluna a ser mudade
    * @param isBomba        Se é uma bomba
    */
    private void setBombaPosicao(int x, int y, boolean isBomba){
        this.tabuleiro[x][y].setIsBomba(isBomba);
    }
    
    /**
    * @breif                Método que modifica o valor da Posião em x
    * @param posX           Linha a ser mudada
    */
    private void setPosX(int posX) {
        this.posX = posX;
    }

    /**
    * @breif                Método que modifica o valor da Posião em y
    * @param posX           Coluna a ser mudada
    */
    private void setPosY(int posY) {
        this.posY = posY;
    }
    
    
    /*GETS*/
    
    /**
    * @breif                Método que retorna o x da chegada
    * @return               a posição x da chegada
    */
    public int getxChegada() {
        return xChegada;
    }
    
    /**
    * @breif                Método que retorna o y da chegada
    * @return               a posição y da chegada
    */
    public int getyChegada() {
        return yChegada;
    }

    /**
    * @breif                Método que retorna o x d do personagem
    * @return               a posição x do personagem
    */
    public int getPosX() {
        return posX;
    }
    
    /**
    * @breif                Método que retorna o y do personagem
    * @return               a posição y da chegada
    */
    public int getPosY() {
        return posY;
    }
    
    /**
    * @breif                Método que retorna se uma posicao sai do tabuleiro
    * @param posicaoEmX     Posicao atual em x
    * @param x              Adicional em x
    * @param posicaoEmY     Posicao atual em y
    * @param y              Adicional em y
    * @return               Se sai do tabuleiro ou não
    */
    private static boolean isForaTabuleiro(int posicaoEmX, int posicaoEmY, int x, int y){
        return (posicaoEmX + x > 9 || posicaoEmX + x < 0 || posicaoEmY + y > 9 || posicaoEmY + y < 0);
    }
    
    /**
    * @breif                Método que retorna se uma posicao é parte do caminho ou não
    * @param x              Posicao atual em x
    * @param y              Posicao atual em y
    * @return               Se é um caminho minimo ou não
    */
    private boolean isCaminhoPosicao(int x, int y){
        return this.tabuleiro[x][y].isIsCaminho();
    }
    
    /**
    * @breif                Método que retorna se encontrou a saída ou não
    * @return               Se encontrou a saída ou não
    */
    public boolean isEncontrouSaida() {
        return encontrouSaida;
    }
    
    /**
    * @breif                Método que retorna se é bomba ou não
    * @param x              x linha atual
    * @param y              y coluna atual
    * @return               Se é o campo é uma bomba ou não
    */
    private boolean isBombaPosicao(int x, int y){
       return this.tabuleiro[x][y].isIsBomba();
    }
    
    /**
    * @breif                Método que retorna se é um movimento é valido ou não
    * @param posicaoEmX     x linha atual
    * @param posicaoEmY     y coluna atual
    * @return               Se o movimento será valído ou não
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
