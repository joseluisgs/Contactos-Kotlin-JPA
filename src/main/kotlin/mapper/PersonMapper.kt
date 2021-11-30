package mapper

import dto.PersonDTO
import model.Person

class PersonMapper: BaseMapper<Person, PersonDTO>() {

    override fun fromDTO(item: PersonDTO): Person {
        return Person(
            id = item.id,
            name = item.name,
            email = item.email,
            telephone = null,
            address = null
        )
    }

    override fun toDTO(item: Person): PersonDTO {
        val addresMapper = AddressMapper();
        return PersonDTO(
            id = item.id,
            name = item.name,
            email = item.email,
            // Telefono entra sin tranformarse
            telephone = item.myPhoneNumbers?.toList(),
            // El DTO Lo transformamos para quitar al usuario completo que tiene
            address = item.myAddress?.let { addresMapper.toDTO(it.toList()) }
        )
    }
}