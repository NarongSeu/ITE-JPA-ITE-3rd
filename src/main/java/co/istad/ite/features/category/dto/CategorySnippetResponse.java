package co.istad.ite.features.category.dto;


import lombok.Builder;

@Builder
public record CategorySnippetResponse(
        Integer id,
        String name
) {
}
