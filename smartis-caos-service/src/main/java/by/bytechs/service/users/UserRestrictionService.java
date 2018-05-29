package by.bytechs.service.users;

import by.bytechs.repository.entity.caos.users.UserRestriction;
import by.bytechs.service.exceptions.ServiceException;

/**
 * @author Romanovich Andrei
 */
public interface UserRestrictionService {
    UserRestriction save(UserRestriction userRestriction) throws ServiceException;
}