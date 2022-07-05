package Dischi;

import java.util.concurrent.locks.*;

public class ControlloreLC extends Controllore{

    Lock l=new ReentrantLock();
    Condition possoAllocare=l.newCondition();

    @Override
    void allocaDischi(int a, int b) throws InterruptedException{
        l.lock();
        try {
            System.out.println("Il processo con id "+Thread.currentThread().getId()+" prova ad usare i dischi "+a+" e "+b);
            while (dischi[a - 1].getProcessiAttivi() >= 5 || dischi[b - 1].getProcessiAttivi() >= 5)
                possoAllocare.await();
            dischi[a - 1].allocaProcesso();
            dischi[b - 1].allocaProcesso();
            System.out.println("Il processo con id "+Thread.currentThread().getId()+" ha accesso ai dischi "+a+" e "+b);
            System.out.println("Il disco "+a+" è usato da "+dischi[a-1].getProcessiAttivi()+" processi/o");
            System.out.println("Il disco "+b+" è usato da "+dischi[b-1].getProcessiAttivi()+" processi/o");
        }finally {
            l.unlock();
        }
    }//allocaDischi

    @Override
    void rilasciaDischi(int a, int b) {
        l.lock();
        try{
            dischi[a-1].deallocaProcesso();
            dischi[b-1].deallocaProcesso();
            possoAllocare.signalAll();
            System.out.println("Il processo con id "+Thread.currentThread().getId()+" ha rilasciato i dischi "+a+" e "+b);
        }finally {
            l.unlock();
        }
    }//rilasciaDischi

    public static void main(String[] args){
        new ControlloreLC().test();
    }//main
}//ControlloreLC
