package it.einjojo.nucleoflex.economy;

public interface TransactionStorage {

    void persist(Transaction transaction);
    Transaction loadTransaction(int transactionID);

}
