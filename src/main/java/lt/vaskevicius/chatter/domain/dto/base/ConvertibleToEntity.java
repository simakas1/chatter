package lt.vaskevicius.chatter.domain.dto.base;

public interface ConvertibleToEntity<T> {
    T toEntity();
}
