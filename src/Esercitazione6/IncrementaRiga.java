package Esercitazione6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class IncrementaRiga extends Thread{

    int indiceRiga;
    AtomicInteger[][] matrice;

    public IncrementaRiga(AtomicInteger[][] matrice,int indiceRiga){
        this.indiceRiga=indiceRiga;
        this.matrice=matrice;
    }//costruttore

    public void run() {
        for(int i=0;i<matrice[indiceRiga].length;i++)
            this.matrice[indiceRiga][i].addAndGet(1);
    }

    public static boolean sonoTuttiTerminati(ArrayList<? extends Thread> v){
        for(int i=0;i<v.size();i++)
            if(v.get(i).isAlive())
                return false;
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger[][] m=new AtomicInteger[7][5];

        for(int i=0;i<m.length;i++)
            for(int j=0;j<m[i].length;j++)
                m[i][j]=new AtomicInteger(0);


        ArrayList<IncrementaRiga> ir=new ArrayList<>();
        ArrayList<DecrementaColonna> dc=new ArrayList<>();

        for(int i=0;i<m.length;i++) {
            ir.add(new IncrementaRiga(m, i));
            ir.get(i).start();
            ir.get(i).join();
        }

        for(int i=0;i<m[0].length;i++){
            dc.add(new DecrementaColonna(m,i));
            dc.get(i).start();
            dc.get(i).join();
        }

    flag:    if (sonoTuttiTerminati(dc) && sonoTuttiTerminati(ir)) {
            for (int i = 0; i < m.length; i++)
                for (int j = 0; j < m[i].length; j++)
                    if (!(m[i][j].toString().equals("0"))){
                        System.out.println("Qualcosa è andato storto");
                        for (int k = 0; k < m.length; k++)
                            System.out.println(Arrays.toString(m[k]));
                        System.exit(0);
                    }
        }
        else
            break flag;
        System.out.println("è andato tutto bene");
        System.exit(0);
    }
}
