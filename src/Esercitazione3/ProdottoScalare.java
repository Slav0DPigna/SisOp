package Esercitazione3;
//29

public class ProdottoScalare extends Thread{

    private int[] v1,v2;
    private int r,da,a;

    public ProdottoScalare(int[] x,int[] y,int da,int a){
        if(x.length!=y.length || da>=a)
            throw new IllegalArgumentException();
        v1=new int[a-da];
        v2=new int[a-da];
        r=0;
        for(int i=da;i<a;i++) {
            v1[i-da] = x[i];
            v2[i-da] = y[i];
        }
    }//costruttore

    public void run(){
        for(int i=0;i<v1.length;i++)
            r=r+v1[i]*v2[i];
    }//run

    public int getRisultato() throws InterruptedException{
        this.join();
        return r;
    }//getRisultato

    public static void main(String[] args)throws InterruptedException{
        int[] v1={1,2,3,4,5,6,7,8};
        int[] v2={1,2,3,4,5,6,7,8};
        int r=0;
        ProdottoScalare ps1;
        ProdottoScalare ps2;
        ProdottoScalare ps3;
        ProdottoScalare ps4;

        for(int i=0;i<v1.length;i=i+8) {
            ps1 = new ProdottoScalare(v1, v2, i, i+2);
            ps2 = new ProdottoScalare(v1, v2, i+2, i+4);
            ps3 = new ProdottoScalare(v1, v2, i+4, i+6);
            ps4 = new ProdottoScalare(v1, v2, i+6, i+8);
            ps1.start();
            ps2.start();
            ps3.start();
            ps4.start();
            r=r+ ps4.getRisultato()+ ps2.getRisultato()+ ps3.getRisultato()+ ps1.getRisultato();
        }
        System.out.println(r);
    }//main
    /*
    * l'approccio é giusto ma se avessi usato un array per contenere i miei thread invece di crearli
    * a mano sarebbe stato meglio e oltre a questo la m( la costante di quanti elementi prendeva ogni thread
    * se la impostavo invece di calcolarla a mano sarebbe stat meglio) n=(lunghezza del vettore) passo=n/m
    * nel mio caso il passo era di due. Del resto va bene cosí com'é.
    */
}//ProdottoScalare
