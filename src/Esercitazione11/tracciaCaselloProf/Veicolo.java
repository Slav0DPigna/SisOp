package Esercitazione11.tracciaCaselloProf;



import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Veicolo extends Thread{

	private final int minKm = 5, maxKm = 10;
	private final int minAttesa = 3, maxAttesa = 6, tempoKm = 4;

	private Random random = new Random();
	private Casello casello;

	public Veicolo(Casello c) {
		casello = c;
	}

	@Override
	public void run() {
		try {
			int km = random.nextInt(maxKm - minKm + 1) + minKm;
			// Op1
			attendi(km * tempoKm);
			// Op2 sceglie la porta
			
			int porta = random.nextInt(casello.getNumPorte());
			System.out.format("Veicolo %d si accoda sulla porta %d%n", getId(), porta);
			// Op3 si accoda sulla porta scelta
			casello.accedi(porta);
			System.out.format("Veicolo %d � entrato nella porta %d%n", getId(), porta);
			// Op4
			attendi(minAttesa, maxAttesa);
			// Op5
			casello.pagaEAbbandona(porta, km);
			System.out.format("Veicolo %d paga per %d km e abbandona la porta %d%n", getId(), km, porta);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void attendi(int min, int max) throws InterruptedException {
		TimeUnit.SECONDS.sleep(random.nextInt(max - min + 1) + min);
	}

	private void attendi(int secs) throws InterruptedException {
		TimeUnit.SECONDS.sleep(secs);
	}
}
