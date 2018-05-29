package by.bytechs.service.users.impl;

import by.bytechs.dto.users.AccountDto;
import by.bytechs.dto.users.UserKeyDto;
import by.bytechs.repository.entity.caos.users.Account;
import by.bytechs.repository.entity.caos.users.Organization;
import by.bytechs.repository.entity.caos.users.UserKey;
import by.bytechs.repository.dao.caos.users.AccountDao;
import by.bytechs.service.exceptions.ServiceException;
import by.bytechs.service.users.AccountService;
import by.bytechs.service.users.OrganizationService;
import by.bytechs.service.users.UserKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class implementation {@link by.bytechs.service.users.AccountService}
 *
 * @author Romanovich Andrei
 * @see AccountService
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private UserKeyService userKeyService;

    @Override
    public AccountDto saveAccount(AccountDto accountDto, Integer organizationId) throws ServiceException {
        try {
            if (organizationId != null) {
                Organization organization = organizationService.findById(organizationId);
                if (organization != null && accountDto != null) {
                    Account account = dtoToEntity(accountDto);
                    account.setOrganization(organization);
                    account = accountDao.save(account);
                    organization.getAccountList().add(account);
                    organizationService.updateOrganization(organization);
                    return entityToDto(account);
                }
            }
            return null;
        } catch (DataAccessException | NullPointerException e) {
            throw new ServiceException(e, AccountServiceImpl.class);
        }
    }

    @Override
    public Account dtoToEntity(AccountDto dto) throws ServiceException {
        try {
            if (dto != null) {
                Account account = new Account();
                account.setId(dto.getId());
                account.setAccountCurrency(dto.getAccountCurrency());
                account.setAccountNumber(dto.getAccountNumber());
                return account;
            }
            return null;
        } catch (NullPointerException e) {
            throw new ServiceException(e, AccountServiceImpl.class);
        }
    }

    @Override
    public AccountDto entityToDto(Account account) throws ServiceException {
        try {
            if (account != null) {
                AccountDto dto = new AccountDto(account.getId(), account.getAccountNumber(), account.getAccountCurrency());
                if (account.getUserKeyList() != null && !account.getUserKeyList().isEmpty()) {
                    List<UserKeyDto> userKeyDtoList = new ArrayList<>();
                    dto.setUserKeyDtoList(userKeyDtoList);
                    for (UserKey userKey : account.getUserKeyList()) {
                        UserKeyDto userKeyDto = userKeyService.entityToDto(userKey);
                        userKeyDtoList.add(userKeyDto);
                    }
                }
                return dto;
            }
            return null;
        } catch (DataAccessException | NullPointerException e) {
            throw new ServiceException(e, AccountServiceImpl.class);
        }
    }

    @Override
    public boolean deleteAccount(Integer id) throws ServiceException {
        try {
            accountDao.delete(id);
            return true;
        } catch (DataAccessException e) {
            throw new ServiceException(e, AccountServiceImpl.class);
        }
    }

    @Override
    public Account findById(Integer id) throws ServiceException {
        try {
            return accountDao.findOne(id);
        } catch (DataAccessException | NullPointerException e) {
            throw new ServiceException(e, AccountServiceImpl.class);
        }
    }
}