package connecta4;

import java.util.*;

/**
 *
 * @author Amat Martínez Vilà
 */

public class Player1 {

    private Tauler meutaulell;
    private int maxProfunditat;
    private char algorisme;

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

    Player1(Tauler entrada, int profunditat, char algorisme){
        meutaulell = entrada;
        maxProfunditat = profunditat;
        this.algorisme = algorisme;
    }

    public void tirada(){
        switch (this.algorisme){
            case 'a':
                alfaBeta(meutaulell, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                break;
            case 'm':
                miniMax(meutaulell,0);
                break;
        }
    }

    private int miniMax(Tauler taulerModificat, int profunditat){

        if(profunditat == this.maxProfunditat){
            return heuristic(taulerModificat);
        }

        int millor;
        if (profunditat % 2 == 0) {  //Max

            millor = movimentMax(taulerModificat, ++profunditat);

        } else { //Min

            millor = movimentMinim(taulerModificat, ++profunditat);
        }

        if (millor < 0)
            return heuristic(taulerModificat);
        return millor;
    }

    private int movimentMinim(Tauler taulerModificat, int profunditat){
        
        Move millorMoviment = new Move();
        int millorHeuristic = Integer.MAX_VALUE;

        LinkedList<Move> movimentsPossibles = generarMovimentsPossibles(taulerModificat);

        if (movimentsPossibles.isEmpty())
            return -1;
        
        Move movimentPossible;
        while(!movimentsPossibles.isEmpty()){
            movimentPossible = movimentsPossibles.poll();
            if (taulerModificat.getpos(movimentPossible.getX(), movimentPossible.getY()) == 0) {
                Tauler taulerAux = new Tauler(taulerModificat);
                taulerAux.setTorn(profunditat % 2 + 1);
                taulerAux.setpos(movimentPossible.getX(), movimentPossible.getY());
                int valorHeuristic = miniMax(taulerAux, profunditat);
                
                if (valorHeuristic < millorHeuristic) {
                    millorHeuristic = valorHeuristic;
                    millorMoviment = movimentPossible;
                }
            }
        }
        taulerModificat.setpos(
                millorMoviment.getX(),
                millorMoviment.getY()
        );
        return millorHeuristic;
    }

    private int movimentMax(Tauler taulerModificat, int profunditat){
        Move millorMoviment = new Move();
        int millorHeuristic = Integer.MIN_VALUE;

        Queue<Move> movimentsPossibles = generarMovimentsPossibles(taulerModificat);

        if (movimentsPossibles.isEmpty())
            return -1;

        Move movimentPossible;
        while(!movimentsPossibles.isEmpty()){
            movimentPossible = movimentsPossibles.poll();
            if (taulerModificat.getpos(movimentPossible.getX(), movimentPossible.getY()) == 0) {

                Tauler taulerAux = new Tauler(taulerModificat);
                taulerAux.setTorn(profunditat % 2 + 1);
                taulerAux.setpos(movimentPossible.getX(), movimentPossible.getY());
                int valorHeuristic = miniMax(taulerAux, profunditat);

                if (valorHeuristic > millorHeuristic) {
                    millorHeuristic = valorHeuristic;
                    millorMoviment = movimentPossible;
                }
            }
        }
        taulerModificat.setpos(
                millorMoviment.getX(),
                millorMoviment.getY()
        );
        return millorHeuristic;
    }


    private int alfaBeta(Tauler taulerModificat, int profunditat, int alfa, int beta){

        if(profunditat == this.maxProfunditat){
            return heuristic(taulerModificat);
        }

        if (profunditat % 2 == 0) {  //Max
            return movimentMaxAlfaBeta(taulerModificat, ++profunditat, alfa, beta);
        } else { //Min
            return movimentMinimAlfaBeta(taulerModificat, ++profunditat, alfa, beta);
        }
    }

    private int movimentMaxAlfaBeta(Tauler taulerModificat, int profunditat, int alfa, int beta){

        Move millorMoviment = null, movimentPossible;

        Queue<Move> movimentsPossibles = generarMovimentsPossibles(taulerModificat);

        if (movimentsPossibles.isEmpty())
            return heuristic(taulerModificat);

        while(!movimentsPossibles.isEmpty() && alfa < beta){

            movimentPossible = movimentsPossibles.poll();
            if (taulerModificat.getpos(movimentPossible.getX(), movimentPossible.getY()) == 0) {

                Tauler taulerAux = new Tauler(taulerModificat);
                taulerAux.setTorn(profunditat % 2 + 1);
                taulerAux.setpos(
                    movimentPossible.getX(),
                    movimentPossible.getY()
                );

                int valorHeuristic = alfaBeta(taulerAux, profunditat, alfa, beta);

                if (valorHeuristic > alfa) {
                    alfa = valorHeuristic;
                    millorMoviment = movimentPossible;
                }
            }

        }
        if (millorMoviment != null)
            taulerModificat.setpos(
                millorMoviment.getX(),
                millorMoviment.getY()
            );
        return alfa;
    }

    private int movimentMinimAlfaBeta(Tauler taulerModificat, int profunditat, int alfa, int beta){

        Move millorMoviment = null, movimentPossible;

        LinkedList<Move> movimentsPossibles = generarMovimentsPossibles(taulerModificat);

        if (movimentsPossibles.isEmpty())
            return heuristic(taulerModificat);

        while(!movimentsPossibles.isEmpty() && alfa < beta){

            movimentPossible = movimentsPossibles.poll();
            if (taulerModificat.getpos(movimentPossible.getX(), movimentPossible.getY()) == 0) {

                Tauler taulerAux = new Tauler(taulerModificat);
                taulerAux.setTorn(profunditat % 2 + 1);
                taulerAux.setpos(
                    movimentPossible.getX(),
                    movimentPossible.getY()
                );

                int valorHeuristic = alfaBeta(taulerAux, profunditat, alfa, beta);

                if (valorHeuristic < beta) {
                    beta = valorHeuristic;
                    millorMoviment = movimentPossible;
                }

            }

        }
        if (millorMoviment != null)
            taulerModificat.setpos(
                millorMoviment.getX(),
                millorMoviment.getY()
            );
        return beta;
    }

    public LinkedList<Move> generarMovimentsPossibles(Tauler tauler){
        int tableWidth = (int) tauler.getX();

        LinkedList<Move> moviments = new LinkedList<Move>();
        ArrayList<Integer> columnesAleatories = new ArrayList<Integer>();

        for (int i = 0; i < tableWidth; i++)
            columnesAleatories.add(i);

        Collections.shuffle(columnesAleatories);
        for (int col: columnesAleatories){
            moviments.add(new Move(col, primeraFilaBuida(tauler, col)));
        }
        return moviments;
    }

    public int primeraFilaBuida(Tauler tauler, int col){
        int row = 0;
        while (row < tauler.getY()-1 && tauler.getpos(col, row) != 0)
            row++;
        return row;
    }

    public int getProfunditat() {
        return maxProfunditat;
    }

    public char getAlgorisme() {
        return algorisme;
    }


    public int heuristic(Tauler tauler){
        int valor = 0,
                jugadorActual = tauler.getTornActual();

        for(int x = 0; x < tauler.getX(); x++){
            for(int y = 0; y < tauler.getY(); y++){
                if (tauler.getpos(x, y) != 0) {
                    valor += valorarPos(tauler, x, y);
                }
            }
        }
        return valor;
    }

    private int valorarPos(Tauler tauler, int col, int row){
        if (tauler.getpos(col, row) == 0)
            return 0;
        return valorarJugador(tauler, col, row, tauler.getTornActual()) -
               valorarJugador(tauler, col, row, tauler.getTornActual()%2+1);
    }

    private int valorarJugador(Tauler tauler, int col, int row, int jugador){
        int x, y;
        int valorTotal = 0,
                valorComprovacio;

        /* Vertical inferior*/
        x = col; y = row; valorComprovacio = 0;
        while (--y > 0 && tauler.getpos(x, y) == jugador)
            valorComprovacio++;
        valorTotal+=valorarComprovacio(valorComprovacio);

        /* Vertical superior */
        x = col; y = row; valorComprovacio = 0;
        while (++y < tauler.getY()-1 && tauler.getpos(x, y) == tauler.getTornActual())
            valorComprovacio++;
        valorTotal+=valorarComprovacio(valorComprovacio);

        /* Diagonal inferior esquerre */
        x = col; y = row; valorComprovacio = 0;
        while (--y > 0 && --x > 0 && tauler.getpos(x, y) == tauler.getTornActual())
            valorComprovacio++;
        valorTotal+=valorarComprovacio(valorComprovacio);

        /* Diagonal superior esquerre */
        x = col; y = row; valorComprovacio = 0;
        while (++y < tauler.getY()-1 && --x > 0 && tauler.getpos(x, y) == tauler.getTornActual())
            valorComprovacio++;
        valorTotal+=valorarComprovacio(valorComprovacio);

        /* Diagonal inferior dreta */
        x = col; y = row; valorComprovacio = 0;
        while (--y > 0 && ++x < tauler.getX()-1 && tauler.getpos(x, y) == tauler.getTornActual())
            valorComprovacio++;
        valorTotal+=valorarComprovacio(valorComprovacio);

        /* Diagonal superior dreta */
        x = col; y = row; valorComprovacio = 0;
        while (++y < tauler.getY()-1 && ++x < tauler.getX()-1 && tauler.getpos(x, y) == tauler.getTornActual())
            valorComprovacio++;
        valorTotal+=valorarComprovacio(valorComprovacio);

        /* Horitzontal esquerre */
        x = col; y = row; valorComprovacio = 0;
        while (--x > 0 && tauler.getpos(x, y) == tauler.getTornActual())
            valorComprovacio++;
        valorTotal+=valorarComprovacio(valorComprovacio);

        /* Horitzontal dreta */
        x = col; y = row;
        while (++x < tauler.getX()-1 && tauler.getpos(x, y) == tauler.getTornActual())
            valorComprovacio++;
        valorTotal+=valorarComprovacio(valorComprovacio);
        return valorTotal;
    }

    private int valorarComprovacio(int valorComprovacio){
        switch (valorComprovacio){
            case 3:
                return valorComprovacio*10;
            case 4:
                return valorComprovacio*100;
            default:
                return valorComprovacio;
        }
    }
}
