package com.pompouslypickled.venison.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.pompouslypickled.venison.api.Hit.TheDatabase

class SeedDatabaseWorker (
    context: Context,
    workerParams: WorkerParameters
): Worker(context, workerParams){

    private val TAG by lazy { SeedDatabaseWorker::class.java.simpleName }

    override fun doWork(): Result {

                TODO("Do something with Database..")


                    val database = TheDatabase.getInstance(applicationContext)


                   return Result.success()

            }

    }



