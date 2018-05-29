package by.bytechs.ui.service;

import by.bytechs.dto.users.AccountDto;
import by.bytechs.dto.users.OrganizationDto;
import by.bytechs.dto.users.UserDto;
import by.bytechs.dto.users.UserTypeDto;
import by.bytechs.ui.restService.interfaces.UserRestService;
import by.bytechs.ui.service.interfaces.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Romanovich Andrei
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRestService userRestService;

    @Override
    public ObservableList<UserDto> getAllUsers() {
        List<UserDto> userDtoList = userRestService.getAllUsers();
        if (userDtoList != null) {
            ObservableList<UserDto> resultList = FXCollections.observableArrayList(userDtoList);
            return resultList;
        }
        return FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<OrganizationDto> getAllOrganization() {
        List<OrganizationDto> organizationDtoList = userRestService.getAllOrganization();
        OrganizationDto tempDto = new OrganizationDto();
        tempDto.setName("Добавить новую организацию");
        tempDto.setId(-1);
        if (organizationDtoList != null) {
            organizationDtoList.add(0, tempDto);
        } else {
            organizationDtoList = new ArrayList<>();
            organizationDtoList.add(tempDto);
        }
        return FXCollections.observableArrayList(organizationDtoList);
    }

    @Override
    public ObservableList<UserTypeDto> getAllUserType() {
        List<UserTypeDto> userTypeDtoList = userRestService.getAllUserType();
        if (userTypeDtoList != null) {
            return FXCollections.observableArrayList(userTypeDtoList);
        }
        return FXCollections.observableArrayList();
    }

    @Override
    public OrganizationDto addOrUpdateOrganization(OrganizationDto dto) {
        return userRestService.addOrUpdateOrganization(dto);
    }

    @Override
    public AccountDto addNewAccount(AccountDto accountDto, Integer organizationId) {
        return userRestService.addNewAccount(accountDto, organizationId);
    }

    @Override
    public boolean deleteAccount(Integer id) {
        return userRestService.deleteAccount(id);
    }

    @Override
    public UserDto addNewUser(UserDto userDto) {
        return userRestService.addNewUser(userDto);
    }

    @Override
    public boolean deleteUser(Integer id) {
        return userRestService.deleteUser(id);
    }
}