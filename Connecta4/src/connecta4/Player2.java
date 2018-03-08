/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connecta4;

import java.util.ArrayList;

/**
 *
 * @author Exor_P
 */

class FakeBoard{
    private int[][] _fakeBoard;
    private int _rows;
    private int _columns;
    
    public FakeBoard(int[][] board){
        this._fakeBoard = board;
        this._rows = board[0].length;
        this._columns = board.length;
    }
    
    public int getpos(int x, int y){return _fakeBoard[x][y];}
    
    public void setpos(int x, int y, int colour){_fakeBoard[x][y] = colour;}
    
    public int rows(){return _rows;}
    
    public int cols(){return _columns;}
    
    public String toString(){
        String res = "";
        for (int i = 0; i < _fakeBoard.length; i++) {
            for (int j = 0; j < _fakeBoard[i].length; j++) {
                res += _fakeBoard[i][j] + " ";
                //System.out.print(_fakeBoard[i][j] + " ");
            }
            res += "\n";
            //System.out.println();
        }
        return res;
    }
}

public class Player2 {
    static int _maxDepth_ = 4;
    final static int _range_ = 100;
    static int _rows_ = 0;
    static int _columns_ = 0;
    static int _mineColour_;
    static int _oponentColour_;
    
    private class Tree { 
        public int weight;
        private FakeBoard _board;
        private ArrayList<Integer> _bestMoves;
        private int _preRow;
        private int _preCol;
        private int _prePlayer;
        private int _depth;
        
        public Tree(Tauler board, int depth){
            this._board = new FakeBoard(board.getTaulell());
            this._bestMoves = new ArrayList<>();
            this._depth = depth;
            this.weight = get_weight();
            if (_rows_ == 0) {
                System.out.println(_board.rows() + "dssssss");
                _rows_ = _board.rows();
                _columns_ = _board.cols();
            }
            set_posibilities();
            //insert_in_board_heuristic(_rows_, _columns_);
            //System.out.println(_rows_);
        }
        
        public Tree(FakeBoard board, int depth){
            this._board = board;//todo!!!!!!!!
            this._bestMoves = new ArrayList<>();
            this._depth = depth;
            this.weight = get_weight();
            if (_rows_ == 0) {
                _rows_ = _board.rows();
                _columns_ = _board.cols();
            }
            set_posibilities();
            //insert_in_board_heuristic(_rows_, _columns_);
            
        }
        
        public int[] res(){
            int x = best_randomizer();
            int y = 0;
            while(_board.getpos(x, y) != 0){
                y++;
            }
            return new int[]{x, y};
        }
        
        public int best_randomizer(){
            int factor = _bestMoves.size();
            //if (factor == 0) return 0;
            int random = (int)(Math.random() * 100) % factor;
        
            System.out.println(_bestMoves);

            return _bestMoves.get(random);
        }
        
        public int four_connected(int x, int y, int i, int j){
            int res_weight = 1;
            int aux_x = x;
            int aux_y = y;
            int aux_colour = _board.getpos(x, y);
            
            for(int k = 1; k < 4; k++){
                int _x_i_k = x+i*k;
                int _y_j_k = y+j*k;
                System.out.println("Range" + _x_i_k + "lll" + _y_j_k);
                if(_x_i_k < 0 || _y_j_k < 0 || _x_i_k >= _columns_ || _y_j_k >= _rows_){
                    return 0;
                }
                
                int iter_colour = _board.getpos(_x_i_k, _y_j_k);
                
                if(iter_colour == aux_colour){
                    res_weight++;
                }else if(iter_colour == 0){
                    return 0;
                }else{
                    for(int w = _y_j_k; w >=0; w--){
                        if(_board.getpos(_x_i_k, w) == 0) res_weight--;
                    }
                }
            }
            if (res_weight == 4) return 100;
            if (res_weight < 0) return -100;
            return res_weight;
        }
        
