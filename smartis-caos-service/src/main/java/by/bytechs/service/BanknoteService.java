package by.bytechs.service;

import by.bytechs.dto.BanknoteModuleDTO;
import by.bytechs.repository.entity.caos.BanknoteModule;

/**
 * Created by Kotsuba_VV on 23.01.2017.
 */
public interface BanknoteService extends Service<BanknoteModule, BanknoteModuleDTO>, DeviceService<BanknoteModule> {
    void saveBanknoteModule(BanknoteModuleDTO dto);
    BanknoteModuleDTO getBanknoteModuleByTerminal(String terminalID);
}
