package com.kb.shared.flutter

import io.flutter.flutter.*
import platform.darwin.NSObject

actual typealias CommonMethodChannel = FlutterMethodChannel

@Suppress("FunctionNaming")
actual fun CommonMethodChannel(binaryMessenger: Any, name: String): CommonMethodChannel {
    return FlutterMethodChannel.methodChannelWithName(name, binaryMessenger as NSObject)
}

actual fun CommonMethodChannel.invokeMethodCommon(method: String, arguments: Any?) {
    invokeMethod(method, arguments)
}

@Suppress("ComplexMethod")
actual fun CommonMethodChannel.setMethodChannelHandlerParameters(
    vararg methodChannelHandlerParameters: MethodChannelHandlerParameters<*, *>
) {

    setMethodCallHandler { flutterMethodCall, function ->

        with(methodChannelHandlerParameters) {
            find {
                flutterMethodCall?.method == it.methodCall
            }.let {
                print("method")
                if (it != null) {
                    it.run {
                        println("method found")
                        when {

                            action != null -> {
                                when (shouldPassToResult) {
                                    true -> when (isError) {
                                        true -> function?.invoke(FlutterError)
                                        false -> function?.invoke(action.invoke())
                                    }
                                    false -> action.invoke()
                                }
                            }
                            functionWithParameters != null -> {
                                when (shouldPassToResult) {
                                    true -> function?.invoke(
                                        functionWithParameters
                                            .invoke(flutterMethodCall?.arguments!!)
                                    )
                                    false -> functionWithParameters.invoke(
                                        flutterMethodCall?.arguments!!
                                    )
                                }
                            }
                            functionWithTransformationAndParameters != null -> {
                                when (shouldPassToResult) {
                                    true -> function?.invoke(
                                        functionWithTransformationAndParameters
                                            .invoke(flutterMethodCall?.arguments!!)
                                    )
                                    false ->
                                        functionWithTransformationAndParameters
                                            .invoke(flutterMethodCall?.arguments!!)
                                }
                            }

                            else -> return@with
                        }
                    }
                } else {
                    println("result not implemented")
                    function?.invoke(FlutterMethodNotImplemented)
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
    setMethodCallHandler { flutterMethodCall, function ->
        handler(
            flutterMethodCall?.method,
            flutterMethodCall?.arguments,
            function,
            function::flutterError,
            function::notImplemented
        )
    }
}

actual fun CommonMethodChannel.setMethodCallHandlerLambda(handler: Handler) {
    setMethodCallHandler { flutterMethodCall, function ->
        handler.handle(
            method = flutterMethodCall?.method,
            arguments = flutterMethodCall?.arguments,
            success = function,
            error = function::flutterError,
            notImplemented = function::notImplemented
        )
    }
}

fun FlutterResult.flutterError(
    code: String,
    message: String?,
    details: Any?
) {
    this?.invoke(io.flutter.flutter.FlutterError.errorWithCode(code, message, details))
}

fun FlutterResult.notImplemented() {
    this?.invoke(io.flutter.flutter.FlutterMethodNotImplemented)
}

actual typealias CommonMethodCall = FlutterMethodCall