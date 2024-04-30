package com.cearsolutions.mapper;

import com.cearsolutions.dto.request.UpdateUserRequestDto;
import com.cearsolutions.dto.request.UserRequestDto;
import com.cearsolutions.dto.response.UserResponseDto;
import com.cearsolutions.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * {@link UserMapper}
 *
 * @author Dmytro Trotsenko on 4/27/24
 */

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserResponseDto toResponseDto(User user);

  @Mapping(target = "id", ignore = true)
  User toEntity(UserRequestDto userRequestDto);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(@MappingTarget User user, UpdateUserRequestDto userRequestDto);
}
