package com.pompouslypickled.venison.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

const val GAME_TYPE = "USE_GAME_TYPE"

class GameTypeProviderImpl(context: Context) : GameTypeProvider {


    private val appContext = context.applicationContext
    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)
    override fun getGameTypeEnum(): GameTypeEnum {
        val selectedType = preferences.getString(GAME_TYPE,GameTypeEnum.VENISON.name)
        return GameTypeEnum.valueOf(selectedType!!)
    }
}