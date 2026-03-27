package gael.guerra.login.model

import gael.guerra.login.R

data class Producto(val id: Int, val nombre: String, val precio: Int, val imagen: Int, val descripcion: String )

val listaZapatos = listOf(
    Producto(
        id = 1,
        nombre = "Nike Air Force 1",
        precio = 2500,
        imagen = R.drawable.airforce,
        descripcion = "Zapatillas clásicas, cómodas y versátiles para uso diario."
    ),
    Producto(
        id = 2,
        nombre = "Adidas Ultraboost",
        precio = 3200,
        imagen = R.drawable.ultraboost,
        descripcion = "Ideales para correr, con gran amortiguación y retorno de energía."
    ),
    Producto(
        id = 3,
        nombre = "Puma RS-X",
        precio = 2800,
        imagen = R.drawable.rsx,
        descripcion = "Diseño moderno y llamativo con excelente comodidad."
    ),
    Producto(
        id = 4,
        nombre = "Vans Old Skool",
        precio = 1800,
        imagen = R.drawable.oldskool,
        descripcion = "Estilo urbano clásico, perfectas para uso casual."
    ),
    Producto(
        id = 5,
        nombre = "Converse Chuck Taylor",
        precio = 1500,
        imagen = R.drawable.chuck,
        descripcion = "Icónicas y ligeras, combinan con todo."
    )
)

fun filtrarProductos(query: String): List<Producto> {
    return if (query.isBlank()) {
        listaZapatos
    } else {
        listaZapatos.filter {
            it.nombre.contains(query, ignoreCase = true)
        }
    }
}

fun obtenerProductoPorId(id: Int): Producto? {
    return listaZapatos.find {
        it.id == id
    }
}

