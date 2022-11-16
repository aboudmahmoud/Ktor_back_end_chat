package com.chatcosute.data

import com.chatcosute.data.model.Message
import org.litote.kmongo.coroutine.CoroutineDatabase

class DataSourceImpalaction(
    private val db: CoroutineDatabase
) : MassgeDataSource {

    private val messages = db.getCollection<Message>()


    override suspend fun getAllMessage(): List<Message> {
        return messages.find().descendingSort(Message::timestamp).toList()
    }

    override suspend fun insertMassge(message: Message) {
        messages.insertOne(message)
    }
}