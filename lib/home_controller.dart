



import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutterkotlinmultiplatform/home_view.dart';
import 'package:flutterkotlinmultiplatform/protobufs/MessageModelProto.pb.dart';

class HomeController extends StatefulWidget {
  @override
  HomeControllerState createState() => HomeControllerState();

}

class HomeControllerState extends State<HomeController> {

 static const MethodChannel messageFlutterToNativeMethodChannel =
     MethodChannel("messageFlutterToNativeMethodChannel");

 static const MethodChannel messageNativeToFlutterMethodChannel =
     MethodChannel("messageNativeToFlutterMethodChannel");

 List<MessageModelProto> messageList = <MessageModelProto>[];

 @override
  void initState() {
   messageNativeToFlutterMethodChannel.setMethodCallHandler(handler);
   loadMessages();
    super.initState();
  }

  void loadMessages(){
    messageFlutterToNativeMethodChannel.invokeMethod("loadMessages");
  }

 void updateList(List<MessageModelProto> messageList) {
   if (messageList != this.messageList) {
     setState(() {
       this.messageList.clear();
       this.messageList.addAll(messageList);

     });
   }
 }

 Future<void> handler(MethodCall methodCall) async {
   if (methodCall.method == "displayMessages") {
     try {
       final List<MessageModelProto> messages = (methodCall.arguments as List<
           dynamic>)
           .map((item) =>
           MessageModelProto.fromBuffer((item as Uint8List).toList()))
           .toList();
       updateList(messages);
     } catch (e) {
       print(e);
     }
   }
 }

  @override
  Widget build(BuildContext context) {
    return HomeView(this);
  }

}