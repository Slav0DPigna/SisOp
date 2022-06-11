package Esercitazione8;

public class IncrementaRigaSem extends Thread{

    int[][] matrice;
    int indiceRiga;

    public IncrementaRigaSem(int [][] matrice,int indiceRiga){
        this.matrice=matrice;
        this.indiceRiga=indiceRiga;
    }//costruttore

    public void run(){
        for(int i=0;i<matrice[indiceRiga].length;i++)
            matrice[indiceRiga][i]++;
        try{
            this.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }//run

}//IncrementaRigaSem
