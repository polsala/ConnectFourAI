package connecta4;

import java.awt.Frame;
import javax.swing.JOptionPane;
/*
 * Es la classe que mantindra en memoria tot el nostre entorn de simulació.
 * Aqui és on trobarem les llistes de robots, bruticies, parets i la paperera 
 * que intervenen a la simulació actual. També hi tenim definida la creació de 
 * l'entorn i funcions de execució pas per pas.
 */

/**
 *
 * @author Llorenç
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


    public Tauler() {
        /*Constructor, inicialitza el tamany del taulell
         */
        //inicialitzem el tamany del taulell
        this.dimx = 6;   // minim 4
        this.dimy = 7; // minim 4
        this.taulell =  new int[this.dimx][this.dimy];
        this.jugador=1;
        for(int i = 0; i != this.dimx; i++){
            for (int j = 0; j != this.dimy; j++) {
                this.taulell[i][j]=0;
            }
        }
        jugador1 = new Player1(this);
        jugador2 = new Player2(this);
        
    }

    public int[][] getTaulell(){
        /*Retorna el taulell*/
        return this.taulell;
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

    public boolean Step() {
        boolean acabat=false;
        if (this.jugador==1){
            int[] tir=jugador1.tirada();
            setpos(tir[0],tir[1]);
            acabat=fi();
            this.jugador=2;
            
        }else{
            int[] tir=jugador2.tirada();
            setpos(tir[0],tir[1]);
            acabat=fi();
            this.jugador=1;
        }
        //retornem true si falten passos de simulació
        return acabat;
    }

    public double getX() {
        /*Metode que retorna la dimenció X del taulell de simulació, la
         * coordenada (0,0) es la de dalt a l'esquerra i la de baix a la dreta
         * es la (dimx,dimy)
         */
        return dimx;
    }

    public double getY() {
        /*Metode que retorna la dimenció X del taulell de simulació, la
         * coordenada (0,0) es la de dalt a l'esquerra i la de baix a la dreta
         * es la (dimx,dimy)
         */
        return dimy;
    }

    private boolean fi() {
        //si ha acabat el joc retornar true altrament false
        
        //horitzontal
        for(int i=0;i<this.getX()-3;i++){
            for(int j=0;j<this.getY();j++){
                if (this.getpos(i,j) == 1){
                    if(this.getpos(i+1,j) == 1&&this.getpos(i+2,j) == 1&&this.getpos(i+3,j) == 1){
                        Frame frame = new Frame();
                        JOptionPane.showMessageDialog(frame, "Guanya jugador1! (horitzontal)");
                        return true;
                    }
                }else if (this.getpos(i,j) == 2){
                    if(this.getpos(i+1,j) == 2&&this.getpos(i+2,j) == 2&&this.getpos(i+3,j) == 2){
                        Frame frame = new Frame();
                        JOptionPane.showMessageDialog(frame, "Guanya jugador2! (horitzontal)");
                        return true;
                    }                
                }               
            }
        }
        
        //vertical
        for(int i=0;i<this.getX();i++){
            for(int j=0;j<this.getY()-3;j++){
                if (this.getpos(i,j) == 1){
                    if(this.getpos(i,j+1) == 1&&this.getpos(i,j+2) == 1&&this.getpos(i,j+3) == 1){
                        Frame frame = new Frame();
                        JOptionPane.showMessageDialog(frame, "Guanya jugador1! (vertical)");
                        return true;
                    }
                }else if (this.getpos(i,j) == 2){
                    if(this.getpos(i,j+1) == 2&&this.getpos(i,j+2) == 2&&this.getpos(i,j+3) == 2){
                        Frame frame = new Frame();
                        JOptionPane.showMessageDialog(frame, "Guanya jugador2! (vertical)");
                        return true;
                    }                
                }               
            }
        }
        
        //diagonal 1
        for(int i=0;i<this.getX()-3;i++){
            for(int j=0;j<this.getY()-3;j++){
                if (this.getpos(i,j) == 1){
                    if(this.getpos(i+1,j+1) == 1&&this.getpos(i+2,j+2) == 1&&this.getpos(i+3,j+3) == 1){
                        Frame frame = new Frame();
                        JOptionPane.showMessageDialog(frame, "Guanya jugador1! (diagonal 1)");
                        return true;
                    }
                }else if (this.getpos(i,j) == 2){
                    if(this.getpos(i+1,j) == 2&&this.getpos(i+2,j+2) == 2&&this.getpos(i+3,j+3) == 2){
                        Frame frame = new Frame();
                        JOptionPane.showMessageDialog(frame, "Guanya jugador2! (diagonal 1)");
                        return true;
                    }                
                }               
            }
        }
        
        //diagonal 2
        for(int i=0;i<this.getX()-3;i++){
            for(int j=3;j<this.getY();j++){
                if (this.getpos(i,j) == 1){
                    if(this.getpos(i+1,j-1) == 1&&this.getpos(i+2,j-2) == 1&&this.getpos(i+3,j-3) == 1){
                        Frame frame = new Frame();
                        JOptionPane.showMessageDialog(frame, "Guanya jugador1! (diagonal 2)");
                        return true;
                    }
                }else if (this.getpos(i,j) == 2){
                    if(this.getpos(i+1,j-1) == 2&&this.getpos(i+2,j-2) == 2&&this.getpos(i+3,j-3) == 2){
                        Frame frame = new Frame();
                        JOptionPane.showMessageDialog(frame, "Guanya jugador2! (diagonal 2)");
                        return true;
                    }                
                }               
            }
        }
        
        //fi de joc no queden caselles lliures
         for(int i=0;i<this.getX();i++){
            for(int j=0;j<this.getY();j++){
                if (this.getpos(i,j) == 0){
                    return false;
                }
            }
        }
        Frame frame2 = new Frame();
         JOptionPane.showMessageDialog(frame2, "Empat!");
        return true;
    }
}
