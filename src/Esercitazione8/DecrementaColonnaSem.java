package Esercitazione8;

public class DecrementaColonnaSem extends Thread{
        int[][] matrice;
        int indiceColonna;

        public DecrementaColonnaSem(int [][] matrice,int indiceColonna){
            this.matrice=matrice;
            this.indiceColonna=indiceColonna;
        }//costruttore

        public void run(){
            for(int i=0;i<matrice.length;i++)
                matrice[i][indiceColonna]--;
            try{
            this.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }//run
    }//DecrementaColonnaSem
