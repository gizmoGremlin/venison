package com.pompouslypickled.venison.api.Hit

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


//appId ="c3f86cd2", appKey = "4f3347598c67bddaab8183119f5fcc61",

interface RecipeApi {
    // curl ="https://api.edamam.com/search?q=chicken&app_id=${YOUR_APP_ID}&app_key=${YOUR_APP_KEY}&from=0&to=3&calories=591-722&health=alcohol-free"
    @GET("search")
    fun searchRecipies(
        @Query("q") searchTerm: String, @Query("app_id") appId: String,
        @Query("app_key") appKey: String, @Query("from") from: Int, @Query("to") to: Int
    ): Observable<RecipeData>


}

