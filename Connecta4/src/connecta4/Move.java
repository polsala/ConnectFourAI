package connecta4;

/**
 *
 * @author amat
 */
public class Move{
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
    
    public int[] retornaArray(){
        return this.pos;
    }
    
    @Override public boolean equals(Object object) {
        if (object instanceof Move){
            Move m = (Move)object;
            return this.pos[0] == m.pos[0] && this.pos[1] == m.pos[1];
        }
        return false;
    }
}
