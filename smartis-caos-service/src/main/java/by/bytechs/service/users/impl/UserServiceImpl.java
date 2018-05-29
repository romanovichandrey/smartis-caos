package by.bytechs.service.users.impl;

import by.bytechs.dto.users.UserDto;
import by.bytechs.dto.users.UserKeyDto;
import by.bytechs.repository.entity.caos.users.User;
import by.bytechs.repository.entity.caos.users.UserKey;
import by.bytechs.repository.entity.caos.users.UserPassword;
import by.bytechs.repository.entity.caos.users.UserRestriction;
import by.bytechs.repository.dao.caos.users.UserDao;
import by.bytechs.service.exceptions.ServiceException;
import by.bytechs.service.users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class implementation {@link by.bytechs.service.users.UserService}
 *
 * @author Romanovich Andrei
 * @see UserService
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserKeyService userKeyService;
    @Autowired
    private UserTypeService userTypeService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private AccountService accountService;

    @Override
    public List<UserDto> entityToDto() throws ServiceException {
        try {
            List<User> userList = userDao.findAll();
            if (userList != null) {
                List<UserDto> userDtoList = new ArrayList<>();
                for (User user : userList) {
                    UserDto dto = entityToDto(user);
                    userDtoList.add(dto);
                }
                return userDtoList;
            }
            return null;
        } catch (DataAccessException | NullPointerException e) {
            throw new ServiceException(e, UserServiceImpl.class);
        }
    }

    @Override
    public UserDto entityToDto(User user) throws ServiceException {
        try {
            UserDto dto = new UserDto();
            dto.setId(user.getId());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setMiddleName(user.getMiddleName());
            dto.setPasswordChangeDate((user.getUserPassword().getPasswordChangeDate()));
            dto.setPasswordIsChange(user.getUserRestriction().isPasswordIsChange());
            if (user.getUserKeyList() != null) {
                List<UserKeyDto> userKeyDtoList = new ArrayList<>();
                dto.setUserKeyDtoList(userKeyDtoList);
                for (UserKey userKey : user.getUserKeyList()) {
                    UserKeyDto userKeyDto = userKeyService.entityToDto(userKey);
                    userKeyDtoList.add(userKeyDto);
                }
            }
            if (user.getUserType() != null) {
                dto.setUserTypeDto(userTypeService.entityToDto(user.getUserType()));
            }
            if (user.getOrganization() != null) {
                dto.setOrganizationDto(organizationService.entityToDto(user.getOrganization()));
            }
            return dto;
        } catch (NullPointerException e) {
            throw new ServiceException(e, UserServiceImpl.class);
        }
    }

    @Override
    public UserDto save(UserDto userDto) throws ServiceException {
        try {
            User user = new User();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setMiddleName(userDto.getMiddleName());
            UserRestriction userRestriction = new UserRestriction();
            userRestriction.setUser(user);
            userRestriction.setPasswordIsChange(userDto.isPasswordIsChange());
            user.setUserRestriction(userRestriction);
            UserPassword userPassword = new UserPassword();
            userPassword.setUser(user);
            userPassword.setPasswordHash(userDto.getUserPasswordHash());
            userPassword.setPasswordChangeDate(userDto.getPasswordChangeDate());
            user.setUserPassword(userPassword);
            user.setUserType(userTypeService.findByTypeId(userDto.getUserTypeDto().getTypeId()));
            user.setOrganization(organizationService.findById(userDto.getOrganizationDto().getId()));
            user = userDao.save(user);
            user.setUserKeyList(userKeyService.saveAll(userDto.getUserKeyDtoList(), user));
            return entityToDto(user);
        } catch (DataAccessException | NullPointerException e) {
            throw new ServiceException(e, UserServiceImpl.class);
        }
    }

    @Override
    public boolean delete(Integer id) throws ServiceException {
        try {
            userDao.delete(id);
            return true;
        } catch (DataAccessException | NullPointerException e) {
            throw new ServiceException(e, UserServiceImpl.class);
        }
    }
}