package it.einjojo.nucleoflex.economy;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TransactionLogger {
    private final Queue<Transaction> transactions = new ConcurrentLinkedQueue<>();
    private final TransactionStorage transactionStorage;

    public TransactionLogger(TransactionStorage transactionStorage) {
        this.transactionStorage = transactionStorage;
    }

    public void log(UUID playerUuid, long deltaBalance) {
        log(new Transaction(-1, playerUuid, deltaBalance, System.currentTimeMillis()));
    }

    public void log(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * Flushes the transaction log to the database.
     */
    public void flush() {
        while (!transactions.isEmpty()) {
            Transaction transaction = transactions.poll();
            transactionStorage.persist(transaction);
        }
    }

    public Transaction loadTransaction(int id) {
        return transactionStorage.loadTransaction(id);
    }

}
