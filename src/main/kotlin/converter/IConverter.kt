package converter

interface IConverter <A, B> {

    fun convertTo(origen: A): B
    fun convertFrom(destino: B): A
}
