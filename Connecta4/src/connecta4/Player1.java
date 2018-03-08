/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connecta4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Usuari
 */
public class Player1 {

    private Tauler meutaulell;
    private int maxProfunditat;

    public class Move {
        private int []pos;

        Move(int x, int y){
            this.pos = new int[]{x,y};
        }
        Move(){
            this.pos = new int[]{0,0};
        }

        public int getX(){
            return this.pos[0];
        }

        public int getY(){
            return this.pos[1];
        }
    }

    Player1(Tauler entrada){
        meutaulell = entrada;
        maxProfunditat = 8;
    }

    public int[] tirada(){
        int [][]taulerAux = copiaTauler(meutaulell.getTaulell());
        int millorValor = Integer.MIN_VALUE;
        int [] millorMov = new int[]{0,1};
        LinkedList<int[]> movimentsPossibles = generarMovimentsPossibles(taulerAux);
        while(!movimentsPossibles.isEmpty()){
            int [] mov = movimentsPossibles.poll();
            taulerAux[mov[0]][mov[1]] = 1;
            int valor = miniMax(taulerAux, 1);
            if (valor > millorValor){
                millorMov = mov;
                millorValor = valor;
            }
        }
        return millorMov;
    }

    private int miniMax(int [][] taulerModificat, int profunditat){

        if(profunditat == this.maxProfunditat){
            return heuristic(taulerModificat, profunditat%2+1);
        }

        int millor;
        if (profunditat % 2 == 0) {  //Max

            millor = movimentMax(taulerModificat, ++profunditat);

        } else { //Min

            millor = movimentMinim(taulerModificat, ++profunditat);
        }

        if (millor < 0)
            return heuristic(taulerModificat, profunditat);
        return millor;
    }

    private int movimentMinim(int[][] taulerModificat, int profunditat){

        int[] millorMoviment = new int[]{0,0};
        int millorHeuristic = Integer.MAX_VALUE;

        LinkedList<int[]> movimentsPossibles = generarMovimentsPossibles(taulerModificat);

        if (movimentsPossibles.isEmpty())
            return -1;

        int[] movimentPossible;
        while(!movimentsPossibles.isEmpty()){
            movimentPossible = movimentsPossibles.poll();
            if (taulerModificat[movimentPossible[0]][movimentPossible[1]] == 0) {
                int [][]taulerAux = copiaTauler(taulerModificat);
                taulerAux[movimentPossible[0]][movimentPossible[1]] = profunditat%2+1;
                int valorHeuristic = miniMax(taulerAux, profunditat);

                if (valorHeuristic < millorHeuristic) {
                    millorHeuristic = valorHeuristic;
                    millorMoviment = movimentPossible;
                }
            }
        }
        taulerModificat[millorMoviment[0]][millorMoviment[1]] = profunditat%2+1;
        return millorHeuristic;
    }

