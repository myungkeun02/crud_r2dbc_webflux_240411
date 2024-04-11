package org.myungkeun.crud_r2dbc_webflux_240411.Dto.registerDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
    private String username;
    private String email;
    private String phone;
    private String password;
}
