package by.bytechs.service;

import by.bytechs.repository.entity.caos.Transaction;

/**
 * Created by Kotsuba_VV on 16.02.2017.
 */
public interface TransactionService {
    void saveTransaction(Transaction transaction);
}
