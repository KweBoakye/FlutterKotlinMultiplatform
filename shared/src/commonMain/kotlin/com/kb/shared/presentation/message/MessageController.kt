package com.kb.shared.presentation.message

import com.kb.shared.domain.MessageProvider

class MessageController(
private val messagePresenter: MessagePresenter) {

    fun getAndDisplayMessages(){
        MessageProvider.getMessages()
            .run(messagePresenter::displayMessages)
    }
}