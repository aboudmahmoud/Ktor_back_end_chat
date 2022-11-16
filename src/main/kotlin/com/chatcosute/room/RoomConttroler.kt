package com.chatcosute.room

import com.chatcosute.data.MassgeDataSource
import com.chatcosute.data.model.Message
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class RoomConttroler(
    private val massgeDataSource: MassgeDataSource
) {
    private val members = ConcurrentHashMap<String, Mamber>()

    fun onJoin(
        username: String,
        sessionsID: String,
        scocket: WebSocketSession
    ) {
        if (members.contains(username)) {
            throw MamberAlredyExsitsExepation()
        }
        members[username] = Mamber(
            userName = username,
            seesionId = sessionsID,
            scocket = scocket
        )
    }

    suspend fun SendMessage(SenderUserName: String, message: String) {
        members.values.forEach { member ->
            val messageEntity = Message(
                text = message,
                userName = SenderUserName,
                timestamp = System.currentTimeMillis()
            )
            massgeDataSource.insertMassge(messageEntity)

            val parsedMassge = Json.encodeToString(messageEntity)
            member.scocket.send(Frame.Text(parsedMassge))

        }
    }

    suspend fun getAllMessage(): List<Message> {
        return massgeDataSource.getAllMessage()
    }

    suspend fun tryDissconcete(username: String){
        members[username]?.scocket?.close()
        if(members.contains(username)){
            members.remove(username)
        }
    }
}