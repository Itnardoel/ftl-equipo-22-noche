package com.footalentgroup.models.dtos;

import com.footalentgroup.models.entities.User;
import com.footalentgroup.models.enums.RoleList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String lastName;
    private RoleList role;

    public UserDto (User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.role = user.getRole();
    }
}
