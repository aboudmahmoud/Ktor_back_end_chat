package com.chatcosute.route

import com.chatcosute.room.MamberAlredyExsitsExepation
import com.chatcosute.room.RoomConttroler
import com.chatcosute.seaction.ChatSecation
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach

fun Route.chatScoketRoute(
    roomConttroler: RoomConttroler
) {
    webSocket("/chat-socket") {
        val session = call.sessions.get<ChatSecation>()
        if (session == null) {
            //    close()
            //or
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No Seations"))
            return@webSocket
        }
        try {
            roomConttroler.onJoin(
                username = session.userName,
                sessionsID = session.seactionID,
                scocket = this
            )
            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    roomConttroler.SendMessage(
                        SenderUserName = session.userName,
                        message = frame.readText()
                    )
                }
            }
        } catch (expation: MamberAlredyExsitsExepation) {
            call.respond(HttpStatusCode.Conflict)
        } catch (expation: Exception) {
            expation.printStackTrace()
        } finally {
            roomConttroler.tryDissconcete(session.userName)
        }

    }
}

fun Route.getALLMesgge(
    roomConttroler: RoomConttroler
){
get("/messages"){
    call.respond(HttpStatusCode.OK,roomConttroler.getAllMessage())
}
}