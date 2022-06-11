package Crypto1;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class SoluzioneSeminario extends Thread {
    private String pathFileDaDecifrare,pathFileDaRestituire;
    private String parolaChiave;
    private int da,a;
    private byte[] inputByte,outputByte;
    private boolean trovata=false;

    public SoluzioneSeminario(String pathFileDaDecifrare, String pathFileDaRestituire, String parolaChiave, int da, int a) {
        if (da >= a) {
            throw new IllegalArgumentException();
        } else {
            this.parolaChiave = parolaChiave;
            this.pathFileDaDecifrare = pathFileDaDecifrare;
            this.pathFileDaRestituire = pathFileDaRestituire;
            this.da = da;
            this.a = a;
            inputByte=fileToByte(pathFileDaDecifrare);
        }
    }

    /*public boolean trovaParola() {//inutilizzato
        boolean flag=false;
        BufferedReader bf;
        try {
            bf = new BufferedReader(new FileReader(this.pathFileDaRestituire));
            String linea = bf.readLine();
            while(linea != null) {
                StringTokenizer st = new StringTokenizer(linea, " ", false);
                while(st.hasMoreTokens()) {
                    String parola=st.nextToken();
                    if (parola.equalsIgnoreCase(this.parolaChiave)) {
                        return true;
                    }
                }
                linea=bf.readLine();
            }
            bf.close();
        } catch (IOException var4) {
            var4.printStackTrace();
        }
        return flag;
    }*/

    private byte[] fileToByte(String path){
       StringBuilder sr=new StringBuilder();
       BufferedReader bf;
       try {
           bf=new BufferedReader(new FileReader(path));
           String linea=bf.readLine();
           while(linea!=null) {
               sr.append(linea);
               linea=bf.readLine();
           }
           bf.close();
       }catch (IOException e){
           e.printStackTrace();
       }
       return sr.toString().getBytes();
    }//fileToByte


    public boolean getTrovata(){
        return trovata;
    }

    private String creaChiave(int i) {
        return String.format("%016d", i);
    }
    private byte[] decrypt(String key) throws CryptoException{
        if(key.length()!=16)
            throw new IllegalArgumentException("LUNGHEZZA CHIAVE SBAGLIATA");
        try {
            Key secretKey=new SecretKeySpec(key.getBytes(),"AES");
            Cipher cipher =Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,secretKey);
            byte[] outputBytes = cipher.doFinal(inputByte);
            return outputBytes;
        } catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e){
            throw new CryptoException("Error encrypting/decripting file",e);
        }
    }//decrypt

    public void run() {
        String fileDecriptato=null;
        int spazioDiRicerca=a-da;
        for (int i = da; i <= this.a && !this.trovata; i++) {
            if(this.isInterrupted())
                break;
            boolean flag=true;
            double percentuale=(100*(i-da))/spazioDiRicerca;
            String perc=String.format("%f",percentuale);
            System.out.println("Thead "+this.getName()+" "+creaChiave(i)+" percentuale di completamento "+perc);
            try {
               fileDecriptato=new String(decrypt(creaChiave(i)));
            } catch (CryptoException e) {
                e.printStackTrace();
                flag=false;
            }
            if(flag && fileDecriptato.contains(parolaChiave)) {
                try {
                    File fileRitorno = new File(this.pathFileDaRestituire);
                    if (!fileRitorno.exists())
                        fileRitorno.createNewFile();
                    PrintWriter pw = new PrintWriter(new FileWriter(pathFileDaRestituire));
                    pw.write(fileDecriptato);
                    pw.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
                trovata = true;
                break;
            }
        }
        System.out.println(this.getName()+" ha finito");
    }

    public static void main(String[] args) throws InterruptedException, CryptoException {
        CryptoUtils.encrypt(String.format("%016d", 2),new File("C:\\Users\\salva\\Desktop\\SISOP\\cacca.txt"),new File("C:\\Users\\salva\\Desktop\\SISOP\\testo1.txt"));
        long inizio = System.currentTimeMillis();

        String pathFileDaDecifrare = "C:\\Users\\salva\\Desktop\\SISOP\\testo1.txt";
        String pathFileDaRestituire = "C:\\Users\\salva\\Desktop\\SISOP\\testoRisultato";
        int numeroDiThread=0;
        int numeroChiavi=Integer.MAX_VALUE;
        for(int i=10;i<numeroChiavi;i++)
            if((numeroChiavi-1)%i==0){
                numeroDiThread=i;
                break;
            }
        SoluzioneSeminario[] s = new SoluzioneSeminario[numeroDiThread];
        int parteDaMoltiplicare = (numeroChiavi-1)/ s.length;

        for(int i = 0; i < s.length; ++i) {
            s[i] = new SoluzioneSeminario(pathFileDaDecifrare, pathFileDaRestituire + i + ".txt", "cacca", i * parteDaMoltiplicare, i * parteDaMoltiplicare + parteDaMoltiplicare);
            s[i].setName("Thread "+i);
            s[i].start();
        }

        boolean flag=true;
        while(flag){
            int value=0;
            for(int i=0;i<s.length;i++) {
                if (s[i].getTrovata()) {
                    System.out.println("File leggibile: " + s[i].pathFileDaRestituire);
                    flag = false;
                }
            }
        }

        //TimeUnit.SECONDS.sleep(1);
        long fine = System.currentTimeMillis();
        System.out.println("Tempo d'esecuzione "+(fine-inizio)/1000+" secondi");
        System.exit(0);
    }
}

