package Esercitazione12Funivia;

public abstract class Funivia {
    protected final int TURISTA_A_PIEDI=0,TURISTA_IN_BICI=1;

    public abstract void pilotaStart() throws InterruptedException;
    public abstract void pilotaEnd() throws InterruptedException;
    public abstract void turistaSali(int t) throws InterruptedException;
    public abstract void turistaScendi(int t) throws InterruptedException;

    public void test(int numTuristiPiedi,int numTuristiBici){
        for(int i=0;i<numTuristiPiedi;i++)
            new Thread(new Turista(this,TURISTA_A_PIEDI)).start();
        for(int i=0;i<numTuristiBici;i++)
            new Thread(new Turista(this,TURISTA_IN_BICI)).start();//se é runnable si fa cosí
        Thread t=new Thread(new Pilota(this));
        t.setDaemon(true);
        t.start();
    }//test
}//Funivia
