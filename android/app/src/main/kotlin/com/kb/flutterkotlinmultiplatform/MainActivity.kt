package com.kb.flutterkotlinmultiplatform

import android.os.Bundle
import com.kb.flutterkotlinmultiplatform.message.MessageConverter
import com.kb.shared.presentation.message.MessagePresenter
import com.kb.shared.presentation.message.MessageScreen
import com.kb.shared.domain.MessageProvider
import com.kb.shared.presentation.message.MessageController
import io.flutter.embedding.android.FlutterActivity

class MainActivity: FlutterActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binaryMessenger = flutterEngine!!.dartExecutor.binaryMessenger
        MessageScreen(
            MessageController(
                MessagePresenter(
                    MessageConverter(), binaryMessenger)),
        binaryMessenger
        )
    }
}
