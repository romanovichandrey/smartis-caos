package by.bytechs.service.users.impl;

import by.bytechs.repository.entity.caos.users.UserPassword;
import by.bytechs.repository.dao.caos.users.UserPasswordDao;
import by.bytechs.service.exceptions.ServiceException;
import by.bytechs.service.users.UserPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Romanovich Andrei
 */
@Service
@Transactional
public class UserPasswordServiceImpl implements UserPasswordService {

    @Autowired
    private UserPasswordDao userPasswordDao;

    @Override
    public UserPassword saveOrUpdate(UserPassword userPassword) throws ServiceException {
        try {
            return userPasswordDao.save(userPassword);
        } catch (DataAccessException e) {
            throw new ServiceException(e, UserPasswordServiceImpl.class);
        }
    }
}