package co.avbinvest.userservices.mapper;

import co.avbinvest.userservices.dto.request.UserRequestDTO;
import co.avbinvest.userservices.dto.response.CompanyDTO;
import co.avbinvest.userservices.dto.response.UserBasicDTO;
import co.avbinvest.userservices.dto.response.UserWithCompanyDTO;
import co.avbinvest.userservices.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User createModel(UserRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateModel(UserRequestDTO dto, @MappingTarget User entity);

    UserBasicDTO responseBasicDto(User entity);

    @Mapping(target = "companyDTO", expression = "java(company)")
    @Mapping(target = "id", source = "basicDto.id")
    UserWithCompanyDTO responseDtoWithCompany(UserBasicDTO basicDto, CompanyDTO company);
}
