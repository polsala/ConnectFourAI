package connecta4;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.awt.Frame;
import java.util.*;
import javax.swing.*;
/*
 * Es la classe que mantindra en memoria tot el nostre entorn de simulació.
 * Aqui és on trobarem les llistes de robots, bruticies, parets i la paperera 
 * que intervenen a la simulació actual. També hi tenim definida la creació de 
 * l'entorn i funcions de execució pas per pas.
 */

/**
 *
 * @author 
 */
public class Tauler {

    //Atributs publics per facilitar acces

    
    //atributs privats
    private int dimx;
    private int dimy;
    private int[][] taulell;
    private int jugador;
    private Player1 jugador1;
    private Player2 jugador2;


    public Tauler(int profunditat, char algorisme) {
        /*Constructor, inicialitza el tamany del taulell
         */
        //inicialitzem el tamany del taulell
        this.dimx = 7;   // minim 4
        this.dimy = 6; // minim 4
        this.taulell =  new int[this.dimx][this.dimy];
        this.jugador=1;
        for(int i = 0; i != this.dimx; i++){
            for (int j = 0; j != this.dimy; j++) {
                this.taulell[i][j]=0;
            }
        }
        jugador1 = new Player1(this, profunditat, algorisme);
        jugador2 = new Player2(this);
        
    }
    
    public Tauler(Tauler t) {

        this.dimx = t.dimx;   // minim 4
        this.dimy = t.dimy; // minim 4
        this.taulell = new int[this.dimx][this.dimy];
        this.jugador = t.jugador;

        jugador1 = new Player1(this, t.jugador1.getProfunditat(), t.jugador1.getAlgorisme());
        jugador2 = new Player2(this);

        for(int i = 0; i != this.dimx; i++){
            for (int j = 0; j != this.dimy; j++) {
                this.taulell[i][j]=t.taulell[i][j];
            }
        }
        
    }

    public void setTorn(int jugador){
        if (jugador <= 2 && jugador >= 1)
            this.jugador = jugador;
    }
    public int getpos(int x,int y) {
        /* retorna el valor d'una casella
         */
        return this.taulell[x][y];
    }
    
    public void setpos(int x,int y) {
        /* Afegeix una fitxa
         */
        if(this.taulell[x][y]==0){//si es buida
            this.taulell[x][y]=this.jugador;
        }else{
            Frame frame = new Frame();
            JOptionPane.showMessageDialog(frame, "Jugador"+ this.jugador +" fa un moviment no valid!");
        }
    }

    private int preguntaEntrada(){
        char minCol = 'A', maxCol = (char) ('A'+this.dimx-1);
        char minColMinuscula = 'a', maxColMinuscula = (char) ('a'+this.dimx-1);
        char col = ' ';
        while (!(col <= maxCol && col >= minCol) && !(col <= maxColMinuscula && col >= minColMinuscula)){
            JFrame frame = new JFrame("Següent tirada");
            col = (JOptionPane.showInputDialog(frame, "A quina col·lumna vols tirar? [A-"+maxCol+"]")).charAt(0);
        }
        if (col >= minColMinuscula && col <= maxColMinuscula)
            return col-'a';
        else return col-'A';
    }

    public boolean Step() {
        if (this.jugador==1){
            jugador1.tirada();
            this.jugador=2;

        }else{
            jugador2.tirada(preguntaEntrada());
            this.jugador=1;
        }
        return fi();
    }

    public double getX() {
        return dimx;
    }

    public double getY() {
        return dimy;
    }

    private boolean fi() {
        //si ha acabat el joc retornar true altrament false

        //horitzontal
        for (int i = 0; i < this.getX() - 3; i++) {
            for (int j = 0; j < this.getY(); j++) {
                if (this.getpos(i, j) == 1) {
                    if (this.getpos(i + 1, j) == 1 && this.getpos(i + 2, j) == 1 && this.getpos(i + 3, j) == 1) {
                        Frame frame = new Frame();
                        JOptionPane.showMessageDialog(frame, "Guanya jugador1! (horitzontal)");
                        return true;
                    }
                } else if (this.getpos(i, j) == 2) {
                    if (this.getpos(i + 1, j) == 2 && this.getpos(i + 2, j) == 2 && this.getpos(i + 3, j) == 2) {
                        Frame frame = new Frame();
                        JOptionPane.showMessageDialog(frame, "Guanya jugador2! (horitzontal)");
                        return true;
                    }
                }
            }
        }

        //vertical
        for (int i = 0; i < this.getX(); i++) {
            for (int j = 0; j < this.getY() - 3; j++) {
                if (this.getpos(i, j) == 1) {
                    if (this.getpos(i, j + 1) == 1 && this.getpos(i, j + 2) == 1 && this.getpos(i, j + 3) == 1) {
                        Frame frame = new Frame();
                        JOptionPane.showMessageDialog(frame, "Guanya jugador1! (vertical)");
                        return true;
                    }
                } else if (this.getpos(i, j) == 2) {
                    if (this.getpos(i, j + 1) == 2 && this.getpos(i, j + 2) == 2 && this.getpos(i, j + 3) == 2) {
                        Frame frame = new Frame();
                        JOptionPane.showMessageDialog(frame, "Guanya jugador2! (vertical)");
                        return true;
                    }
                }
            }
        }

        //diagonal 1
        for (int i = 0; i < this.getX() - 3; i++) {
            for (int j = 0; j < this.getY() - 3; j++) {
                if (this.getpos(i, j) == 1) {
                    if (this.getpos(i + 1, j + 1) == 1 && this.getpos(i + 2, j + 2) == 1 && this.getpos(i + 3, j + 3) == 1) {
                        Frame frame = new Frame();
                        JOptionPane.showMessageDialog(frame, "Guanya jugador1! (diagonal 1)");
                        return true;
                    }
                } else if (this.getpos(i, j) == 2) {
                    if (this.getpos(i + 1, j) == 2 && this.getpos(i + 2, j + 2) == 2 && this.getpos(i + 3, j + 3) == 2) {
                        Frame frame = new Frame();
                        JOptionPane.showMessageDialog(frame, "Guanya jugador2! (diagonal 1)");
                        return true;
                    }
                }
            }
        }

        //diagonal 2
        for (int i = 0; i < this.getX() - 3; i++) {
            for (int j = 3; j < this.getY(); j++) {
                if (this.getpos(i, j) == 1) {
                    if (this.getpos(i + 1, j - 1) == 1 && this.getpos(i + 2, j - 2) == 1 && this.getpos(i + 3, j - 3) == 1) {
                        Frame frame = new Frame();
                        JOptionPane.showMessageDialog(frame, "Guanya jugador1! (diagonal 2)");
                        return true;
                    }
                } else if (this.getpos(i, j) == 2) {
                    if (this.getpos(i + 1, j - 1) == 2 && this.getpos(i + 2, j - 2) == 2 && this.getpos(i + 3, j - 3) == 2) {
                        Frame frame = new Frame();
                        JOptionPane.showMessageDialog(frame, "Guanya jugador2! (diagonal 2)");
                        return true;
                    }
                }
            }
        }

        //fi de joc no queden caselles lliures
        for (int i = 0; i < this.getX(); i++) {
            for (int j = 0; j < this.getY(); j++) {
                if (this.getpos(i, j) == 0) {
                    return false;
                }
            }
        }
        Frame frame2 = new Frame();
        JOptionPane.showMessageDialog(frame2, "Empat!");
        return true;
    }

    public int getTornActual() {
        return jugador;
    }
}
