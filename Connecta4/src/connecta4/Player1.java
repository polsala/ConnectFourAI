/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connecta4;

/**
 *
 * @author Usuari
 */
public class Player1 {
    private Tauler meutaulell;
    Player1(Tauler entrada){
        meutaulell = entrada;
    }
    public int[] tirada(){
        int x,y;
        //busco una posicio buida
        for(int i=0;i<meutaulell.getX();i++){
            for(int j=0;j<meutaulell.getY();j++){
                if (meutaulell.getpos(i,j) == 0){
                    return new int[]{i,j}; 
                }
            }
        }
        
        //Un retorn per defecte
        return new int[]{0,0};  
    }
}
