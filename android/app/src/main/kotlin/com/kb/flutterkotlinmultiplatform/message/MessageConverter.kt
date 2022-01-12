package com.kb.flutterkotlinmultiplatform.message

import com.kb.shared.presentation.message.MessageConverterInterface
import com.kb.shared.domain.model.MessageModel

class MessageConverter: MessageConverterInterface {

    override fun convertMessageToBuffer(messageModel: MessageModel): Any{
        return messageModel.toMessageModelProto().protoMarshal()
    }
}