package connecta4;

/**
 *
 * @author Amat Martínez Vilà
 */

public class Player1 {

    private Tauler meutaulell;
    private int maxProfunditat;

    Player1(Tauler entrada){
        meutaulell = entrada;
        maxProfunditat = 4;
    }

    public void tirada(){
//        alfaBeta(0, Integer.MIN_VALUE, Integer.MAX_VALUE)
        miniMax(meutaulell,0);
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

        int millorMoviment = -1,
            movimentActual = 0,
            millorHeuristic = Integer.MAX_VALUE;

        Move[] movimentsPossibles = taulerModificat.generarMovimentsPossibles();

        if (movimentsPossibles.length == 0)
            return -1;

        for (Move movimentPossible : movimentsPossibles){
            if (taulerModificat.getpos(movimentPossible.getX(), movimentPossible.getY()) == 0) {
                Tauler taulerAux = new Tauler(taulerModificat);
                taulerAux.setTorn(profunditat % 2 + 1);
                taulerAux.setpos(movimentPossible.getX(), movimentPossible.getY());
                int valorHeuristic = miniMax(taulerAux, profunditat);

                if (valorHeuristic < millorHeuristic) {
                    millorHeuristic = valorHeuristic;
                    millorMoviment = movimentActual;
                }
            }
            movimentActual++;
        }
        taulerModificat.setpos(
                movimentsPossibles[millorMoviment].getX(),
                movimentsPossibles[millorMoviment].getY()
        );
        return millorHeuristic;
    }

    private int movimentMax(Tauler taulerModificat, int profunditat){
        int indexMillorMoviment = -1,
            indexMovimentActual = 0,
            millorHeuristic = Integer.MIN_VALUE;

        Move[] movimentsPossibles = taulerModificat.generarMovimentsPossibles();

        if (movimentsPossibles.length == 0)
            return -1;

        for (Move movimentPossible : movimentsPossibles){
            if (taulerModificat.getpos(movimentPossible.getX(), movimentPossible.getY()) == 0) {

                Tauler taulerAux = new Tauler(taulerModificat);
                taulerAux.setTorn(profunditat % 2 + 1);
                taulerAux.setpos(movimentPossible.getX(), movimentPossible.getY());
                int valorHeuristic = miniMax(taulerAux, profunditat);

                if (valorHeuristic > millorHeuristic) {
                    millorHeuristic = valorHeuristic;
                    indexMillorMoviment = indexMovimentActual;
                }

            }
            indexMovimentActual++;
        }
        taulerModificat.setpos(
                movimentsPossibles[indexMillorMoviment].getX(),
                movimentsPossibles[indexMillorMoviment].getY()
        );
        return millorHeuristic;
    }

    private int alfaBeta(int nivell, int alfa, int beta){
        if(nivell==this.maxProfunditat){
            return meutaulell.heuristic();
        }
        else {
            Move[] movimentsPossibles = meutaulell.generarMovimentsPossibles();
            if (movimentsPossibles.length == 0)
                return meutaulell.heuristic();
            
            int millorMoviment = -1, movimentActual = 0;
            if (nivell % 2 == 0){  //Max
                for (Move mov : movimentsPossibles){
                    meutaulell.setpos(mov.getX(), mov.getY());
                    int valor = alfaBeta(nivell++, alfa, beta);
                    if (valor > alfa){
                        alfa = valor;
                        millorMoviment = movimentActual;
                    } else {
                        movimentActual++;
//                        meutaulell.borraPos(mov.getX(), mov.getY());
                    }
                    if (alfa >= beta)
                        break;
                }
                return alfa;
            } else { //Min
                for (Move fill : movimentsPossibles){
                    int valor = alfaBeta(nivell++, alfa, beta);
                    if (valor < beta)
                        beta = valor;
                    if (alfa >= beta)
                        break;
                }
                return beta;
            }
        }
    }
}
