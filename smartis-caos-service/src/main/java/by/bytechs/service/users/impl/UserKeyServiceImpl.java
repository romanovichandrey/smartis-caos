package by.bytechs.service.users.impl;

import by.bytechs.dto.users.UserKeyDto;
import by.bytechs.repository.entity.caos.users.User;
import by.bytechs.repository.entity.caos.users.UserKey;
import by.bytechs.repository.dao.caos.users.UserKeyDao;
import by.bytechs.service.exceptions.ServiceException;
import by.bytechs.service.users.AccountService;
import by.bytechs.service.users.UserKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Romanovich Andrei
 */
@Service
@Transactional
public class UserKeyServiceImpl implements UserKeyService {
    @Autowired
    private UserKeyDao userKeyDao;
    @Autowired
    private AccountService accountService;

    @Override
    public UserKeyDto entityToDto(UserKey userKey) throws ServiceException {
        try {
            return new UserKeyDto(userKey.getKeyId(), userKey.getAccount().getId(), userKey.getAccount().getAccountNumber(),
                    userKey.getAccount().getAccountCurrency());
        } catch (NullPointerException e) {
            throw new ServiceException(e, UserKeyServiceImpl.class);
        }
    }

    @Override
    public List<UserKey> saveAll(List<UserKeyDto> userKeyDtoList, User user) throws ServiceException {
        try {
            List<UserKey> resultList = new ArrayList<>();
            for (UserKeyDto userKeyDto : userKeyDtoList) {
                UserKey userKey = new UserKey();
                userKey.setUser(user);
                userKey.setKeyId(userKeyDto.getKeyId());
                userKey.setDisabled(userKeyDto.isDisabled());
                userKey.setAccount(accountService.findById(userKeyDto.getAccountId()));
                userKey = userKeyDao.save(userKey);
                resultList.add(userKey);
            }
            return resultList;
        } catch (DataAccessException e) {
            throw new ServiceException(e, UserKeyServiceImpl.class);
        }
    }
}