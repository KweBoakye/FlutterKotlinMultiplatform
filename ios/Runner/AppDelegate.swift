import UIKit
import Flutter
import shared

@UIApplicationMain
@objc class AppDelegate: FlutterAppDelegate {
  override func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
  ) -> Bool {
    GeneratedPluginRegistrant.register(with: self)

    let controller: FlutterViewController = window?.rootViewController as! FlutterViewController

      let messageController = MessageController(messagePresenter:
                                    MessagePresenter(messageConverter: MessageConverter(),
                                                     binaryMessenger: controller.binaryMessenger))
      let _ = MessageScreen(messageController: messageController, binaryMessenger: controller.binaryMessenger)
    return super.application(application, didFinishLaunchingWithOptions: launchOptions)
  }
}
