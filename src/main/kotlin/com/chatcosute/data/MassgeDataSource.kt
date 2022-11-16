package com.chatcosute.data

import com.chatcosute.data.model.Message

interface MassgeDataSource {
    suspend fun  getAllMessage():List<Message>
    suspend fun insertMassge(message: Message)
}