package by.bytechs.ui.restService.interfaces;

import by.bytechs.dto.users.AccountDto;
import by.bytechs.dto.users.OrganizationDto;
import by.bytechs.dto.users.UserDto;
import by.bytechs.dto.users.UserTypeDto;

import java.util.List;

/**
 * <p>
 * The service sends requests for information about the users.
 *
 * @author Romanovich Andrei
 */
public interface UserRestService {

    /**
     * <p>
     * Receive all users registered in the system.
     *
     * @return list userDto
     */
    List<UserDto> getAllUsers();

    /**
     * <p>
     * Receive all organization registered in the system.
     *
     * @return list organizationDto
     */
    List<OrganizationDto> getAllOrganization();

    /**
     * <p>
     * Receive all userType registered in the system.
     *
     * @return userTypeDto
     */
    List<UserTypeDto> getAllUserType();

    /**
     * <p>
     * Add new account for organization.
     *
     * @param accountDto
     * @param organizationId
     * @return accountDto to save DB.
     */
    AccountDto addNewAccount(AccountDto accountDto, Integer organizationId);

    /**
     * <p>
     * Add new organization or update selected organization.
     *
     * @param dto
     * @return organizationDto to save or update DB.
     */
    OrganizationDto addOrUpdateOrganization(OrganizationDto dto);

    /**
     * <p>
     * Delete account.
     *
     * @param id
     * @return <code>true</code> if successfully
     */
    boolean deleteAccount(Integer id);

    /**
     * <p>
     * Added new user for system.
     *
     * @param userDto
     * @return save userDto to DB.
     */
    UserDto addNewUser(UserDto userDto);

    /**
     * <p>
     * Delete user by id.
     *
     * @param id
     * @return <code>true</code> if the deletion was successful.
     */
    boolean deleteUser(Integer id);
}