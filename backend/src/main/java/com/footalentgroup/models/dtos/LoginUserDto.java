package com.footalentgroup.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {
       private String email;
       private String password;

       public String getEmail() {
              return email;
       }

       public String getPassword() {
              return password;
       }
}
