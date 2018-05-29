package by.bytechs.service.users;

import by.bytechs.dto.users.AccountDto;
import by.bytechs.repository.entity.caos.users.Account;
import by.bytechs.service.exceptions.ServiceException;

/**
 * <p>
 * Interface for a business logical {@link by.bytechs.repository.entity.caos.users.Account}.
 *
 * @author Romanovich Andrei
 */
public interface AccountService {

    /**
     * <p>
     * Save Account.
     *
     * @param accountDto
     * @param organizationId
     * @return this save account convert to {@link AccountDto}
     * @throws ServiceException
     */
    AccountDto saveAccount(AccountDto accountDto, Integer organizationId) throws ServiceException;

    /**
     * <p>
     * Converts {@link AccountDto} to {@link Account}.
     *
     * @param dto
     * @return entity
     * @throws ServiceException
     */
    Account dtoToEntity(AccountDto dto) throws ServiceException;

    /**
     * <p>
     * Converts {@link Account} to {@link AccountDto}.
     *
     * @param account
     * @return dto
     * @throws ServiceException
     */
    AccountDto entityToDto(Account account) throws ServiceException;

    /**
     * <p>
     *     Delete account.
     * @param id
     * @return <code>true</code> if successfully
     * @throws ServiceException
     */
    boolean deleteAccount(Integer id) throws ServiceException;

    Account findById(Integer id) throws ServiceException;
}