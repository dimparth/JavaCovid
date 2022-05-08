package blockchain;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Block {
    private String hash;
    private final String previousHash;
    private final String data;
    private final long timeStamp;
    private int numberOnce;
    private boolean finished; //Elegxos an exei teleiosei to mining

    //telescoping constructors
    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.numberOnce = Integer.MIN_VALUE;
        this.hash = calculateHash(this.numberOnce);
    }

    public Block(String hash, String data, String previousHash, String timestamp, int nonce) {
        this.hash = hash;
        this.data = data;
        this.timeStamp = Long.parseLong(timestamp);
        this.previousHash = previousHash;
        this.numberOnce = nonce;
    }

    //euresi hash
    public String calculateHash(int nonce) {
        return StringSha.sha256(
                previousHash +
                        timeStamp +
                        nonce +
                        data
        );
    }

    //an teleiose to mining
    synchronized boolean isFinished() {
        return finished;
    }

    //i multithreaded diadikasia mining
    public void mineBlock(int prefix) {
        finished = false;
        long startTime = System.nanoTime();
        ExecutorService executor = Executors.newFixedThreadPool(3);  //Xrisi 3 thread.
        Runnable runnableTask = () -> {
            String target = new String(new char[prefix]).replace('\0', '0');
            int tnonce = this.numberOnce;    //kathe thread apothikevei to arxiko nonice kai hash
            String thash = this.hash;
            while (!thash.substring(0, prefix).equals(target) && !finished) {
                tnonce++;
                thash = calculateHash(tnonce);
            }
            finished = true;

            synchronized (this) {
                if (finished && thash.substring(0, prefix).equals(target)) {
                    this.numberOnce = tnonce;
                    this.hash = thash;
                    long endTime = System.nanoTime();
                    long convert = TimeUnit.SECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);
                    System.out.println("Latest Block Mined Successfully with hash : " + hash);
                    System.out.println("Current block's mining took: " + convert + " seconds");
                    executor.shutdown();
                }
            }
        };
        executor.execute(runnableTask);
        while (!isFinished()) {
        } // wait mexri na teleiosei to mining, oste na mh xalasei to print order
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getData() {
        return data;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getNumberOnce() {
        return numberOnce;
    }
}
