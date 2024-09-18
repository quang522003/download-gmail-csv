package org.example.downloademailcsv.Dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResonseDto<T> {
    @Builder.Default
    private int code = 200;
    @Builder.Default
    private String message = "OK";
    private T data;

}

