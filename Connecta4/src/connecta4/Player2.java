/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connecta4;

/**
 *
 * @author Usuari
 */
public class Player2 {

    private Tauler meutaulell;

    Player2(Tauler entrada){
        meutaulell = entrada;
    }

    public void tirada(int col){
        int row = 0;
        while (row < meutaulell.getY()-1 && meutaulell.getpos(col, row) != 0)
            row++;
        meutaulell.setpos(col, row);
    }
}
