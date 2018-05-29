package by.bytechs.web.controllers.users;

import by.bytechs.dto.errors.ErrorResponseDto;
import by.bytechs.dto.users.AccountDto;
import by.bytechs.dto.users.OrganizationDto;
import by.bytechs.dto.users.UserDto;
import by.bytechs.dto.users.UserTypeDto;
import by.bytechs.service.exceptions.ServiceException;
import by.bytechs.service.users.AccountService;
import by.bytechs.service.users.OrganizationService;
import by.bytechs.service.users.UserService;
import by.bytechs.service.users.UserTypeService;
import by.bytechs.web.exceptions.WebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Romanovich Andrei
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private UserTypeService userTypeService;
    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET, value = "getAllUsers/")
    public List<UserDto> getAllUsers() throws WebException {
        try {
            return userService.entityToDto();
        } catch (ServiceException e) {
            throw new WebException(e, UserController.class);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "getAllOrganization/")
    public List<OrganizationDto> getAllOrganization() throws WebException {
        try {
            return organizationService.entityToDto();
        } catch (ServiceException e) {
            throw new WebException(e, UserController.class);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "getAllUserType/")
    public List<UserTypeDto> getAllUserType() throws WebException {
        try {
            return userTypeService.entityToDto();
        } catch (ServiceException e) {
            throw new WebException(e, UserController.class);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "addNewAccount/{organizationId}")
    public AccountDto addNewAccount(@RequestBody AccountDto accountDto,
                                    @PathVariable Integer organizationId) throws WebException {
        try {
            return accountService.saveAccount(accountDto, organizationId);
        } catch (ServiceException e) {
            throw new WebException(e, UserController.class);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "addOrUpdateOrganization/")
    public OrganizationDto addOrUpdateOrganization(@RequestBody OrganizationDto dto) throws WebException {
        try {
            return organizationService.saveOrganization(dto);
        } catch (ServiceException e) {
            throw new WebException(e, UserController.class);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteAccount/{id}")
    public boolean deleteAccount(@PathVariable Integer id) throws WebException {
        try {
            return accountService.deleteAccount(id);
        } catch (ServiceException e) {
            throw new WebException(e, UserController.class);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "addNewUser/")
    public UserDto addNewUser(@RequestBody UserDto userDto) throws WebException {
        try {
            return userService.save(userDto);
        } catch (ServiceException e) {
            throw new WebException(e, UserController.class);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteUser/{id}")
    public boolean deleteUser(@PathVariable Integer id) throws WebException {
        try {
            return userService.delete(id);
        } catch (ServiceException e) {
            throw new WebException(e, UserController.class);
        }
    }

    @ExceptionHandler(WebException.class)
    public ResponseEntity<ErrorResponseDto> exceptionHandler(HttpServletResponse response, WebException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setErrorCode(response.getStatus());
        errorResponse.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}