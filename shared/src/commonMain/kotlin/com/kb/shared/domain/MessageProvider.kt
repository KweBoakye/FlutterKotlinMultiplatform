package com.kb.shared.domain

import com.kb.shared.domain.model.MessageModel

object MessageProvider {
    fun getMessages(): List<MessageModel>{
        return listOf(
            MessageModel(
                messageId = 1,
                title = "Message 1",
                message = "first message"
            ),
            MessageModel(
                messageId = 2,
                title = "Message 2",
                message = "second Message"
            ),
            MessageModel(
                messageId = 3,
                title = "Message 3",
                message = "third message"
            )
        )
    }
}