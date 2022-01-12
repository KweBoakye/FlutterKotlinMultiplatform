package com.kb.shared.flutter

expect class CommonMethodChannel

expect fun CommonMethodChannel(binaryMessenger: Any, name: String): CommonMethodChannel

expect fun CommonMethodChannel.invokeMethodCommon(method: String, arguments: Any?)

expect fun CommonMethodChannel.setMethodChannelHandlerParameters(
    vararg methodChannelHandlerParameters: MethodChannelHandlerParameters<*, *>
)

fun interface Handler {
    fun handle(
        method: String?,
        arguments: Any?,
        success: ((result: Any) -> Unit)?,
        error: (errorCode: String, message: String?, details: Any?) -> Unit,
        notImplemented: () -> Unit
    )
}

expect fun CommonMethodChannel.setMethodCallHandlerLambda(
    handler: (
        method: String?,
        arguments: Any?,
        success: ((result: Any) -> Unit)?,
        error: (errorCode: String, message: String?, details: Any?) -> Unit,
        notImplemented: () -> Unit
    ) -> Unit
)

expect fun CommonMethodChannel.setMethodCallHandlerLambda(handler: Handler)

expect class CommonMethodCall