package Esercitazione4;

public class TrovaMinimo extends Thread{
    int r=0;
    int[] colonna;

    public TrovaMinimo(int[][] matrice, int i){
        colonna=new int[matrice.length];
        for(int j=0;j<matrice.length;j++){
            colonna[j]=matrice[j][i];
        }
    }//costruttore

    public void run(){
        for(int i=0;i<colonna.length;i++)
            if(colonna[i]<colonna[r])
                r=i;
    }

    public int getiMin() throws InterruptedException{
        this.join();
        return r;
    }
}//TrovaMinimo
