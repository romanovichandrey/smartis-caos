package by.bytechs.service.impl;

import by.bytechs.dto.CashUnitDTO;
import by.bytechs.repository.dao.caos.CashUnitRepository;
import by.bytechs.repository.entity.caos.BanknoteModule;
import by.bytechs.repository.entity.caos.CashUnit;
import by.bytechs.service.CashUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Kotsuba_VV on 24.01.2017.
 */

@Service
@Transactional
public class CashUnitServiceImpl implements CashUnitService {

    @Autowired
    private CashUnitRepository cashUnitRepository;

    @Override
    public void deleteAllByBDM(BanknoteModule banknoteModule) {
        cashUnitRepository.deleteAllByBanknoteModule(banknoteModule);
    }

    @Override
    public CashUnitDTO entityToDTO(CashUnit entity) {
        CashUnitDTO dto = new CashUnitDTO();
        dto.setCurrency(entity.getCurrency());
        dto.setDenomination(entity.getDenomination());
        dto.setQuantity(entity.getQuantity());
        dto.setType(entity.getType());
        dto.setModifyDate(entity.getModifyDate());
        return dto;
    }

    @Override
    public CashUnit dtoToEntity(CashUnitDTO dto) {
        CashUnit header = new CashUnit();

        return header;
    }


}
