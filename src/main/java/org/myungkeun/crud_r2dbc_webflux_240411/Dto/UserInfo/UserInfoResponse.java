package org.myungkeun.crud_r2dbc_webflux_240411.Dto.UserInfo;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode

public class UserInfoResponse {
    private String email;
}
