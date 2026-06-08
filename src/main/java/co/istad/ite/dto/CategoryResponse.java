package co.istad.ite.dto;

public record CategoryResponse(
        Integer id,
        String name,
        String description,
        String icon,
        String isDeleted,
        CategoryResponse categoryResponse
) {
}
