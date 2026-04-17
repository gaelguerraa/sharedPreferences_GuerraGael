package campa.david.thecheezery_davidcampa.domain

data class Product(
    val id: Int = 0,
    val name: String,
    val price: Float,
    val image: String? = null,
    val descriptor: String? = null
)