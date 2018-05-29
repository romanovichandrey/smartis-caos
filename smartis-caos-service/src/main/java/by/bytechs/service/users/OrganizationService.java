package by.bytechs.service.users;

import by.bytechs.dto.users.OrganizationDto;
import by.bytechs.repository.entity.caos.users.Organization;
import by.bytechs.service.exceptions.ServiceException;

import java.util.List;

/**
 * <p>
 * Interface for a business logical {@link by.bytechs.repository.entity.caos.users.Organization}.
 *
 * @author Romanovich Andrei
 */
public interface OrganizationService {

    /**
     * <p>
     * Save Organization.
     *
     * @param dto
     * @return this save Organization
     * @throws ServiceException
     */
    OrganizationDto saveOrganization(OrganizationDto dto) throws ServiceException;

    /**
     * <p>
     * Update Organization.
     *
     * @param organization
     * @return this update Organization
     * @throws ServiceException
     */
    Organization updateOrganization(Organization organization) throws ServiceException;

    /**
     * <p>
     * Converts {@link OrganizationDto} to {@link Organization}.
     *
     * @param dto
     * @return entity
     * @throws ServiceException
     */
    Organization dtoToEntity(OrganizationDto dto) throws ServiceException;

    /**
     * <p>
     * Converts {@link Organization} to {@link OrganizationDto}
     *
     * @param organization
     * @return dto
     * @throws ServiceException
     */
    OrganizationDto entityToDto(Organization organization) throws ServiceException;

    /**
     * <p>
     * Converts all {@link Organization} to {@link OrganizationDto}
     *
     * @return dto list
     * @throws ServiceException
     */
    List<OrganizationDto> entityToDto() throws ServiceException;

    /**
     * <p>
     * Find {@link Organization} by organizationId.
     *
     * @param organizationId
     * @return found organization or null if an organization with such id is not found.
     * @throws ServiceException
     */
    Organization findById(Integer organizationId) throws ServiceException;

}
