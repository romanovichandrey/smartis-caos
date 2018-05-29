package by.bytechs.service.users;

import by.bytechs.repository.entity.caos.users.UserPassword;
import by.bytechs.service.exceptions.ServiceException;

/**
 * @author Romanovich Andrei
 */
public interface UserPasswordService {

    UserPassword saveOrUpdate(UserPassword userPassword) throws ServiceException;
}