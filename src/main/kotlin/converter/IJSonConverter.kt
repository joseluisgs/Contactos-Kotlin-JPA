package converter

interface IJSonConverter<T, String>: IConverter<T, String> {
    override fun convertTo(item: T?): String
    override fun convertFrom(json: String): T
}