    private int movimentMax(int [][] taulerModificat, int profunditat){
        int[] millorMoviment = new int[]{0,0};
        int millorHeuristic = Integer.MIN_VALUE;

        LinkedList<int[]> movimentsPossibles = generarMovimentsPossibles(taulerModificat);

        if (movimentsPossibles.isEmpty())
            return -1;

        int[] movimentPossible;
        while(!movimentsPossibles.isEmpty()){
            movimentPossible = movimentsPossibles.poll();
            if (taulerModificat[movimentPossible[0]][movimentPossible[1]] == 0) {

                int [][]taulerAux = copiaTauler(taulerModificat);
                taulerAux[movimentPossible[0]][movimentPossible[1]] = profunditat%2+1;
                int valorHeuristic = miniMax(taulerAux, profunditat);

                if (valorHeuristic > millorHeuristic) {
                    millorHeuristic = valorHeuristic;
                    millorMoviment = movimentPossible;
                }
            }
        }
        taulerModificat[millorMoviment[0]][millorMoviment[1]] = profunditat%2+1;
        return millorHeuristic;
    }


//    private int alfaBeta(Tauler taulerModificat, int profunditat, int alfa, int beta){
//
//        if(profunditat == this.maxProfunditat){
//            return heuristic(taulerModificat);
//        }
//
//        if (profunditat % 2 == 0) {  //Max
//            return movimentMaxAlfaBeta(taulerModificat, ++profunditat, alfa, beta);
//        } else { //Min
//            return movimentMinimAlfaBeta(taulerModificat, ++profunditat, alfa, beta);
//        }
//    }

//    private int movimentMaxAlfaBeta(Tauler taulerModificat, int profunditat, int alfa, int beta){
//
//        Move millorMoviment = null, movimentPossible;
//
//        Queue<Move> movimentsPossibles = generarMovimentsPossibles(taulerModificat);
//
//        if (movimentsPossibles.isEmpty())
//            return heuristic(taulerModificat);
//
//        while(!movimentsPossibles.isEmpty() && alfa < beta){
//
//            movimentPossible = movimentsPossibles.poll();
//            if (taulerModificat.getpos(movimentPossible.getX(), movimentPossible.getY()) == 0) {
//
//                int [][]taulerAux = copiaTauler(taulerModificat);
//                taulerAux[movimentPossible.getX()][movimentPossible.getY()] = profunditat%2+1;
//
//                int valorHeuristic = alfaBeta(taulerAux, profunditat, alfa, beta);
//
//                if (valorHeuristic > alfa) {
//                    alfa = valorHeuristic;
//                    millorMoviment = movimentPossible;
//                }
//            }
//
//        }
//        if (millorMoviment != null)
//            taulerModificat.setpos(
//                millorMoviment.getX(),
//                millorMoviment.getY()
//            );
//        return alfa;
//    }
//
//    private int movimentMinimAlfaBeta(Tauler taulerModificat, int profunditat, int alfa, int beta){
//
//        Move millorMoviment = null, movimentPossible;
//
//        LinkedList<Move> movimentsPossibles = generarMovimentsPossibles(taulerModificat);
//
//        if (movimentsPossibles.isEmpty())
//            return heuristic(taulerModificat);
//
//        while(!movimentsPossibles.isEmpty() && alfa < beta){
//
//            movimentPossible = movimentsPossibles.poll();
//            if (taulerModificat.getpos(movimentPossible.getX(), movimentPossible.getY()) == 0) {
//
//                Tauler taulerAux = new Tauler(taulerModificat);
//                taulerAux.setTorn(profunditat % 2 + 1);
//                taulerAux.setpos(
//                    movimentPossible.getX(),
//                    movimentPossible.getY()
//                );
//
//                int valorHeuristic = alfaBeta(taulerAux, profunditat, alfa, beta);
//
//                if (valorHeuristic < beta) {
//                    beta = valorHeuristic;
//                    millorMoviment = movimentPossible;
//                }
//
//            }
//
//        }
//        if (millorMoviment != null)
//            taulerModificat.setpos(
//                millorMoviment.getX(),
//                millorMoviment.getY()
//            );
//        return beta;
//    }

    public LinkedList<int[]> generarMovimentsPossibles(int[][] tauler){
        int tableWidth = tauler.length;

        LinkedList<int[]> moviments = new LinkedList<int[]>();
        ArrayList<Integer> columnesAleatories = new ArrayList<Integer>();

        for (int i = 0; i < tableWidth; i++)
            columnesAleatories.add(i);

        Collections.shuffle(columnesAleatories);
        for (int col: columnesAleatories){
            moviments.add(new int[]{col, primeraFilaBuida(tauler, col)});
        }
        return moviments;
    }

    public int primeraFilaBuida(int[][] tauler, int col){
        int row = 0;
        while (row < tauler.length && tauler[col][row] != 0)
            row++;
        return row;
    }


    public int heuristic(int[][] tauler, int nivell){
        int valor = 0;

        for(int x = 0; x < tauler.length; x++){
            for(int y = 0; y < tauler[x].length; y++){
                if (tauler[x][y] != 0) {
                    valor += valorarPos(tauler, x, y, nivell);
                }
            }
        }
        return valor;
    }

