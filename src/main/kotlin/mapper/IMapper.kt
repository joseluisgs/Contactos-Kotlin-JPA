package mapper

interface IMapper<T, DTO> {

    fun fromDTO(items: List<DTO>): List<T>
    fun toDTO(items: List<T>): List<DTO>

    fun fromDTO(item: DTO): T
    fun toDTO(item: T): DTO

}