package mapper

abstract class BaseMapper<T, DTO> : IMapper<T, DTO> {
    override fun fromDTO(items: List<DTO>): List<T> {
        return items.map { item: DTO -> this.fromDTO(item) }.toList()
    }

    // Obligamos a implementarlas
    abstract override fun fromDTO(item: DTO): T

    override fun toDTO(items: List<T>): List<DTO> {
        return items.map { item: T -> this.toDTO(item) }.toList()
    }

    abstract override fun toDTO(item: T): DTO
}