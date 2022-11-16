package com.chatcosute.plugins

import com.chatcosute.seaction.ChatSecation
import io.ktor.server.sessions.*
import io.ktor.server.application.*
import io.ktor.util.*

fun Application.configureSecurity() {

    install(Sessions) {
        cookie<ChatSecation>("SESSION")
    }
    intercept(ApplicationCallPipeline.Plugins){
        if (call.sessions.get<ChatSecation>()==null){
            val userName=call.parameters["userName"] ?: "Guest"
            call.sessions.set(ChatSecation(userName, generateNonce()))
        }
    }


}
