package controller

interface IController<S> {
    val service: S

    // Otros métodos que tenga el controllador
}