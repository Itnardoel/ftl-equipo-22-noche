package com.footalentgroup.models.dtos;

import com.footalentgroup.models.enums.RoleList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {
    String name;
    String lastName;
    String email;
    String password;
    RoleList role;

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public RoleList getRole() {
        return role;
    }
}
