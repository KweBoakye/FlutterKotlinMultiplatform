package com.kb.shared.flutter

data class MethodChannelHandlerParameters<R, T>(
    val methodCall: String? = null,
    val action: (() -> Any)? = null,
    val actionWithParameter: ((Any) -> Any)? = null,
    val actionParameters: Any? = null,
    val shouldPassToResult: Boolean = false,
    val functionWithParameters: FunctionWithParameters<T>? = null,
    val functionWithTransformationAndParameters:
    FunctionWithTransformationAndParameters<R, T>? = null,
    val isError: Boolean = false

)

data class FunctionWithParameters<T> (
    val actionWithParameter: ((T) -> Any)? = null
) : (Any) -> Unit {
    @Suppress("UNCHECKED_CAST")
    override fun invoke(p1: Any) {
        actionWithParameter?.invoke(p1 as T)
    }
}

data class FunctionWithTransformationAndParameters<R, T>(
    val actionWithParameter: ((T) -> Any)? = null,
    val transformation: (R.() -> T)? = null
) : (Any) -> Unit {

    @Suppress("UNCHECKED_CAST")
    override fun invoke(p1: Any) {
        if (actionWithParameter != null) {
            transformation?.invoke((p1 as R))
                ?.run(actionWithParameter::invoke)
        }
    }
}
