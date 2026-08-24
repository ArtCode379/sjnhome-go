package sjnenterprises.household.sjnhomego.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import sjnenterprises.household.sjnhomego.data.model.Product
import sjnenterprises.household.sjnhomego.data.model.ProductCategory

class ProductRepository {
    private val products = listOf(
        Product(
            id = 1,
            title = "Woven Storage Basket",
            description = "A sturdy handwoven basket with comfortable handles for blankets, toys, or everyday essentials.",
            category = ProductCategory.HOME,
            price = 24.95,
            imageUrl = "https://images.unsplash.com/photo-1618220179428-22790b461013?w=1200",
        ),
        Product(
            id = 2,
            title = "Stoneware Mug Set",
            description = "Four softly glazed mugs designed for relaxed morning coffee and easy everyday entertaining.",
            category = ProductCategory.HOME,
            price = 18.50,
            imageUrl = "https://images.unsplash.com/photo-1514228742587-6b1558fcca3d32?w=1200",
        ),
        Product(
            id = 3,
            title = "Soft Cotton Throw",
            description = "A breathable textured throw that adds warmth and a calm finishing layer to any room.",
            category = ProductCategory.HOME,
            price = 32.00,
            imageUrl = "https://images.unsplash.com/photo-1583845112203-454c2254edce?w=1200",
        ),
        Product(
            id = 4,
            title = "Desk Essentials Set",
            description = "A coordinated notebook, ruler, clips, and writing tools for a tidy and productive desk.",
            category = ProductCategory.STATIONERY,
            price = 16.75,
            imageUrl = "https://images.unsplash.com/photo-1456324504439-367cee3b3c32?w=1200",
        ),
        Product(
            id = 5,
            title = "Hardback Weekly Planner",
            description = "An undated weekly planner with generous notes pages and a durable fabric-bound cover.",
            category = ProductCategory.STATIONERY,
            price = 14.25,
            imageUrl = "https://images.unsplash.com/photo-1506784983877-45594efa4cbe?w=1200",
        ),
        Product(
            id = 6,
            title = "Fine Line Pen Collection",
            description = "Six smooth fine-line pens for planning, sketching, journaling, and thoughtful notes.",
            category = ProductCategory.STATIONERY,
            price = 9.95,
            imageUrl = "https://images.unsplash.com/photo-1583485088034-697b5bc54ccd?w=1200",
        ),
        Product(
            id = 7,
            title = "Everyday Canvas Tote",
            description = "A roomy reinforced canvas tote for shopping trips, workdays, and weekend errands.",
            category = ProductCategory.ACCESSORIES,
            price = 19.50,
            imageUrl = "https://images.unsplash.com/photo-1594223274512-ad4803739b7c?w=1200",
        ),
        Product(
            id = 8,
            title = "Travel Jewellery Case",
            description = "A compact zip case with soft-lined sections to keep small accessories organised on the move.",
            category = ProductCategory.ACCESSORIES,
            price = 21.00,
            imageUrl = "https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?w=1200",
        ),
        Product(
            id = 9,
            title = "Compact Automatic Umbrella",
            description = "A reliable wind-resistant umbrella with a comfortable handle and neat storage sleeve.",
            category = ProductCategory.ACCESSORIES,
            price = 17.95,
            imageUrl = "https://images.unsplash.com/photo-1534274988757-a28bf1a57c17?w=1200",
        ),
        Product(
            id = 10,
            title = "Garden Lantern String",
            description = "Warm outdoor lights that bring an inviting glow to balconies, patios, and summer tables.",
            category = ProductCategory.SEASONAL,
            price = 27.50,
            imageUrl = "https://images.unsplash.com/photo-1527529482837-4698179dc6ce?w=1200",
        ),
        Product(
            id = 11,
            title = "Cosy Winter Candle",
            description = "A long-burning soy wax candle with notes of cedar, orange peel, and gentle spice.",
            category = ProductCategory.SEASONAL,
            price = 13.50,
            imageUrl = "https://images.unsplash.com/photo-1602874801006-e26bca1a572b?w=1200",
        ),
        Product(
            id = 12,
            title = "Picnic Blanket",
            description = "A water-resistant foldaway blanket with a carry handle for parks, beaches, and festivals.",
            category = ProductCategory.SEASONAL,
            price = 29.95,
            imageUrl = "https://images.unsplash.com/photo-1523987355523-c7b5b0dd90a7?w=1200",
        ),
    )

    fun observeById(id: Int): Flow<Product?> = flowOf(products.find { it.id == id })

    fun getById(id: Int): Product? = products.find { it.id == id }

    fun observeAll(): Flow<List<Product>> = flowOf(products)
}

