package co.istad.ite.features.specification_dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequestDto {

    String column;
    String value;
    Operation operation;
    String joinTable;
    public enum Operation {
        EQUAL,
        LIKE,
        GREATER,
        GREATER_OR_EQUAL,
        LESS,
        LESS_OR_EQUAL,
        BETWEEN,
        IN,
        NOT_EQUAL,
        IS_NULL,
        IS_NOT_NULL,
        JOIN
    }

}
