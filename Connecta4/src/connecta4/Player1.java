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
            return taulerModificat.heuristic();
        }

        int millor;
        if (profunditat % 2 == 0) {  //Max

            millor = movimentMax(taulerModificat, ++profunditat);

        } else { //Min

            millor = movimentMinim(taulerModificat, ++profunditat);
        }

        if (millor < 0)
            return taulerModificat.heuristic();
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
            return taulerModificat.heuristic();
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
            return taulerModificat.heuristic();

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
            return taulerModificat.heuristic();

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
}
