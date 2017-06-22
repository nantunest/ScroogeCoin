import java.security.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nelson on 6/19/17.
 */
public class Scrooge {

    public static UTXOPool utxoPool;
    public static ArrayList<Transaction> validTransactions;
    public static HashMap<PublicKey, PrivateKey> keyMap;


    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static void main(String[] args) {

        try {
            utxoPool = new UTXOPool();
            validTransactions = new ArrayList<>();
            keyMap = new HashMap<>();

            ArrayList<Transaction> txToValidate = new ArrayList<>();

            KeyPair scroogeKp = Crypto.generateKeyPair();

            keyMap.put(scroogeKp.getPublic(), scroogeKp.getPrivate());

            createCoin(utxoPool, 10, scroogeKp);

            printUtxoPool();

            KeyPair dstKp = Crypto.generateKeyPair();
            keyMap.put(dstKp.getPublic(), dstKp.getPrivate());
            KeyPair dstKp2 = Crypto.generateKeyPair();
            keyMap.put(dstKp2.getPublic(), dstKp2.getPrivate());

            Transaction nTx = createSimpleTransaction(validTransactions.get(0).getHash(), 0,1, dstKp.getPublic(), scroogeKp.getPrivate());
            Transaction nTx2 = createSimpleTransaction(nTx.getHash(), 0,1, dstKp2.getPublic(), dstKp.getPrivate());


            txToValidate.add(nTx);
            txToValidate.add(nTx2);

            TxHandler txHandler = new TxHandler(utxoPool);

            Transaction [] txarr = txHandler.handleTxs(txToValidate.toArray(new Transaction[txToValidate.size()]));

            utxoPool = txHandler.utxoPool;

            printUtxoPool();

            System.out.println("tx: " + txarr.length);

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public static void createCoin(UTXOPool utxoPool, float value, KeyPair scroogeKp) {

        try {

            Transaction newTx = new Transaction();

            newTx.addOutput(value, scroogeKp.getPublic());
            newTx.addOutput(value, scroogeKp.getPublic());

            newTx.finalize();

            UTXO nUtxo = new UTXO(newTx.getHash(), 0);
            UTXO nUtxo2 = new UTXO(newTx.getHash(), 1);


            utxoPool.addUTXO(nUtxo, newTx.getOutput(0));
            utxoPool.addUTXO(nUtxo2, newTx.getOutput(1));

            validTransactions.add(newTx);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Transaction createSimpleTransaction(byte[] prevTxHash, int prevOutputIdx, double value, PublicKey pk, PrivateKey privateKey) {
        Transaction nTx = new Transaction();

        nTx.addInput(prevTxHash, prevOutputIdx);
        nTx.addOutput(value, pk);

        Signature sig = null;
        try {
            sig = Signature.getInstance("SHA256withRSA");
            sig.initSign(privateKey);
            sig.update(nTx.getRawDataToSign(0));
            nTx.addSignature(sig.sign(), 0);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }


        nTx.finalize();

        return nTx;
    }

    public static void printUtxoPool() {

        System.out.println("#---- UTXO POOL ----#");

        for (UTXO utxo : utxoPool.getAllUTXO()) {
            System.out.println("hash: " + bytesToHex(utxo.getTxHash()));
            System.out.println("index: " + utxo.getIndex());
        }
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}
