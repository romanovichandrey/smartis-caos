package by.bytechs.service.users;

import by.bytechs.dto.users.UserDto;
import by.bytechs.repository.entity.caos.users.User;
import by.bytechs.service.exceptions.ServiceException;

import java.util.List;

/**
 * <p>
 * Interface for a business logical {@link by.bytechs.repository.entity.caos.users.User}.
 *
 * @author Romanovich Andrei
 */
public interface UserService {

    /**
     * <p>
     * Converts all {@link by.bytechs.repository.entity.caos.users.User} to {@link UserDto}.
     *
     * @return list dto
     * @throws ServiceException
     */
    List<UserDto> entityToDto() throws ServiceException;

    /**
     * <p>
     * Converts {@link by.bytechs.repository.entity.caos.users.User} to {@link UserDto}.
     *
     * @param user
     * @return dto
     * @throws ServiceException
     */
    UserDto entityToDto(User user) throws ServiceException;

    /**
     * <p>
     * Save user.
     *
     * @return userDto
     * @throws ServiceException
     */
    UserDto save(UserDto userDto) throws ServiceException;

    /**
     * <p>
     * Delete user with database by userId.
     *
     * @param id
     * @return <code>true</code> if the deletion was successful.
     * @throws ServiceException
     */
    boolean delete(Integer id) throws ServiceException;
}