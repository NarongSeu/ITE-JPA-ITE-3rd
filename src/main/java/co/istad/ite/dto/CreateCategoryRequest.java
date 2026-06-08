package co.istad.ite.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
public record CreateCategoryRequest(

        @NotBlank
        @Size(max = 50)
        String name,
        @NotBlank
        @Size(max = 250)
        String description,
        String icon,
        @Positive
        @Nullable
        Integer parentCategoryId
) {
}
