package com.chatcosute.di

import com.chatcosute.data.DataSourceImpalaction
import com.chatcosute.data.MassgeDataSource
import com.chatcosute.room.RoomConttroler
import io.ktor.http.auth.*
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


val mainMoudel = module {
  single {
      KMongo.createClient().coroutine.getDatabase("message_db_yt")
  }
    // ^ get

    single<MassgeDataSource> {
        DataSourceImpalaction(get())
    }
    single {
        RoomConttroler(get())
    }
}