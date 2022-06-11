package Esercitazione6;

public class ContoCorrenteTest {
    public static void main(String[] args) throws InterruptedException{
        int depositoIniziale=100_000;
        ContoCorrente cc=new ContoCorrenteAI(depositoIniziale);
        int numeroCorrentisti=2000;
        int importo=100;
        int numOperazioni=100;
        testContoCorrente(cc,numeroCorrentisti,importo,numOperazioni);

        if(cc.getDeposito()==depositoIniziale){
            System.out.format("Corretto! il deposito finale é %s%n",cc.getDeposito());
        }else {
            System.out.format("Errore! Il deposito iniziale era di %s, il deposito " +
                    "finale é di %s%n",depositoIniziale,cc.getDeposito());
        }
    }//main

    public static void testContoCorrente(ContoCorrente cc,int numeroCorrentisti,int importo, int numOperazioni)
            throws InterruptedException{
        Correntista [] correntisti=new Correntista[numeroCorrentisti];
        for(int i=0;i<numeroCorrentisti;i++){
            correntisti[i]=new Correntista(cc,importo,numOperazioni);
        }

        Thread threadCorrentisti[]=new Thread[numeroCorrentisti];
        for(int i=0;i<numeroCorrentisti;i++){
            threadCorrentisti[i]=new Thread(correntisti[i]);
            threadCorrentisti[i].setName("Thread "+i);
            threadCorrentisti[i].start();
        }
        for(int i=0;i<numeroCorrentisti;i++){
            threadCorrentisti[i].join();
        }
    }//testContoCorrente
}//ContoCorrenteTest
