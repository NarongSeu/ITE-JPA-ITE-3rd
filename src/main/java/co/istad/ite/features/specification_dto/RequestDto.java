package co.istad.ite.features.specification_dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RequestDto {
     public List<SearchRequestDto> searchRequestDto;
    public GlobalOperator globalOperator;
    public static enum GlobalOperator{
        AND,OR
    }
}
