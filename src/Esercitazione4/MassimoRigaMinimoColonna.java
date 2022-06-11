package Esercitazione4;

//1.04

public class MassimoRigaMinimoColonna {
    public static void main(String[] args) throws InterruptedException{
        int [][] matrice={{2,2,2,5,2},{2,2,2,7,2},{1,1,1,4,1},{2,2,2,9,2}};
        int numeroRighe=matrice.length;
        int numeroColonne=matrice[0].length;
        System.out.format("La matrice è composta da %d righe e %d colonne\n",numeroRighe,numeroColonne);

        TrovaMassimo[] maxThread=new TrovaMassimo[numeroRighe];
        for(int i=0;i<numeroRighe;i++){
            maxThread[i]=new TrovaMassimo(matrice,i);
            maxThread[i].start();
        }

        TrovaMinimo[] minThread=new TrovaMinimo[numeroColonne];
        for(int i=0;i<minThread.length;i++){
            minThread[i]=new TrovaMinimo(matrice,i);
            minThread[i].start();
        }

        int iMin=-1;
        int jMax=-1;

        if(numeroRighe<=numeroColonne){
            for(int i=0;i<numeroRighe;i++){
                jMax=maxThread[i].getjMax();
                if(minThread[jMax].getiMin()==i){
                    iMin=i;
                    break;
                }
            }
        }
        else{
            for(int j=0;j<numeroColonne;j++){
                iMin=minThread[j].getiMin();
                if(maxThread[iMin].getjMax()==j){
                    jMax=j;
                    break;
                }
            }
        }
        if(jMax<0 || iMin<0)
            System.out.println("L'elemento cercato non esiste");
        else
            System.out.println(String.format("L'elemento cercato si trova in posizione (%d,%d) con valore %d",iMin,jMax,matrice[iMin][jMax]));
    }//main
}//MassimoRigaMinimoColonna
