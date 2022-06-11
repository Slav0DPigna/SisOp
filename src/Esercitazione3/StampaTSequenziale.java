package Esercitazione3;

public class StampaTSequenziale {
    public static void main(String[] args){
        StampanteT s1=new StampanteT(1,10);
        StampanteT s2=new StampanteT(11,20);
        Thread t1=new Thread(s1);
        Thread t2=new Thread(s2);
        //in questo caso ho dovuto usare la soluzione di creare un oggetto thread perchè  la mia classe implements runnable e non extends Thread
        t1.start();

        try{
            t1.join();
            t2.start();
            t2.join();
            //da notare che la seconda join è necessaria perchè non fa eseguire il thread main
        }
        catch(InterruptedException e){
        }
        System.out.println();
        System.out.println("Fine");
    }//main
}//StampaTSequenziale
/*
* Da notare che questa app scritta in questo modo non ha nessun senso
*/