    private int valorarPos(int [][] tauler, int col, int row, int tornActual){
        if (tauler[col][row] == 0)
            return 0;
        return valorarJugador(tauler, col, row, tornActual) - valorarJugador(tauler, col, row, tornActual%2+1);
    }

    private int valorarJugador(int[][] tauler, int col, int row, int jugador){
        int x, y;
        int valorTotal = 0,
                valorComprovacio;

        /* Vertical inferior*/
        x = col; y = row; valorComprovacio = 0;
        while (y > 0 && tauler[x][y--] == jugador)
            valorComprovacio++;
        if (tauler[x][y--] == 0)
            while (y > 0 && tauler[x][y--] == jugador)
                valorComprovacio++;
        valorTotal+=valorarComprovacio(valorComprovacio);

        /* Vertical superior */
        x = col; y = row; valorComprovacio = 0;
        while (y < tauler[x].length-1 && tauler[x][y++] == jugador)
            valorComprovacio++;
        if (tauler[x][y++] == 0)
            while (y < tauler[x].length-1 && tauler[x][y++] == jugador)
                valorComprovacio++;
        valorTotal+=valorarComprovacio(valorComprovacio);

//        /* Diagonal inferior esquerre */
//        x = col; y = row; valorComprovacio = 0;
//        while (--y > 0 && --x > 0 && tauler[x][y] == jugador)
//            valorComprovacio++;
//        valorTotal+=valorarComprovacio(valorComprovacio);

//        /* Diagonal superior esquerre */
//        x = col; y = row; valorComprovacio = 0;
//        while (++y < tauler[x].length-1 && --x > 0 && tauler[x][y] == jugador)
//            valorComprovacio++;
//        valorTotal+=valorarComprovacio(valorComprovacio);

        /* Diagonal inferior dreta */
        x = col; y = row; valorComprovacio = 0;
        while (y > 0 && x < tauler.length-1 && tauler[++x][y--] == jugador)
            valorComprovacio++;
        if (tauler[x++][y] == 0)
            while (y > 0 && x < tauler.length-1 && tauler[x++][y--] == jugador)
                valorComprovacio++;
        valorTotal+=valorarComprovacio(valorComprovacio);

        /* Diagonal superior dreta */
        x = col; y = row; valorComprovacio = 0;
        while (y < tauler[x].length-1 && x < tauler.length-1 && tauler[x++][y++] == jugador)
            valorComprovacio++;
        if (tauler[x++][y++] == 0)
            while (x < tauler.length-1 && y < tauler[x].length-1 && tauler[x++][y++] == jugador)
                valorComprovacio++;
        valorTotal+=valorarComprovacio(valorComprovacio);

//        /* Horitzontal esquerre */
//        x = col; y = row; valorComprovacio = 0;
//        while (--x > 0 && tauler[x][y] == jugador)
//            valorComprovacio++;
//        valorTotal+=valorarComprovacio(valorComprovacio);

        /* Horitzontal dreta */
        x = col; y = row;
        while (x < tauler.length-1 && tauler[x++][y] == jugador)
            valorComprovacio++;
        if (tauler[x++][y] == 0)
            while (x < tauler.length-1 && tauler[x++][y] == jugador)
                valorComprovacio++;
        valorTotal+=valorarComprovacio(valorComprovacio);
        return valorTotal;
    }

    private int valorarComprovacio(int valorComprovacio){
        switch (valorComprovacio){
            case 3:
                return valorComprovacio*100;
            case 4:
                return valorComprovacio*1000;
            default:
                return valorComprovacio;
        }
    }

    private int[][] copiaTauler(int [][] tauler){
        int [][]copia = new int[tauler.length][tauler[0].length];
        for (int i = 0; i < tauler.length; i++){
            for (int j = 0; j < tauler[i].length; j++){
                copia[i][j] = tauler[i][j];
            }
        }
        return copia;
    }
}
