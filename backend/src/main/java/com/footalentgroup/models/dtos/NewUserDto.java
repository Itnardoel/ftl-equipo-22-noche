package com.footalentgroup.models.dtos;

import com.footalentgroup.models.enums.RoleList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {
    String name;
    String lastName;
    String email;
    String password;
    RoleList role;
}
