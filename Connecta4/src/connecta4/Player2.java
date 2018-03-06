/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connecta4;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Exor_P
 */



public class Player2 {
    static int _maxDepth_ = 8;
    final static int _range_ = 100;
    static int _rows_ = 0;
    static int _columns_ = 0;
    
    private static Object cloneObject(Object obj){
        try{
            Object clone = obj.getClass().newInstance();
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if(field.get(obj) == null || Modifier.isFinal(field.getModifiers())){
                    continue;
                }
                if(field.getType().isPrimitive() || field.getType().equals(String.class)
                        || field.getType().getSuperclass().equals(Number.class)
                        || field.getType().equals(Boolean.class)){
                    field.set(clone, field.get(obj));
                }else{
                    Object childObj = field.get(obj);
                    if(childObj == obj){
                        field.set(clone, clone);
                    }else{
                        field.set(clone, cloneObject(field.get(obj)));
                    }
                }
            }
            return clone;
        }catch(Exception e){
            return null;
        }
    }
    
    private class Tree {
        public int weight;
        private Tauler _board;
        private ArrayList<Integer> _bestMoves;
        private int _preRow;
        private int _preCol;
        private int _prePlayer;
        private int _depth;
        
        public Tree(Tauler board, int depth){
            this._board = board;
            this._bestMoves = new ArrayList<>();
            this._depth = depth;
            this.weight = get_weight();
            if (_rows_ != 0) {
                _rows_ = int.class.cast(_board.getX());
                _columns_ = int.class.cast(_board.getY());
            }
            
            
        }
        
        int get_weight(){
            return 1;
        }
        
        private void insert_in_board_heuristic(int row, int col){
            if(_board.getpos(row, col) == 0){
                while(col < _columns_-1 && _board.getpos(row, col+1) == 0){
                    row++;
                }
                if (_depth % 2 == 0){
                    //1
                    _board.setpos(row, col);
                }else{
                    //2
                    _board.setpos(row, col);
                }
                // todo check
                _preRow = row;
                _preCol = col;
            
            }
        }
        
        private void set_posibilities(){
            /**
             * Void function to set _bestMoves class variable with all 
             * respectable moves.
             * @param 
             * @return 
             **/
            if (_depth < _maxDepth_ && this.weight < 100 && this.weight > -100){
                ArrayList<Integer> prov_moves = new ArrayList<>();
                // _rows in truly sets all possiblities in a move
                for (int i = 0; i < _rows_; i++){
                    if(_board.getpos(i, 0) == 0){
                        prov_moves.add(i);
                    }
                }
                
                for (int i = 0; i < prov_moves.size(); i++){
                    insert_in_board_heuristic(prov_moves.get(i),0);
                    Tree child = new Tree(_board, _depth+1);
                    // todo _board.setpos(x,y,0)
                    
                    if (i == 1){
                        _bestMoves.add(prov_moves.get(i));
                        weight = child.weight;
                    }else if (_depth % 2 == 0){
                        if (weight < child.weight){
                            _bestMoves.clear();
                            _bestMoves.add(prov_moves.get(i));
                            weight = child.weight;
                        }else if (weight == child.weight){
                            _bestMoves.add(prov_moves.get(i));
                        }
                    }else if(_depth % 2 == 0){
                        if (weight > child.weight){
                            _bestMoves.clear();
                            _bestMoves.add(prov_moves.get(i));
                            weight = child.weight;
                        }else if (weight == child.weight){
                            _bestMoves.add(prov_moves.get(i));
                        }
                    }
                }
            }else {
                weight = get_weight();
            }
        }
    }           
    private Tauler meutaulell;
    Player2(Tauler entrada){
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
        return new int[]{1,1};  
    }
}
