package mapper

import dto.PersonDTO
import model.Person

class PersonMapper: BaseMapper<Person, PersonDTO>() {

    override fun fromDTO(item: PersonDTO): Person {
        val addressMapper = AddressMapper();
        return Person(
            id = item.id,
            name = item.name,
            email = item.email,
            telephone = item.telephone!!.toMutableSet(),
            // Mapeamos a la inversa AddressMapper
            address = item.address?.let { addressMapper.fromDTO(it).toMutableSet() }
        )
    }

    override fun toDTO(item: Person): PersonDTO {
        val addressMapper = AddressMapper();
        return PersonDTO(
            id = item.id,
            name = item.name,
            email = item.email,
            // Telefono entra sin tranformarse
            telephone = item.myPhoneNumbers?.toList(),
            // El DTO Lo transformamos para quitar al usuario completo que tiene
            address = item.myAddress?.let { addressMapper.toDTO(it.toList()) }
        )
    }
}