package com.kb.shared.presentation.message

import com.kb.shared.domain.model.MessageModel

interface MessageConverterInterface {
    fun convertMessageToBuffer(messageModel: MessageModel): Any
}