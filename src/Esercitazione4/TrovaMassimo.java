package Esercitazione4;

public class TrovaMassimo extends Thread{
    int r=0;
    int[] riga;
    public TrovaMassimo(int[][] matrice,int i){
        if(i<0|| i>=matrice[0].length)
            throw new IllegalArgumentException();
        riga=new int[matrice[0].length];
        for(int j=0;j<matrice[0].length;j++)
            riga[j]=matrice[i][j];
    }//TrovaMassimo

    public void run(){
        for(int i=0;i<riga.length;i++)
            if(riga[i]>riga[r])
                r=i;
    }//run

    public int getjMax() throws InterruptedException{
        this.join();
        return r;
    }
}//TrovaMassimo
