package org.myungkeun.crud_r2dbc_webflux_240411.Dto.registerDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.myungkeun.crud_r2dbc_webflux_240411.entities.User;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDto {
    private User user;
}
