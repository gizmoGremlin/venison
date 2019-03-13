package com.pompouslypickled.venison.api.Hit

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.pompouslypickled.venison.workers.SeedDatabaseWorker

@Database(entities = [ RecipeBook::class,StoredRecipes::class], version = 1, exportSchema = false)
abstract class TheDatabase : RoomDatabase(){
    abstract fun recipeDao(): RecipeDao
    //TODO RecipeBookDao()

    companion object {

        @Volatile private var instance: TheDatabase? = null

        fun getInstance(context: Context): TheDatabase {
            return instance ?: synchronized(this){
                instance ?: buildTheDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildTheDatabase(context: Context): TheDatabase {
            return Room.databaseBuilder(context, TheDatabase::class.java, "venison_db")
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                      WorkManager.getInstance().enqueue(request)
                    }
                })
                .build()
        }
    }



}