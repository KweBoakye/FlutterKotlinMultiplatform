package com.kb.flutterkotlinmultiplatform.message

import com.kb.shared.protobuf.MessageModelProto
import com.kb.shared.domain.model.MessageModel

fun MessageModel.toMessageModelProto(): MessageModelProto{
    return MessageModelProto(
        messageId = messageId,
        title = title,
        message = message
        )
}