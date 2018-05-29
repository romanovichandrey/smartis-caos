package by.bytechs.repository.entity.caos;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Kotsuba_VV on 18.01.2017.
 */

@Entity
@Table(name = "IButtonReaders")
public class IButtonReader extends Device {
    public IButtonReader() {
        super();
    }
}
