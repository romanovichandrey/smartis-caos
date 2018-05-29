package by.bytechs.service.users.impl;

import by.bytechs.dto.users.AccountDto;
import by.bytechs.dto.users.OrganizationDto;
import by.bytechs.repository.entity.caos.users.Account;
import by.bytechs.repository.entity.caos.users.Organization;
import by.bytechs.repository.dao.caos.users.OrganizationDao;
import by.bytechs.service.exceptions.ServiceException;
import by.bytechs.service.users.AccountService;
import by.bytechs.service.users.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class implementation {@link by.bytechs.service.users.OrganizationService}
 *
 * @author Romanovich Andrei
 * @see OrganizationService
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    private OrganizationDao organizationDao;
    @Autowired
    private AccountService accountService;

    @Override
    public OrganizationDto saveOrganization(OrganizationDto dto) throws ServiceException {
        try {
            if (dto != null) {
                Organization organization = dtoToEntity(dto);
                if (organization.getId() != null) {
                    Organization entity = organizationDao.findOne(organization.getId());
                    entity.setOrganizationName(organization.getOrganizationName());
                    entity.setPhoneNumber(organization.getPhoneNumber());
                    entity.setLegalAddress(organization.getLegalAddress());
                    organization = organizationDao.save(entity);
                } else {
                    organization = organizationDao.save(organization);
                }
                return entityToDto(organization);
            }
            return null;
        } catch (DataAccessException e) {
            throw new ServiceException(e, OrganizationServiceImpl.class);
        }
    }

    @Override
    public Organization updateOrganization(Organization organization) throws ServiceException {
        try {
            return organizationDao.save(organization);
        } catch (DataAccessException | NullPointerException e) {
            throw new ServiceException(e, OrganizationServiceImpl.class);
        }
    }

    @Override
    public Organization dtoToEntity(OrganizationDto dto) throws ServiceException {
        try {
            Organization organization = new Organization();
            organization.setId(dto.getId());
            organization.setLegalAddress(dto.getLegalAddress());
            organization.setOrganizationName(dto.getName());
            organization.setPhoneNumber(dto.getPhoneNumber());
            return organization;
        } catch (NullPointerException e) {
            throw new ServiceException(e, OrganizationServiceImpl.class);
        }
    }

    @Override
    public OrganizationDto entityToDto(Organization organization) throws ServiceException {
        try {
            OrganizationDto dto = new OrganizationDto(organization.getId(), organization.getLegalAddress(),
                    organization.getOrganizationName(), organization.getPhoneNumber());
            List<AccountDto> accountDtoList = new ArrayList<>();
            dto.setAccountDtoList(accountDtoList);
            if (organization.getAccountList() != null && !organization.getAccountList().isEmpty()) {
                for (Account account : organization.getAccountList()) {
                    accountDtoList.add(accountService.entityToDto(account));
                }
            }
            return dto;
        } catch (NullPointerException e) {
            throw new ServiceException(e, OrganizationServiceImpl.class);
        }
    }

    @Override
    public List<OrganizationDto> entityToDto() throws ServiceException {
        try {
            List<Organization> organizationList = organizationDao.findAll();
            List<OrganizationDto> dtoList = new ArrayList<>();
            for (Organization organization : organizationList) {
                OrganizationDto dto = new OrganizationDto(organization.getId(), organization.getLegalAddress(),
                        organization.getOrganizationName(), organization.getPhoneNumber());
                List<AccountDto> accountDtoList = new ArrayList<>();
                dto.setAccountDtoList(accountDtoList);
                if (organization.getAccountList() != null && !organization.getAccountList().isEmpty()) {
                    for (Account account : organization.getAccountList()) {
                        accountDtoList.add(accountService.entityToDto(account));
                    }
                }
                dtoList.add(dto);
            }
            return dtoList;
        } catch (DataAccessException | NullPointerException e) {
            throw new ServiceException(e, OrganizationServiceImpl.class);
        }
    }

    @Override
    public Organization findById(Integer organizationId) throws ServiceException {
        try {
            return organizationDao.findOne(organizationId);
        } catch (DataAccessException | NullPointerException e) {
            throw new ServiceException(e, OrganizationServiceImpl.class);
        }
    }
}
