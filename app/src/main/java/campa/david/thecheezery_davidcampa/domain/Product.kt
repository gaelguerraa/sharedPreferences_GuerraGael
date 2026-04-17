package campa.david.thecheezery_davidcampa.domain

import campa.david.thecheezery_davidcampa.R

data class Product(
    val id: Int = 0,
    val name: String,
    val price: Float,
    val image: Int? = null,
    val descriptor: String? = null
)

val productList = listOf(
    Product(
        id = 1,
        name = "Nike Air Force 1",
        price = 2500f,
        image = R.drawable.airforce,
        descriptor = "Classic sneakers, comfortable and versatile for everyday use."
    ),
    Product(
        id = 2,
        name = "Adidas Ultraboost",
        price = 3200f,
        image = R.drawable.ultraboost,
        descriptor = "Ideal for running, with great cushioning and energy return."
    ),
    Product(
        id = 3,
        name = "Puma RS-X",
        price = 2800f,
        image = R.drawable.rsx,
        descriptor = "Modern and eye-catching design with excellent comfort."
    ),
    Product(
        id = 4,
        name = "Vans Old Skool",
        price = 1800f,
        image = R.drawable.oldskool,
        descriptor = "Classic urban style, perfect for casual wear."
    ),
    Product(
        id = 5,
        name = "Converse Chuck Taylor",
        price = 1500f,
        image = R.drawable.chuck,
        descriptor = "Iconic and lightweight, matches everything."
    )
)
fun obtenerProductoPorId(productId: Int): Product? = productList.find { it.id == productId }