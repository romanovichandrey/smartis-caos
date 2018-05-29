package by.bytechs.ui.restService;

import by.bytechs.dto.users.AccountDto;
import by.bytechs.dto.users.OrganizationDto;
import by.bytechs.dto.users.UserDto;
import by.bytechs.dto.users.UserTypeDto;
import by.bytechs.ui.restService.interfaces.UserRestService;
import by.bytechs.ui.restService.utils.RestUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @author Romanovich Andrei
 */
@Service
public class UserRestServiceImpl implements UserRestService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private String getAllUsers;
    @Autowired
    private String getAllOrganization;
    @Autowired
    private String getAllUserType;
    @Autowired
    private String addNewAccount;
    @Autowired
    private String addOrUpdateOrganization;
    @Autowired
    private String deleteAccount;
    @Autowired
    private String addNewUser;
    @Autowired
    private String deleteUser;
    private final Logger errorLogger = LogManager.getLogger(TerminalRestServiceImpl.class);

    @Override
    public List<UserDto> getAllUsers() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", "application/json;charset=utf-8");
            HttpEntity<String> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(getAllUsers, HttpMethod.GET,
                    httpEntity, String.class);
            TypeReference<List<UserDto>> typeReference = new TypeReference<List<UserDto>>() {
            };
            RestUtil<List<UserDto>> restUtil = new RestUtil<>();
            return restUtil.getResultActionCollection(responseEntity, typeReference);
        } catch (RestClientException e) {
            StackTraceElement[] stacks = e.getStackTrace();
            errorLogger.error(Arrays.toString(stacks));
        }
        return null;
    }

    @Override
    public List<OrganizationDto> getAllOrganization() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", "application/json;charset=utf-8");
            HttpEntity<String> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(getAllOrganization, HttpMethod.GET,
                    httpEntity, String.class);
            TypeReference<List<OrganizationDto>> typeReference = new TypeReference<List<OrganizationDto>>() {
            };
            RestUtil<List<OrganizationDto>> restUtil = new RestUtil<>();
            return restUtil.getResultActionCollection(responseEntity, typeReference);
        } catch (RestClientException e) {
            StackTraceElement[] stacks = e.getStackTrace();
            errorLogger.error(Arrays.toString(stacks));
        }
        return null;
    }

    @Override
    public List<UserTypeDto> getAllUserType() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", "application/json;charset=utf-8");
            HttpEntity<String> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(getAllUserType, HttpMethod.GET,
                    httpEntity, String.class);
            TypeReference<List<UserTypeDto>> typeReference = new TypeReference<List<UserTypeDto>>() {
            };
            RestUtil<List<UserTypeDto>> restUtil = new RestUtil<>();
            return restUtil.getResultActionCollection(responseEntity, typeReference);
        } catch (RestClientException e) {
            StackTraceElement[] stacks = e.getStackTrace();
            errorLogger.error(Arrays.toString(stacks));
        }
        return null;
    }

    @Override
    public AccountDto addNewAccount(AccountDto accountDto, Integer organizationId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", "application/json;charset=utf-8");
            HttpEntity<AccountDto> httpEntity = new HttpEntity<>(accountDto, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(addNewAccount, HttpMethod.PUT,
                    httpEntity, String.class, organizationId);
            RestUtil<AccountDto> restUtil = new RestUtil<>();
            return restUtil.getResultActionObject(responseEntity, AccountDto.class);
        } catch (RestClientException e) {
            StackTraceElement[] stacks = e.getStackTrace();
            errorLogger.error(Arrays.toString(stacks));
        }
        return null;
    }

    @Override
    public OrganizationDto addOrUpdateOrganization(OrganizationDto dto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", "application/json;charset=utf-8");
            HttpEntity<OrganizationDto> httpEntity = new HttpEntity<>(dto, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(addOrUpdateOrganization, HttpMethod.PUT,
                    httpEntity, String.class);
            RestUtil<OrganizationDto> restUtil = new RestUtil<>();
            return restUtil.getResultActionObject(responseEntity, OrganizationDto.class);
        } catch (RestClientException e) {
            StackTraceElement[] stacks = e.getStackTrace();
            errorLogger.error(Arrays.toString(stacks));
        }
        return null;
    }

    @Override
    public boolean deleteAccount(Integer id) {
        return overallMethodForDeleteEntity(id, deleteAccount);
    }

    @Override
    public UserDto addNewUser(UserDto userDto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", "application/json;charset=utf-8");
            HttpEntity<UserDto> httpEntity = new HttpEntity<>(userDto, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(addNewUser, HttpMethod.PUT,
                    httpEntity, String.class);
            RestUtil<UserDto> restUtil = new RestUtil<>();
            return restUtil.getResultActionObject(responseEntity, UserDto.class);
        } catch (RestClientException e) {
            StackTraceElement[] stacks = e.getStackTrace();
            errorLogger.error(Arrays.toString(stacks));
        }
        return null;
    }

    @Override
    public boolean deleteUser(Integer id) {
        return overallMethodForDeleteEntity(id, deleteUser);
    }

    private boolean overallMethodForDeleteEntity(Integer id, String pathURL) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", "application/json;charset=utf-8");
            HttpEntity<String> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(pathURL, HttpMethod.DELETE,
                    httpEntity, String.class, id);
            RestUtil<Boolean> restUtil = new RestUtil<>();
            return restUtil.getResultActionObject(responseEntity, Boolean.class);
        } catch (RestClientException e) {
            StackTraceElement[] stacks = e.getStackTrace();
            errorLogger.error(Arrays.toString(stacks));
        }
        return false;
    }
}