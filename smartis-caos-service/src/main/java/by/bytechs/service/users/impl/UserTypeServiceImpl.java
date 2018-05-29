package by.bytechs.service.users.impl;

import by.bytechs.dto.users.UserTypeDto;
import by.bytechs.repository.entity.caos.users.UserType;
import by.bytechs.repository.dao.caos.users.UserTypeDao;
import by.bytechs.service.exceptions.ServiceException;
import by.bytechs.service.users.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class implementation {@link by.bytechs.service.users.UserTypeService}
 *
 * @author Romanovich Andrei
 * @see UserTypeService
 */
@Service
@Transactional
public class UserTypeServiceImpl implements UserTypeService {
    @Autowired
    private UserTypeDao userTypeDao;

    @Override
    public List<UserTypeDto> entityToDto() throws ServiceException {
        try {
            List<UserType> userTypeList = userTypeDao.findAll();
            if (userTypeList != null) {
                List<UserTypeDto> dtoList = new ArrayList<>();
                for (UserType userType : userTypeList) {
                    UserTypeDto dto = new UserTypeDto(userType.getTypeId(), userType.getTypeName());
                    dtoList.add(dto);
                }
                return dtoList;
            }
            return null;
        } catch (DataAccessException | NullPointerException e) {
            throw new ServiceException(e, UserTypeServiceImpl.class);
        }
    }

    @Override
    public UserTypeDto entityToDto(UserType userType) throws ServiceException {
        try {
            UserTypeDto dto = new UserTypeDto(userType.getTypeId(), userType.getTypeName());
            return dto;
        } catch (NullPointerException e) {
            throw new ServiceException(e, UserTypeServiceImpl.class);
        }
    }

    @Override
    public UserType dtoToEntity(UserTypeDto userTypeDto) throws ServiceException {
        try {
            UserType userType = new UserType();
            userType.setTypeId(userTypeDto.getTypeId());
            userType.setTypeName(userTypeDto.getTypeName());
            return userType;
        } catch (NullPointerException e) {
            throw new ServiceException(e, UserTypeServiceImpl.class);
        }
    }

    @Override
    public UserType findByTypeId(Integer typeId) throws ServiceException {
        try {
            return userTypeDao.findOne(typeId);
        } catch (DataAccessException | NullPointerException e) {
            throw new ServiceException(e, UserTypeServiceImpl.class);
        }
    }
}