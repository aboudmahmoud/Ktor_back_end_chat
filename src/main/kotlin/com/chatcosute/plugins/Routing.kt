package com.chatcosute.plugins

import com.chatcosute.room.RoomConttroler
import com.chatcosute.route.chatScoketRoute
import com.chatcosute.route.getALLMesgge
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val roomConttroler by  inject<RoomConttroler>()
    install(Routing){
        chatScoketRoute(roomConttroler = roomConttroler)
        getALLMesgge(roomConttroler)
    }
}
