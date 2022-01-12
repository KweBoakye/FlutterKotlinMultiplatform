package com.kb.shared.flutter

import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

actual typealias CommonMethodChannel = MethodChannel

@Suppress("FunctionNaming")
actual fun CommonMethodChannel(binaryMessenger: Any, name: String): CommonMethodChannel {
    println("MethodChannel $name created")
    return MethodChannel(binaryMessenger as BinaryMessenger, name)
}

actual fun CommonMethodChannel.invokeMethodCommon(method: String, arguments: Any?) {
    invokeMethod(method, arguments)
}

@Suppress("ComplexMethod")
actual fun CommonMethodChannel.setMethodChannelHandlerParameters(
    vararg methodChannelHandlerParameters: MethodChannelHandlerParameters<*, *>
) {

    setMethodCallHandler { call, result ->
        with(methodChannelHandlerParameters) {
            find {
                call.method == it.methodCall
            }.let {
                if (it != null) {
                    it.run {
                        when {

                            action != null -> {
                                when (shouldPassToResult) {
                                    true -> when (isError) {
                                        true -> result.error("", null, null)
                                        false -> result.success((action.invoke()))
                                    }
                                    false -> action.invoke()
                                }
                            }
                            functionWithParameters != null -> {
                                when (shouldPassToResult) {
                                    true -> result.success(
                                        functionWithParameters.invoke(call.arguments)
                                    )
                                    false -> functionWithParameters.invoke(call.arguments)
                                }
                            }
                            functionWithTransformationAndParameters != null -> {
                                when (shouldPassToResult) {
                                    true -> result.success(
                                        functionWithTransformationAndParameters
                                            .invoke(call.arguments)
                                    )
                                    false -> functionWithTransformationAndParameters.invoke(
                                        call.arguments
                                    )
                                }
                            }
                            else -> return@with
                        }
                    }
                } else {
                    result.notImplemented()
                }
            }
        }
    }
}

actual fun CommonMethodChannel.setMethodCallHandlerLambda(
    handler: (
        method: String?,
        arguments: Any?,
        success: ((result: Any) -> Unit)?,
        error: (errorCode: String, message: String?, details: Any?) -> Unit,
        notImplemented: () -> Unit
    ) -> Unit
) {
    setMethodCallHandler { call, result ->
        handler.invoke(
            call.method,
            call.arguments,
            result::success,
            result::error,
            result::notImplemented
        )
    }
}

actual fun CommonMethodChannel.setMethodCallHandlerLambda(handler: Handler) {
    setMethodCallHandler { call, result ->
        handler.handle(
            method = call.method,
            arguments = call.arguments,
            success = result::success,
            error = result::error,
            notImplemented = result::notImplemented
        )
    }
}

actual typealias CommonMethodCall = MethodCall