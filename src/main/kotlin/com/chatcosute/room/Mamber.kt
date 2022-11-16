package com.chatcosute.room

import io.ktor.websocket.*


data class Mamber(
    val userName:String,
    val seesionId:String,
    val scocket: WebSocketSession
)
