package implementacion.anchura;


import genericos.anchura.Estado;
import genericos.anchura.PAnchura;

import java.util.LinkedList;

/**
 *
 * @author sebastian
 */
public class PuzzleState implements Estado, Comparable <Object> {
    
    MyKey tablero;
    Estado papa;
    String transicion;
    
    public class MyKey implements Comparable<MyKey> {
        byte[] tablero;
        
        public MyKey(byte[] tablero) {
            this.tablero = tablero;
        }
        
        public int compareTo(MyKey b) {
            for (int i=0; i<9; i++) {
                int d = this.tablero[i] - b.tablero[i];
                if (d != 0) return d;
            }
            return 0;
        }        
    }
    
    public PuzzleState(byte[] tablero) {
        this.tablero = new MyKey(tablero);
    }

    public LinkedList<Estado> sigEstados() {
        LinkedList<Estado> res = new LinkedList<Estado>();
        for (int i=0; i<3; i++) {
            byte[] ntab = tablero.tablero.clone();
            shiftRow(i, ntab);
            PuzzleState p = new PuzzleState(ntab);
            p.transicion = "H" + (i+1);
            p.papa = this;
            res.add(p);
            ntab = tablero.tablero.clone();
            shiftCol(i, ntab);
            p = new PuzzleState(ntab);
            p.transicion = "V" + (i+1);
            p.papa = this;
            res.add(p);
        }        
        return res;
    }
    
    private void shiftCol(int col, byte[] tab) {
        byte temp = tab[col];
        for (int i=0; i<2; i++) {
            tab[col + i*3] = tab[col + 3*(i+1)];
        }
        tab[col + 6] = temp;
    }
    
    private void shiftRow(int row, byte[] tab) {
        int i = row*3;
        byte temp = tab[i];
        for (int j=0; j<2; j++) {
            tab[i+j] = tab[i+j+1];
        }
        tab[i+2] = temp;        
    }

    public Object getKey() {
        return tablero;
    }

    public Estado getPadre() {
        return papa;
    }

    public void setPadre(Estado padre) {
        papa = padre;
    }

    public int compareTo(Object o) {
        PuzzleState b = (PuzzleState)o;
        return this.tablero.compareTo(b.tablero);
    }
    
    @Override
    public String toString() {
        return transicion;        
    }
    
    public static void main(String[] args) {
        PAnchura pa = new PAnchura();
        PuzzleState org = new PuzzleState(new byte[]{1,2,3,4,5,6,7,8,9});
        pa.genArbol(org);
        PuzzleState dest = new PuzzleState(new byte[]{2,3,1,4,5,6,8,9,7});
        PuzzleState act = (PuzzleState)pa.getNodo(dest.getKey());
        if (act == null)
            System.out.println("No tiene sol");
        else {
            while (act.compareTo(org) != 0) {
                System.out.println(act);
                act = (PuzzleState)act.getPadre();
            }
        }
    }
}
