package com.cearsolutions.mapper;

import com.cearsolutions.dto.request.UserRequestDto;
import com.cearsolutions.dto.response.UserResponseDto;
import com.cearsolutions.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
}
