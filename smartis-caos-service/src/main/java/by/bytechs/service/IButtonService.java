package by.bytechs.service;

import by.bytechs.dto.IButtonReaderDTO;
import by.bytechs.repository.entity.caos.IButtonReader;

/**
 * Created by Kotsuba_VV on 23.01.2017.
 */

public interface IButtonService extends Service<IButtonReader, IButtonReaderDTO>, DeviceService<IButtonReader> {
}
