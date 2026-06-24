package co.istad.ite.features.specification;

import co.istad.ite.features.specification_dto.RequestDto;
import co.istad.ite.features.specification_dto.SearchRequestDto;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class FilterSpecification<T> {
    private <T> T convert(Class<T> type, String value) {
        if (type == Integer.class) return (T) Integer.valueOf(value);
        if (type == Long.class) return (T) Long.valueOf(value);
        if (type == Double.class) return (T) Double.valueOf(value);
        if (type == Float.class) return (T) Float.valueOf(value);
        if (type == Boolean.class) return (T) Boolean.valueOf(value);
        if (type == String.class) return (T) value;
        if (type == LocalDate.class) return (T) LocalDate.parse(value);
        if (type == LocalDateTime.class) return (T) LocalDateTime.parse(value);
        throw new IllegalArgumentException("Unsupported type: " + type);
    }
    private Comparable getComparable(Path<?> path, SearchRequestDto dto) {
        Class<?> type = path.getJavaType();
        if (!Comparable.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException( dto.getOperation() + " not supported for " + type.getSimpleName()
            );
        }
        return (Comparable) convert(type, dto.getValue());
    }
    private Path<?> getPath(Root<T> root, SearchRequestDto dto) {
        if (dto.getJoinTable() != null) {
            Join<?, ?> join = root.join(dto.getJoinTable());
            return join.get(dto.getColumn());
        }
        return root.get(dto.getColumn());
    }
    private Predicate buildPredicate(CriteriaBuilder cb,Root<T> root,SearchRequestDto dto
    ) {
        Path<?> path = getPath(root,dto);
        switch (dto.getOperation()) {
            case EQUAL: return cb.equal(path, convert(path.getJavaType(), dto.getValue()));
            case NOT_EQUAL: return cb.notEqual(path,convert(path.getJavaType(), dto.getValue()));
            case LIKE:
                if (!String.class.isAssignableFrom(path.getJavaType()))
                    throw new IllegalArgumentException("LIKE only supports String fields");
                return cb.like(cb.lower((Path<String>) path), "%"+dto.getValue().toLowerCase()
                        + "%");
            case GREATER:
                return cb.greaterThan((Path<Comparable>) path, getComparable(path, dto));
            case LESS:
                return cb.lessThan((Path<Comparable>) path, getComparable(path, dto));
            case GREATER_OR_EQUAL:
                return cb.greaterThanOrEqualTo((Path<Comparable>) path,getComparable(path, dto));
            case LESS_OR_EQUAL:
                return cb.lessThanOrEqualTo((Path<Comparable>) path, getComparable(path, dto));
            case BETWEEN: {
                String[] split = dto.getValue().split(",");
                if (split.length != 2)
                    throw new IllegalArgumentException("BETWEEN requires 'value1,value2' format");
                Comparable from = (Comparable) convert(path.getJavaType(), split[0].trim());
                Comparable to = (Comparable) convert(path.getJavaType(), split[1].trim());
                return cb.between((Path<Comparable>) path, from, to);
            }
            case IS_NULL: return cb.isNull(path);
            case IS_NOT_NULL: return cb.isNotNull(path);
            default: throw new RuntimeException("Unsupported operation");
        }
    }
    public Specification<T> getSearchSpecification(List<SearchRequestDto> dtos, String operator) {
        return (root, query, cb) -> {
            List<Predicate> predicates = dtos.stream()
                    .map(dto->buildPredicate(cb,root,dto))
                    .toList();
            return operator == RequestDto.GlobalOperator.AND.toString()
                    ? cb.and(predicates.toArray(new Predicate[0]))
                    : cb.or(predicates.toArray(new Predicate[0]));
        };
    }
}
