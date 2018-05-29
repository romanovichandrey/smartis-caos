package by.bytechs.service.users;

import by.bytechs.dto.users.UserTypeDto;
import by.bytechs.repository.entity.caos.users.UserType;
import by.bytechs.service.exceptions.ServiceException;

import java.util.List;

/**
 * <p>
 * Interface for a business logical {@link by.bytechs.repository.entity.caos.users.UserType}.
 *
 * @author Romanovich Andrei
 */
public interface UserTypeService {

    /**
     * <p>
     * Converts all {@link by.bytechs.repository.entity.caos.users.UserType} to {@link UserTypeDto}.
     *
     * @return list userTypeDto
     * @throws ServiceException
     */
    List<UserTypeDto> entityToDto() throws ServiceException;

    /**
     * <p>
     * Converts {@link by.bytechs.repository.entity.caos.users.UserType} to {@link UserTypeDto}.
     *
     * @param userType
     * @return dto
     * @throws ServiceException
     */
    UserTypeDto entityToDto(UserType userType) throws ServiceException;

    UserType dtoToEntity(UserTypeDto userTypeDto) throws ServiceException;

    UserType findByTypeId(Integer typeId) throws ServiceException;
}