package Esercitazione3;

public class StampanteT implements Runnable {

    private int da,a;

    public StampanteT(int da,int a){
        if(da>a)
            throw new IllegalArgumentException();
        this.a=a;
        this.da=da;
    }//StampanteT

    @Override
    public void run() {
        for(int i=da;i<=a;i++)
            System.out.print(i+" ");
    }//run
}//StampanteT
