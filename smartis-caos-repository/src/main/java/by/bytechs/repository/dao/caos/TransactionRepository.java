package by.bytechs.repository.dao.caos;

import by.bytechs.repository.entity.caos.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Kotsuba_VV on 16.02.2017.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
