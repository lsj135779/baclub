package com.sparta.baclub;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) //parsing할때 null값은 알아서 제외
public class CommonResponseDto {
    private String msg;
    private Integer statusCode;
}
