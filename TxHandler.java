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

        ArrayList<UTXO> claimedUtxos;

        float sumOfInputs = 0; // sum of outputs claimed by inputs
        float sumOfOutputs = 0;

        // (1) all outputs claimed by {@code tx} are in the current UTXO pool,
        // for o in all tx.i if utxo.contains(o)
        // Outputs claimed by transactions are outputs in the UTXO referenced by transactions inputs

        // for all transaction inputs
        for (Transaction.Input txIn : tx.getInputs()) {

            // Construct a claimedUtxo to test "contains" in the UTXOPool
            UTXO claimedUtxo(txIn.prevTxHash, txIn.outputIndex)

            // If not every utxo referenced by an input in the transaction is in the UTXOPool, "isValidTx" fails
            if (!utxoPool.contains(claimedUtxo))
                return false;

            // (2) the signatures on each input of {@code tx} are valid,
            // for all i in tx.i if Crypto.verifySignature(o.pk, tx.getRawTx, i.sig)

            // Get the transaction that generated the claimed utxo
            Transaction.Output claimedOutput = utxoPool.getTxOutput(claimedUtxo);

            // Verify signature
            if (!Crypto.verifySignature(claimedOutput.address, txIn.(txIn.getInputs.indexOf(txIn)), tx.signature))
                return false;

            // (3) no UTXO is claimed multiple times by {@code tx}
            if(claimedUtxos.contains(claimedUtxo))
              return false;

            // add claimedOutput value to the amount of input values to this transaction
            sumOfInputs += claimedOutput.value;

            // add claimedUtxo to the claimed utxo list for the next inputs to
            // verify diouble spending within this transaction
            claimedUtxos.add(claimedUtxo);
        }


        // (4) all of {@code tx}s output values are non-negative
        for (Transaction.Output txOut : tx.getOutputs()) {

          if(txOut.value < 0) {
            return false;

          sumOfOutputs += txOut.value;

        }

        // (5) the sum of {@code tx}s input values is greater than or equal to the sum of its output
        // values; and false otherwise.
        if(!(sumOfInputs >= sumOfOutputs))
          return false;

        return true;

    }

    /**
     * Handles each epoch by receiving an unordered array of proposed transactions, checking each
     * transaction for correctness, returning a mutually valid array of accepted transactions, and
     * updating the current UTXO pool as appropriate.
     */
    public Transaction[] handleTxs(Transaction[] possibleTxs) {
        // IMPLEMENT THIS

        // validate each transaction individually

        // create a temporary utxo pool
    }

}
