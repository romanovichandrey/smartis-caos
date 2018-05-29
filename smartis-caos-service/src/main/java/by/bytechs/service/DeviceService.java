package by.bytechs.service;

import by.bytechs.repository.entity.caos.Device;

/**
 * Created by Kotsuba_VV on 17.01.2017.
 */
public interface DeviceService<D extends Device> {
    void saveDevice(D device);
}
