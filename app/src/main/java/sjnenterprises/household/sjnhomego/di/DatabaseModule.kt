package sjnenterprises.household.sjnhomego.di

import androidx.room.Room
import sjnenterprises.household.sjnhomego.data.database.MBWRKDatabase
import org.koin.dsl.module

private const val DB_NAME = "mbwrk_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = MBWRKDatabase::class.java,
            name = DB_NAME
        ).build()
    }

    single { get<MBWRKDatabase>().cartItemDao() }

    single { get<MBWRKDatabase>().orderDao() }
}