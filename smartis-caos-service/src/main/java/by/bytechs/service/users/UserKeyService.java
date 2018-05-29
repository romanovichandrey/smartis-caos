package by.bytechs.service.users;

import by.bytechs.dto.users.UserKeyDto;
import by.bytechs.repository.entity.caos.users.User;
import by.bytechs.repository.entity.caos.users.UserKey;
import by.bytechs.service.exceptions.ServiceException;

import java.util.List;

/**
 * <p>
 * Interface for a business logical {@link by.bytechs.repository.entity.caos.users.UserKey}.
 *
 * @author Romanovich Andrei
 */
public interface UserKeyService {

    /**
     * <p>
     * Converts {@link UserKey} to {@link UserKeyDto}.
     *
     * @param userKey
     * @return dto
     * @throws ServiceException
     */
    UserKeyDto entityToDto(UserKey userKey) throws ServiceException;

    List<UserKey> saveAll(List<UserKeyDto> userKeyDtoList, User user) throws ServiceException;
}