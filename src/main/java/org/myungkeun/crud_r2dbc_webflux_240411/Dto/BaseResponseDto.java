package org.myungkeun.crud_r2dbc_webflux_240411.Dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

public class BaseResponseDto<T> {
    private int statusCode;
    private String message;
    private T data;
    private List<String> error;
}
