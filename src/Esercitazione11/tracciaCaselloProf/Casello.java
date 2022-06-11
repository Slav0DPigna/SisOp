package Esercitazione11.tracciaCaselloProf;

public abstract class Casello {

	protected int incasso;
	protected int numPorte;
	protected int tariffa;

	public Casello(int numPorte, int tariffa) {
		this.incasso = 0;
		this.numPorte = numPorte;
		this.tariffa = tariffa;
	}

	public abstract void accedi(int porta) throws InterruptedException;;

	public abstract void pagaEAbbandona(int porta, int km) throws InterruptedException;
	
	public int getNumPorte(){
		return numPorte;
	}
	
	public int getIncasso(){
		return incasso;
	}
	
	public void test(int V) {
		Veicolo[] veicoli = new Veicolo[V];
		for (int i = 0; i < V; i++) {
			veicoli[i] = new Veicolo(this);
		}
		
		for (int i = 0; i < V; i++) {
			veicoli[i].start();
		}
		
		for (int i = 0; i < V; i++) {
			try {
				veicoli[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
		System.out.format("Incasso finale è	 di %d%n", getIncasso());
	}
}
