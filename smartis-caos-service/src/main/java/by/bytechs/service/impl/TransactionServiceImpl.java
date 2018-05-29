package by.bytechs.service.impl;

import by.bytechs.repository.dao.caos.TransactionRepository;
import by.bytechs.repository.entity.caos.Transaction;
import by.bytechs.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Kotsuba_VV on 16.02.2017.
 */

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