        public int possible_moves(int i, int j)
	{
		int res_nMoves = 0;
		res_nMoves += four_connected(i, j, -1, -1);
		res_nMoves += four_connected(i, j, -1, 0);
		res_nMoves += four_connected(i, j, -1, 1);
		res_nMoves += four_connected(i, j, 0, -1);
		res_nMoves += four_connected(i, j, 0, 1);
		res_nMoves += four_connected(i, j, 1, -1);
		res_nMoves += four_connected(i, j, 1, 0);
		res_nMoves += four_connected(i, j, 1, 1);
		
		return res_nMoves;
	}

        
        int get_weight(){
            int res_weight = 0;
            for(int j = 0; j < _rows_; j++){
                for(int i = 0; i < _columns_; i++){
                    int colour = _board.getpos(i, j);
                    
                    if (colour != 0){
                        // todo reviw
                        if (colour == _mineColour_){
                            res_weight += possible_moves(i, j) * (_maxDepth_ - _depth);
                        }else{
                            res_weight -= possible_moves(i, j) * (_maxDepth_ - _depth);
                        }
                    }
                }
            }
            System.out.println("peso:" + res_weight);
            return res_weight;
        }
        
        /*private void insert_in_board_heuristic(int xx, int yy){
            int x = xx;
            int y = yy;
            if(_board.getpos(x, y) == 0){
                //System.out.println(_board);
                System.out.println(x + "|" + y + "aaa");
                //while(x < _columns_-1 && _board.getpos(x, y+1) == 0){
                while(y < _rows_-1 && _board.getpos(x, y+1) != 0){
               // while(row < _columns_ && _board.getpos(row, col) == 0){
                    //row++;
                    y++;
                }
                if (_depth % 2 == 0){
                    //1
                    System.out.println(x + "|" + y+1);
                    _board.setpos(x, y+1, _mineColour_);
                }else{
                    //2
                    System.out.println(x + "-" + y+1);
                    _board.setpos(x, y+1, _oponentColour_);
                }
                // todo check
                _preRow = x;
                _preCol = y+1;
                _prePlayer = _board.getpos(x, y+1);
                 System.out.println(x + "|" + y + "aaaddd");
                 System.out.println(_board);
            
            }
        }*/
        
        private void insert_in_board_heuristic(int xx, int yy){
            /**
             * This method insert color in a column
             **/
            int x = xx;
            int y = yy;
            if(_board.getpos(x, y) == 0){
                //System.out.println(_board);
                if (_depth % 2 == 0){
                    //1
                    _board.setpos(x, y, _mineColour_);
                }else{
                    //2
                    _board.setpos(x, y, _oponentColour_);
                }
                // todo check
                _preRow = x;
                _preCol = y;
                _prePlayer = _board.getpos(x, y);
                System.out.println(_board);
            
            }else{
                while(y < _rows_ && _board.getpos(x, y) != 0){
                    y++;
                }

                if (_depth % 2 == 0){
                    //1
                    _board.setpos(x, y, _mineColour_);
                }else{
                    //2
                    _board.setpos(x, y, _oponentColour_);
                }
                // todo check
                _preRow = x;
                _preCol = y;
                _prePlayer = _board.getpos(x, y);

                //todo
            }
            System.out.println("setting" +x + "|" + y);
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
                // _colums in truly sets all possiblities in a move
                // Mirem la fila d'abaix si alguna es 0 és opció
                for (int i = 0; i < _columns_; i++){
                    if(_board.getpos(i, 0) == 0){
                        prov_moves.add(i);
                    }
                    /*todo check*/
                    else if(_board.getpos(i, _rows_-1) == 0){
                        prov_moves.add(i);
                    }
                    /**/
                }
                
                for (int i = 0; i < prov_moves.size(); i++){
                    //System.out.println("before in");
                    //System.out.println(_board);
                    insert_in_board_heuristic(prov_moves.get(i),0);
                    //System.out.println("after in");
                    //System.out.println(_board);
                    //System.out.println("sssss" + _preRow + _preCol);
                    System.out.println("new tree");
                    Tree child = new Tree(_board, _depth+1);
                    System.out.println("end tree");
                    _prePlayer = 0;
                    
                    _board.setpos(_preRow, _preCol, 0);
                    
                    if (i == 0){// todo
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
                    }else if(_depth % 2 == 1){
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
        if (this.getClass().getName().endsWith("1")){
            _mineColour_ = 1;
            _oponentColour_ = 2;
        }else{
            _mineColour_ = 2;
            _oponentColour_ = 1;
        }
        
    }
    
    
    public int[] minimax()
    {
        Tree tree = new Tree(meutaulell, 0);
        return tree.res();
    }
    
    public int[] tirada(){
        int x,y;
        //FakeBoard d = new FakeBoard(meutaulell.getTaulell());
        //System.out.println(d);
        //return new int[]{0,1}; 
        return minimax();
        
        //return new int[]{1,1};  
    }
}


