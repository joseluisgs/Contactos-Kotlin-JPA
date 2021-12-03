# Contactos Kotlin JPA
Ejemplos de una aplicación de manejo de contactos con Kotlin y JPA. Usando para testear la aplicación JUnit 5 y Mockito. Almacenamiento en base de datos relacional H2.

[![Kotlin](https://img.shields.io/badge/Code-Kotlin-blueviolet)](https://kotlinlang.org/)
[![LISENCE](https://img.shields.io/badge/Lisence-MIT-green)]()
![GitHub](https://img.shields.io/github/last-commit/joseluisgs/Contactos-Kotlin-JPA)


![imagen](https://www.adesso-mobile.de/wp-content/uploads/2021/02/kotlin-einfu%CC%88hrung.jpg)

## Acerca de
Este ejemplo de clase pensado para Acceso a Datos, muestra como consumir una base de datos relacional usando JPA con Hibernate. Además se ha mostrado el uso de JUnit 5 y Mockito para testear algunos elementos. Finalmente tambien se mapean objetos y se convierten a JSON para entrada y salida de la información.

## Enunciado
En persona puede tener dirección o no, incluso varias direcciones. De la misma manera puede tener o no tener uno o varios teléfonos. Una dirección en un instante solo puede estar ocupada por una persona, y un teléfono sólo pertenece a una persona.

Además, dada una persona sabremos su direcciones y teléfonos, si los hay, pero también dada una dirección necesitamos saber qué persona vive allí. (Por ejemplo, si una persona tiene una dirección en Madrid, podemos saber a qué persona esa dirección pertenece). Para los teléfonos no es el caso.

Se implementa una arquitectura basada en Controlador -> Servicios -> Repositorio, siguiendo los [ejemplos vistos en clase](https://github.com/joseluisgs?tab=repositories&q=blog&type=&language=&sort=). Nos centramos en Persona, aunque gracias a JPA, al pasar un objeto persona con sus datos completos se insertan los datos correspondientes en las tablas indicadas según el diseño de objetos.

### Diagrama
![diagrama](diagrams/diagram.png)

## Autor

Codificado con :sparkling_heart: por [José Luis González Sánchez](https://twitter.com/joseluisgonsan)

[![Twitter](https://img.shields.io/twitter/follow/joseluisgonsan?style=social)](https://twitter.com/joseluisgonsan)
[![GitHub](https://img.shields.io/github/followers/joseluisgs?style=social)](https://github.com/joseluisgs)

### Contacto
<p>
  Cualquier cosa que necesites házmelo saber por si puedo ayudarte 💬.
</p>
<p>
    <a href="https://twitter.com/joseluisgonsan" target="_blank">
        <img src="https://i.imgur.com/U4Uiaef.png" 
    height="30">
    </a> &nbsp;&nbsp;
    <a href="https://github.com/joseluisgs" target="_blank">
        <img src="https://cdn.iconscout.com/icon/free/png-256/github-153-675523.png" 
    height="30">
    </a> &nbsp;&nbsp;
    <a href="https://www.linkedin.com/in/joseluisgonsan" target="_blank">
        <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/ca/LinkedIn_logo_initials.png/768px-LinkedIn_logo_initials.png" 
    height="30">
    </a>  &nbsp;&nbsp;
    <a href="https://joseluisgs.github.io/" target="_blank">
        <img src="https://joseluisgs.github.io/favicon.png" 
    height="30">
    </a>
</p>


## Licencia

Este proyecto está licenciado bajo licencia **MIT**, si desea saber más, visite el fichero [LICENSE](./LICENSE) para su uso docente y educativo.