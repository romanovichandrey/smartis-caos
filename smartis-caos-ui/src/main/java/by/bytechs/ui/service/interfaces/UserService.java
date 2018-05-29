package by.bytechs.ui.service.interfaces;

import by.bytechs.dto.users.AccountDto;
import by.bytechs.dto.users.OrganizationDto;
import by.bytechs.dto.users.UserDto;
import by.bytechs.dto.users.UserTypeDto;
import javafx.collections.ObservableList;

/**
 * @author Romanovich Andrei
 */
public interface UserService {
    ObservableList<UserDto> getAllUsers();
    ObservableList<OrganizationDto> getAllOrganization();
    ObservableList<UserTypeDto> getAllUserType();
    OrganizationDto addOrUpdateOrganization(OrganizationDto dto);
    AccountDto addNewAccount(AccountDto accountDto, Integer organizationId);
    boolean deleteAccount(Integer id);
    UserDto addNewUser(UserDto userDto);
    boolean deleteUser(Integer id);

}