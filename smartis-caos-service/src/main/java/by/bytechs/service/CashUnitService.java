package by.bytechs.service;

import by.bytechs.dto.CashUnitDTO;
import by.bytechs.repository.entity.caos.BanknoteModule;
import by.bytechs.repository.entity.caos.CashUnit;

/**
 * Created by Kotsuba_VV on 24.01.2017.
 */

public interface CashUnitService extends Service<CashUnit, CashUnitDTO> {
    void deleteAllByBDM(BanknoteModule banknoteModule);

}
