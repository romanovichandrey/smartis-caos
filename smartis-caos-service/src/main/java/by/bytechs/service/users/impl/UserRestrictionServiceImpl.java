package by.bytechs.service.users.impl;

import by.bytechs.repository.entity.caos.users.UserRestriction;
import by.bytechs.repository.dao.caos.users.UserRestrictionDao;
import by.bytechs.service.exceptions.ServiceException;
import by.bytechs.service.users.UserRestrictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Romanovich Andrei
 */
@Service
@Transactional
public class UserRestrictionServiceImpl implements UserRestrictionService {

    @Autowired
    private UserRestrictionDao userRestrictionDao;

    @Override
    public UserRestriction save(UserRestriction userRestriction) throws ServiceException {
        try {
            return userRestrictionDao.save(userRestriction);
        } catch (DataAccessException e) {
            throw new ServiceException(e, UserRestrictionServiceImpl.class);
        }
    }
}