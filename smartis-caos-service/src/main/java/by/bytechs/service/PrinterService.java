package by.bytechs.service;

import by.bytechs.dto.PrinterDTO;
import by.bytechs.repository.entity.caos.Printer;

/**
 * Created by Kotsuba_VV on 23.01.2017.
 */

public interface PrinterService extends Service<Printer, PrinterDTO>, DeviceService<Printer> {
}
