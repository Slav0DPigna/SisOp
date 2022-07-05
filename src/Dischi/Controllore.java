package Dischi;

public abstract class Controllore{

    protected Disco[] dischi=new Disco[3];

    public Controllore(){
        for(int i=0;i<3;i++)
            dischi[i]=new Disco(this);
    }//costruttore

    abstract void allocaDischi(int a,int b) throws InterruptedException;
    abstract void rilasciaDischi(int a, int b);

    public void test(){
        for(int i=0;i<100;i++)
            new Processo(this).start();
    }//test
}//Controllore
