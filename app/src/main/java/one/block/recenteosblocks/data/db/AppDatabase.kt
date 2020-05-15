package one.block.recenteosblocks.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import one.block.recenteosblocks.data.db.dao.BlockDao
import one.block.recenteosblocks.data.db.entities.Block

@Database(
    entities = [Block::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getBlockDao() : BlockDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(AppDatabase::class) {
            buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "MyDatabase.db"
            ).build()
    }
}