package com.deggvelopers.pomodoro.mapper;

import com.deggvelopers.pomodoro.dto.user.UserResponse;
import com.deggvelopers.pomodoro.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    UserResponse toUserResponse(User user);
}
