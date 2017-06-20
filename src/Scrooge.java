import java.security.KeyPair;

/**
 * Created by nelson on 6/19/17.
 */
public class Scrooge {


    public static void main(String[] args) {

        UTXOPool utxoPool = new UTXOPool();

    }


    public static void createCoin(UTXOPool utxoPool, float value) {

        try {
            KeyPair newKp = Crypto.generateKeyPair();

            Transaction newTx = new Transaction();

            newTx.addOutput(value, newKp.getPublic());

            newTx.finalize();

            UTXO nUtxo = new UTXO(newTx.getHash(), 0);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
