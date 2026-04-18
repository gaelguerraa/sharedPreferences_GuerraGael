package campa.david.thecheezery_davidcampa.data

import android.content.ContentValues
import campa.david.thecheezery_davidcampa.domain.Product
import campa.david.thecheezery_davidcampa.data.CheezeryContract.ProductsEntry

class ProductDAO(private val dbHelper: DatabaseHelper){
    fun insertProduct(product: Product): Long  {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(ProductsEntry.COLUMN_NAME, product.name)
            put(ProductsEntry.COLUMN_PRICE, product.price)
            put(ProductsEntry.COLUMN_DESCRIPTION, product.descriptor)
            put(ProductsEntry.COLUMN_IMAGE, product.image)
        }
        return db.insert(ProductsEntry.TABLE_NAME, null, values)
    }

    fun getAllProducts():List<Product>{
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            ProductsEntry.TABLE_NAME,
            arrayOf(ProductsEntry.COLUMN_ID,
                ProductsEntry.COLUMN_NAME,
                ProductsEntry.COLUMN_PRICE,
                ProductsEntry.COLUMN_DESCRIPTION,
                ProductsEntry.COLUMN_IMAGE,
                ),
            null, null, null, null, null
        )

        val products = mutableListOf<Product>()
        with(cursor){
            while (moveToNext()){
                val id = getInt(getColumnIndexOrThrow(ProductsEntry.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_NAME))
                val price = getFloat(getColumnIndexOrThrow(ProductsEntry.COLUMN_PRICE))
                val description = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_DESCRIPTION))
                val image = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_IMAGE))
                products.add(Product(id, name, price, image, description))
            }

        }
        cursor.close()
        return products
    }

    fun getProductById(productId: Int): Product? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
        ProductsEntry.TABLE_NAME,
        arrayOf(ProductsEntry.COLUMN_ID,
            ProductsEntry.COLUMN_NAME,
            ProductsEntry.COLUMN_PRICE,
            ProductsEntry.COLUMN_DESCRIPTION,
            ProductsEntry.COLUMN_IMAGE,
        ),
        "${ProductsEntry.COLUMN_ID} = ?",
        arrayOf(productId.toString()),
        null, null, null
        )

        val product: Product? = cursor.use{
            if(it.moveToFirst()){
                val id = it.getInt(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_ID))
                val name = it.getString(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_NAME))
                val price = it.getFloat(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_PRICE))
                val description = it.getString(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_DESCRIPTION))
                val image = it.getString(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_IMAGE))
                Product(id, name, price, image, description)
            } else{
                null
            }
        }
        return product
    }

    fun addToCart(productId: Int): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("productId", productId)
        }
        return db.insert("Cart", null, values)
    }

    fun getCartItems(): List<Product> {
        val db = dbHelper.readableDatabase
        val query = """
            SELECT p.* FROM ${ProductsEntry.TABLE_NAME} p
            JOIN Cart c ON p.${ProductsEntry.COLUMN_ID} = c.productId
        """.trimIndent()
        val cursor = db.rawQuery(query, null)
        val products = mutableListOf<Product>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ProductsEntry.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_NAME))
                val price = getFloat(getColumnIndexOrThrow(ProductsEntry.COLUMN_PRICE))
                val description = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_DESCRIPTION))
                val image = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_IMAGE))
                products.add(Product(id, name, price, image, description))
            }
        }
        cursor.close()
        return products
    }

}

