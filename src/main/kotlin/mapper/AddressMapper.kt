package mapper

import dto.AddressDTO
import model.Address

class AddressMapper : BaseMapper<Address, AddressDTO>() {
    override fun fromDTO(item: AddressDTO): Address {
        return Address(
            item.street,
            item.postalCode,
            item.city
        )
    }

    override fun toDTO(item: Address): AddressDTO {
        return AddressDTO(
            item.id,
            item.street,
            item.postalCode,
            item.city,
            item.person.id
        )
    }
}