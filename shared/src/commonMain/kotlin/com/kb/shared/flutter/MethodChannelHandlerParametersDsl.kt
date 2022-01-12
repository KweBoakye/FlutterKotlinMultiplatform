package com.kb.shared.flutter

@DslMarker
annotation class MethodChannelHandlerParametersDslMarkers

fun <R, T> methodChannelHandlerParameters(
    block: MethodChannelHandlerParametersBuilder<R, T>.() -> Unit
):
        MethodChannelHandlerParameters<R, T> =
    MethodChannelHandlerParametersBuilder<R, T>().apply(block).build()

fun <T> functionAndParameters(block: FunctionAndParametersBuilder<T>.() -> Unit) =
    FunctionAndParametersBuilder<T>().apply(block).build()

@MethodChannelHandlerParametersDslMarkers
class MethodChannelHandlerParametersBuilder<R, T> {

    var methodCall: String = ""
    var action: (() -> Any)? = null
    var actionWithParameter: ((Any) -> Any)? = null
    var actionParameters: Any? = null
    var shouldPassToResult: Boolean = false
    var isError: Boolean = false
    var functionWithParameter: ((T) -> Any)? = null
    var transformation: (R.() -> T)? = null

    fun build(): MethodChannelHandlerParameters<R, T> =
        MethodChannelHandlerParameters(
            methodCall,
            action,
            actionWithParameter,
            actionParameters,
            shouldPassToResult,
            FunctionWithParameters(functionWithParameter),
            FunctionWithTransformationAndParameters(functionWithParameter, transformation)
        )

    /* fun buildWithTransformation(): MethodChannelHandlerParameters<T,R> =
         MethodChannelHandlerParameters(
             methodCall,
             action,
             actionWithParameter,
             actionParameters,
             shouldPassToResult,
             functionWithTransformationAndParameters =
             FunctionWithTransformationAndParameters(functionWithParameter, transformation)
         )*/
}

class FunctionAndParametersBuilder<T> {

    var actionWithParameter: ((T) -> Any)? = null
    // var actionParameters: T ? = null

    fun build(): FunctionWithParameters<T> =
        FunctionWithParameters(
            actionWithParameter
        )
}