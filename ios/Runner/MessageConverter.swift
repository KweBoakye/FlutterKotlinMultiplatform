//
//  MessageConverter.swift
//  Runner
//
//  Created by Kweku on 12/01/2022.
//

import Foundation
import shared
import SwiftProtobuf

class MessageConverter: MessageConverterInterface {
    func convertMessageToBuffer(messageModel: MessageModel) -> Any {
        return MessageModelProto.with {
            $0.messageID = messageModel.messageId
            $0.title = messageModel.title
            $0.message = messageModel.message
        }.toFlutterStandardTypedData()
    }


}
