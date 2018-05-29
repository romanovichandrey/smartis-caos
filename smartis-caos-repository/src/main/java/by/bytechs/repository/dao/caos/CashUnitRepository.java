package by.bytechs.repository.dao.caos;

import by.bytechs.repository.entity.caos.BanknoteModule;
import by.bytechs.repository.entity.caos.CashUnit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Kotsuba_VV on 20.01.2017.
 */
public interface CashUnitRepository extends JpaRepository<CashUnit, Long> {
    void deleteAllByBanknoteModule(BanknoteModule banknoteModule);
}
