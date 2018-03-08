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
    private char algorisme;

    Player1(Tauler entrada){
        meutaulell = entrada;
        maxProfunditat = 10;
        algorisme = 'a';
    }

    public int[] tirada(){

        /*Per començar, generem els moviments possibles actuals i ens quedem amb el més prometedor
         * Suposare això com a un nivell de recursitivat ja, per això, les crides a alfa-beta i minimax, les fem
         * amb profunditat 1.*/

        int [][]taulerOriginal = copiaTauler(meutaulell.getTaulell());

        int valorMillorTirada = Integer.MIN_VALUE;

        int [] millorMoviment = new int[]{0,0};

        LinkedList<int[]> movimentsPossibles = generarMovimentsPossibles(taulerOriginal);

        while(!movimentsPossibles.isEmpty()){

            int[] movimentActual = movimentsPossibles.poll();
            int valorMovimentActual = Integer.MIN_VALUE;

            taulerOriginal[movimentActual[0]][movimentActual[1]] = 1;
            if (this.algorisme == 'm')
                valorMovimentActual = miniMax(taulerOriginal, 1);
            else if (this.algorisme == 'a')
                valorMovimentActual = alfaBeta(taulerOriginal, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (valorMovimentActual > valorMillorTirada){
                millorMoviment = movimentActual;
                valorMillorTirada = valorMovimentActual;
            }

        }
        return millorMoviment;
    }

    /* ======================= CODI MINIMAX =============================*/

    private int miniMax(int [][] taulerModificat, int profunditat){

        if(profunditat == this.maxProfunditat){
            return heuristic(taulerModificat, profunditat);
        }

        if (profunditat % 2 == 0) {  //Max

            return movimentMax(taulerModificat, ++profunditat);

        } else { //Min

            return movimentMinim(taulerModificat, ++profunditat);
        }

    }

    private int movimentMinim(int[][] taulerModificat, int profunditat){

        int[] millorMoviment = new int[]{0,0};
        int millorHeuristic = Integer.MAX_VALUE;

        int jugadorActual = profunditat % 2;
        LinkedList<int[]> movimentsPossibles = generarMovimentsPossibles(taulerModificat);

        if (movimentsPossibles.isEmpty())
            return heuristic(taulerModificat, profunditat);

        int[] movimentPossible;
        while(!movimentsPossibles.isEmpty()){
            movimentPossible = movimentsPossibles.poll();
            if (taulerModificat[movimentPossible[0]][movimentPossible[1]] == 0) {
                int [][]taulerAux = copiaTauler(taulerModificat);
                taulerAux[movimentPossible[0]][movimentPossible[1]] = jugadorActual;
                int valorHeuristic = miniMax(taulerAux, profunditat);

                if (valorHeuristic < millorHeuristic) {
                    millorHeuristic = valorHeuristic;
                    millorMoviment = movimentPossible;
                }
            }
        }
        taulerModificat[millorMoviment[0]][millorMoviment[1]] = jugadorActual;
        return millorHeuristic;
    }

    private int movimentMax(int [][] taulerModificat, int profunditat){
        int[] millorMoviment = new int[]{0,0};
        int millorHeuristic = Integer.MIN_VALUE;

        LinkedList<int[]> movimentsPossibles = generarMovimentsPossibles(taulerModificat);

        if (movimentsPossibles.isEmpty())
            return heuristic(taulerModificat, profunditat);

        int[] movimentPossible;
        int jugadorActual = profunditat % 2;
        while(!movimentsPossibles.isEmpty()){
            movimentPossible = movimentsPossibles.poll();
            if (taulerModificat[movimentPossible[0]][movimentPossible[1]] == 0) {

                int [][]taulerAux = copiaTauler(taulerModificat);
                taulerAux[movimentPossible[0]][movimentPossible[1]] = jugadorActual;
                int valorHeuristic = miniMax(taulerAux, profunditat);

                if (valorHeuristic > millorHeuristic) {
                    millorHeuristic = valorHeuristic;
                    millorMoviment = movimentPossible;
                }
            }
        }
        taulerModificat[millorMoviment[0]][millorMoviment[1]] = jugadorActual;
        return millorHeuristic;
    }

    /* ====================================================================*/

    /* ======================= CODI ALFA-BETA =============================*/

    private int alfaBeta(int[][] taulerModificat, int profunditat, int alfa, int beta){

        if(profunditat == this.maxProfunditat){
            return heuristic(taulerModificat, profunditat);
        }

        if (profunditat % 2 == 0) {  //Max
            return movimentMaxAlfaBeta(taulerModificat, ++profunditat, alfa, beta);
        } else { //Min
            return movimentMinimAlfaBeta(taulerModificat, ++profunditat, alfa, beta);
        }
    }

    private int movimentMaxAlfaBeta(int[][] taulerModificat, int profunditat, int alfa, int beta){

        int[] millorMoviment = new int[]{0,0};
        int[] movimentPossible;

        LinkedList<int[]> movimentsPossibles = generarMovimentsPossibles(taulerModificat);

        int jugadorActual = profunditat % 2;
        if (movimentsPossibles.isEmpty())
            return heuristic(taulerModificat, profunditat);

        while(!movimentsPossibles.isEmpty() && alfa < beta){

            movimentPossible = movimentsPossibles.poll();
            if (taulerModificat[movimentPossible[0]][movimentPossible[1]] == 0) {

                int [][]taulerAux = copiaTauler(taulerModificat);
                taulerAux[movimentPossible[0]][movimentPossible[1]] = jugadorActual;

                int valorHeuristic = alfaBeta(taulerAux, profunditat, alfa, beta);

                if (valorHeuristic > alfa) {
                    alfa = valorHeuristic;
                    millorMoviment = movimentPossible;
                }
            }

        }
        taulerModificat[millorMoviment[0]][millorMoviment[1]] = jugadorActual;
        return alfa;
    }

    private int movimentMinimAlfaBeta(int[][] taulerModificat, int profunditat, int alfa, int beta){

        int[] millorMoviment = new int[]{0,0};
        int[] movimentPossible;

        LinkedList<int[]> movimentsPossibles = generarMovimentsPossibles(taulerModificat);

        int jugadorActual = profunditat % 2;
        if (movimentsPossibles.isEmpty())
            return heuristic(taulerModificat, profunditat);

        while(!movimentsPossibles.isEmpty() && alfa < beta){

            movimentPossible = movimentsPossibles.poll();
            if (taulerModificat[movimentPossible[0]][movimentPossible[1]] == 0) {

                int [][]taulerAux = copiaTauler(taulerModificat);
                taulerAux[movimentPossible[0]][movimentPossible[1]] = jugadorActual;

                int valorHeuristic = alfaBeta(taulerAux, profunditat, alfa, beta);

                if (valorHeuristic < beta) {
                    beta = valorHeuristic;
                    millorMoviment = movimentPossible;
                }

            }

        }
        taulerModificat[millorMoviment[0]][millorMoviment[1]] = jugadorActual;
        return beta;
    }

    /* ====================================================================*/

    /* ========================= CODI COMÚ ===============================*/

    public LinkedList<int[]> generarMovimentsPossibles(int[][] tauler){

        ArrayList<Integer> columnesAleatories = new ArrayList<>();
        for (int i = 0; i < tauler.length; i++)
            columnesAleatories.add(i);
        Collections.shuffle(columnesAleatories);

        LinkedList<int[]> moviments = new LinkedList<>();
        columnesAleatories.forEach((columna)->{
            moviments.add(new int[]{columna, primeraFilaBuida(tauler, columna)});
        });
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
        int jugadorActual = nivell % 2;
        for(int x = 0; x < tauler.length; x++){
            for(int y = 0; y < tauler[x].length; y++){
                if (tauler[x][y] != 0) {
                    valor += valorarPos(tauler, x, y, jugadorActual);
                }
            }
        }
        return valor;
    }

    private int valorarPos(int [][] tauler, int col, int row, int tornActual){
        if (tauler[col][row] == 0)
            return 0;
        return valorarJugador(tauler, col, row, tornActual);
    }

    private int valorarJugador(int[][] tauler, int col, int row, int jugador){
        int x, y;
        int valorTotal = 0, valorComprovacio;

        // Ometem tots els recorreguts a l'esquerre ja que ja les haurem comprovat prèviament per el disseny del
        // recorregut del mètode heuristic().

        /* Vertical inferior*/
        x = col; y = row; valorComprovacio = 0;
        while (dinsElTauler(tauler, x, y) && tauler[x][y--] == jugador)
            valorComprovacio++;
        if (dinsElTauler(tauler, x, y) && tauler[x][y--] == 0)
            while (dinsElTauler(tauler, x, y) && tauler[x][y--] == jugador)
                valorComprovacio++;
        valorTotal+=valorarComprovacio(valorComprovacio);

        /* Vertical superior */
        x = col; y = row; valorComprovacio = 0;
        while (dinsElTauler(tauler, x, y) && tauler[x][y++] == jugador)
            valorComprovacio++;
        if (dinsElTauler(tauler, x, y) && tauler[x][y++] == 0)
            while (dinsElTauler(tauler, x, y) && tauler[x][y++] == jugador)
                valorComprovacio++;
        valorTotal+=valorarComprovacio(valorComprovacio);

        /* Diagonal inferior dreta */
        x = col; y = row; valorComprovacio = 0;
        while (dinsElTauler(tauler, x, y) && tauler[x++][y--] == jugador)
            valorComprovacio++;
        if (dinsElTauler(tauler, x, y) && tauler[x++][y] == 0)
            while (dinsElTauler(tauler, x, y) && tauler[x++][y--] == jugador)
                valorComprovacio++;
        valorTotal+=valorarComprovacio(valorComprovacio);

        /* Diagonal superior dreta */
        x = col; y = row; valorComprovacio = 0;
        while (dinsElTauler(tauler, x, y) && tauler[x++][y++] == jugador)
            valorComprovacio++;
        if (dinsElTauler(tauler, x, y) && tauler[x++][y++] == 0)
            while (dinsElTauler(tauler, x, y) && tauler[x++][y++] == jugador)
                valorComprovacio++;
        valorTotal+=valorarComprovacio(valorComprovacio);

        /* Horitzontal dreta */
        x = col; y = row;
        while (dinsElTauler(tauler, x, y) && tauler[x++][y] == jugador)
            valorComprovacio++;
        if (dinsElTauler(tauler, x, y) && tauler[x++][y] == 0)
            while (dinsElTauler(tauler, x, y) && tauler[x++][y] == jugador)
                valorComprovacio++;
        valorTotal+=valorarComprovacio(valorComprovacio);
        return valorTotal;
    }

    private boolean dinsElTauler(int[][] tauler, int x, int y){
        return x < tauler.length &&
                x >= 0 &&
                y < tauler[x].length &&
                y >= 0;
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

    /* ====================================================================*/
}


