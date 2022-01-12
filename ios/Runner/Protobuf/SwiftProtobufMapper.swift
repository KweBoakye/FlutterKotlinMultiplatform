//
//  SwiftProtobufMapper.swift
//  Runner
//
//  Created by Kweku on 12/01/2022.
//

import Foundation
import SwiftProtobuf

extension Message {
    func toFlutterStandardTypedData() -> FlutterStandardTypedData {
        do {
          return FlutterStandardTypedData(bytes: try serializedData())
        } catch  BinaryEncodingError.anyTranscodeFailure {
            print(BinaryEncodingError.anyTranscodeFailure.localizedDescription)
            return  FlutterStandardTypedData()
        } catch  BinaryEncodingError.missingRequiredFields {
            print(BinaryEncodingError.missingRequiredFields.localizedDescription)
            return FlutterStandardTypedData()
        } catch {
            print("Unknown SwiftProtoBuf Error")
            return FlutterStandardTypedData()
        }
    }
}
