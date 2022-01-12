package com.kb.shared.presentation.message

import com.kb.shared.domain.model.MessageModel
import com.kb.shared.flutter.CommonMethodChannel
import com.kb.shared.flutter.invokeMethodCommon

class MessagePresenter(
    private val messageConverter: MessageConverterInterface,
    binaryMessenger: Any) {
    private val messageNativeToFlutterMethodChannel: CommonMethodChannel = CommonMethodChannel(
        binaryMessenger,
        "messageNativeToFlutterMethodChannel"
    )


    fun displayMessages(messages: List<MessageModel>){
        messageNativeToFlutterMethodChannel.invokeMethodCommon(
            "displayMessages",
            messages.map(messageConverter::convertMessageToBuffer)
        )
    }
}