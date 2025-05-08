package com.noelpinto47.planora.mappers;

import org.mapstruct.Mapper;

import com.noelpinto47.planora.dto.UserDTO;
import com.noelpinto47.planora.entities.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDTO(User user);

    List<UserDTO> toUserDTOs(List<User> users);
}
