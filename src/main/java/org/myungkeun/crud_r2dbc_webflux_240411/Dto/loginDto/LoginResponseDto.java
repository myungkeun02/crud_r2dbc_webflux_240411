package org.myungkeun.crud_r2dbc_webflux_240411.Dto.loginDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String accessToken;
}
