package com.kb.shared.presentation.message

import com.kb.shared.domain.MessageProvider
import com.kb.shared.flutter.CommonMethodChannel
import com.kb.shared.flutter.setMethodCallHandlerLambda

class MessageScreen(
    private val messageController: MessageController,
    binaryMessenger: Any) {

    private val messageFlutterToNativeMethodChannel: CommonMethodChannel
    = CommonMethodChannel(binaryMessenger, "messageFlutterToNativeMethodChannel")

    init {
        messageFlutterToNativeMethodChannel.setMethodCallHandlerLambda {
                method: String?,
                arguments: Any?,
                success: ((result: Any) -> Unit)?,
                error: (errorCode: String, message: String?, details: Any?) -> Unit,
                notImplemented: () -> Unit ->
            when(method){
                "loadMessages" -> messageController.getAndDisplayMessages()
            }
        }
    }
}