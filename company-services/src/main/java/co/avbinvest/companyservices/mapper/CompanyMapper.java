package co.avbinvest.companyservices.mapper;

import co.avbinvest.companyservices.dto.request.CompanyRequestDTO;
import co.avbinvest.companyservices.dto.response.CompanyBasicDTO;
import co.avbinvest.companyservices.dto.response.CompanyWithUsersDTO;
import co.avbinvest.companyservices.dto.response.UserDTO;
import co.avbinvest.companyservices.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    @Mapping(target = "id", ignore = true)
    Company createModel(CompanyRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateModel(CompanyRequestDTO dto, @MappingTarget Company entity);

    CompanyBasicDTO responseBasicDto(Company entity);

    @Mapping(target = "users", expression = "java(users)")
    CompanyWithUsersDTO responseDtoWithUsers(CompanyBasicDTO basicDto, List<UserDTO> users);
}
