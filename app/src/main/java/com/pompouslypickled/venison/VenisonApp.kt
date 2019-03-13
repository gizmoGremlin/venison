package com.pompouslypickled.venison

import android.app.Application
import android.content.Context
import com.pompouslypickled.venison.api.Hit.RecipeApi
import com.pompouslypickled.venison.api.Hit.RecipeRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import androidx.multidex.MultiDex
import com.pompouslypickled.venison.api.Hit.RecipeDao
import com.pompouslypickled.venison.api.Hit.TheDatabase


class VenisonApp : Application() {
    val Base_URL = "https://api.edamam.com/"
    //todo replace with koin ... also declare this in manifest !!!!
 init {
     instance = this
 }


    companion object {
        private lateinit var retrofit: Retrofit
        private lateinit var api: RecipeApi
        private lateinit var recipeRepository: RecipeRepository
        private lateinit var dao: RecipeDao
        fun injectApi() = api

        fun injectRecipeRepo() = recipeRepository
        private var instance: VenisonApp? = null

        fun applicationContext() : VenisonApp {
            return instance as VenisonApp
        }


    }

   override protected fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()



        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Base_URL)
            .build()

        api =  retrofit.create(RecipeApi::class.java)
         dao = TheDatabase.getInstance(baseContext).recipeDao()
        recipeRepository = RecipeRepository(api, dao)
    }
}