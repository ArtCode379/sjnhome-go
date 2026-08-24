package sjnenterprises.household.sjnhomego.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import sjnenterprises.household.sjnhomego.data.dao.CartItemDao
import sjnenterprises.household.sjnhomego.data.dao.OrderDao
import sjnenterprises.household.sjnhomego.data.database.converter.Converters
import sjnenterprises.household.sjnhomego.data.entity.CartItemEntity
import sjnenterprises.household.sjnhomego.data.entity.OrderEntity

@Database(
    entities = [CartItemEntity::class, OrderEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MBWRKDatabase : RoomDatabase() {

    abstract fun cartItemDao(): CartItemDao

    abstract fun orderDao(): OrderDao
}