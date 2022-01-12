@file:UseSerializers(pbandk.ser.TimestampSerializer::class)
package com.kb.shared.protobuf

import kotlinx.serialization.*
import kotlinx.serialization.json.*

data class MessageModelProto(
    val messageId: Int = 0,
    val title: String = "",
    val message: String = "",
    val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message<MessageModelProto> {
    override operator fun plus(other: MessageModelProto?) = protoMergeImpl(other)
    override val protoSize by lazy { protoSizeImpl() }
    override fun protoMarshal(m: pbandk.Marshaller) = protoMarshalImpl(m)
    override fun jsonMarshal(json: Json) = jsonMarshalImpl(json)
    fun toJsonMapper() = toJsonMapperImpl()
    companion object : pbandk.Message.Companion<MessageModelProto> {
        val defaultInstance by lazy { MessageModelProto() }
        override fun protoUnmarshal(u: pbandk.Unmarshaller) = MessageModelProto.protoUnmarshalImpl(u)
        override fun jsonUnmarshal(json: Json, data: String) = MessageModelProto.jsonUnmarshalImpl(json, data)
    }

    @Serializable
    data class JsonMapper (
        @SerialName("messageId")
        val messageId: Int? = null,
        @SerialName("title")
        val title: String? = null,
        @SerialName("message")
        val message: String? = null
    ) {
        fun toMessage() = toMessageImpl()
    }
}

fun MessageModelProto?.orDefault() = this ?: MessageModelProto.defaultInstance

private fun MessageModelProto.protoMergeImpl(plus: MessageModelProto?): MessageModelProto = plus?.copy(
    unknownFields = unknownFields + plus.unknownFields
) ?: this

private fun MessageModelProto.protoSizeImpl(): Int {
    var protoSize = 0
    if (messageId != 0) protoSize += pbandk.Sizer.tagSize(1) + pbandk.Sizer.int32Size(messageId)
    if (title.isNotEmpty()) protoSize += pbandk.Sizer.tagSize(2) + pbandk.Sizer.stringSize(title)
    if (message.isNotEmpty()) protoSize += pbandk.Sizer.tagSize(3) + pbandk.Sizer.stringSize(message)
    protoSize += unknownFields.entries.sumBy { it.value.size() }
    return protoSize
}

private fun MessageModelProto.protoMarshalImpl(protoMarshal: pbandk.Marshaller) {
    if (messageId != 0) protoMarshal.writeTag(8).writeInt32(messageId)
    if (title.isNotEmpty()) protoMarshal.writeTag(18).writeString(title)
    if (message.isNotEmpty()) protoMarshal.writeTag(26).writeString(message)
    if (unknownFields.isNotEmpty()) protoMarshal.writeUnknownFields(unknownFields)
}

private fun MessageModelProto.Companion.protoUnmarshalImpl(protoUnmarshal: pbandk.Unmarshaller): MessageModelProto {
    var messageId = 0
    var title = ""
    var message = ""
    while (true) when (protoUnmarshal.readTag()) {
        0 -> return MessageModelProto(messageId, title, message, protoUnmarshal.unknownFields())
        8 -> messageId = protoUnmarshal.readInt32()
        18 -> title = protoUnmarshal.readString()
        26 -> message = protoUnmarshal.readString()
        else -> protoUnmarshal.unknownField()
    }
}

private fun MessageModelProto.toJsonMapperImpl(): MessageModelProto.JsonMapper =
    MessageModelProto.JsonMapper(
        messageId,
        title.takeIf { it != "" },
        message.takeIf { it != "" }
    )

private fun MessageModelProto.JsonMapper.toMessageImpl(): MessageModelProto =
    MessageModelProto(
        messageId = messageId ?: 0,
        title = title ?: "",
        message = message ?: ""
    )

private fun MessageModelProto.jsonMarshalImpl(json: Json): String =
    json.encodeToString(MessageModelProto.JsonMapper.serializer(), toJsonMapper())

private fun MessageModelProto.Companion.jsonUnmarshalImpl(json: Json, data: String): MessageModelProto {
    val mapper = json.decodeFromString(MessageModelProto.JsonMapper.serializer(), data)
    return mapper.toMessage()
}
