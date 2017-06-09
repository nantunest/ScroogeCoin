public class TxHandler {

    /**
     * Creates a public ledger whose current UTXOPool (collection of unspent transaction outputs) is
     * {@code utxoPool}. This should make a copy of utxoPool by using the UTXOPool(UTXOPool uPool)
     * constructor.
     */
     UTXOPool utxoPool;

    public TxHandler(UTXOPool utxoPool) {
        // IMPLEMENT THIS
        utxoPool = UTXOPool(utxoPool);
    }

    /**
     * @return true if:
     * (1) all outputs claimed by {@code tx} are in the current UTXO pool,
     * (2) the signatures on each input of {@code tx} are valid,
     * (3) no UTXO is claimed multiple times by {@code tx},
     * (4) all of {@code tx}s output values are non-negative, and
     * (5) the sum of {@code tx}s input values is greater than or equal to the sum of its output
     *     values; and false otherwise.
     */
    public boolean isValidTx(Transaction tx) {
        // IMPLEMENT THIS

        // (1) for o in all tx.i if utxo.contains(o)
        for (Transaction.Input txIn : tx.getInputs) {
            UTXO probeUtxo(txIn.prevTxHash, txIn.outputIndex)

            if (!utxoPool.contains(probeUtxo))
                return false;

            else {
              // (2) for all i in tx.i if Crypto.verifySignature(o.pk, tx.getRawTx, i.sig)

              Transaction.Output sigOut = utxoPool.getTxOutput(probeUtxo);
              if (Crypto.verifySignature(sigOut.address, txIn.getRawTx(), tx.signature));
            }
        }



    }

    /**
     * Handles each epoch by receiving an unordered array of proposed transactions, checking each
     * transaction for correctness, returning a mutually valid array of accepted transactions, and
     * updating the current UTXO pool as appropriate.
     */
    public Transaction[] handleTxs(Transaction[] possibleTxs) {
        // IMPLEMENT THIS
    }

}
