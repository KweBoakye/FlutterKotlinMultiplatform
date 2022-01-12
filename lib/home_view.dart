

import 'package:flutter/material.dart';
import 'package:flutterkotlinmultiplatform/message_item_view.dart';
import 'package:flutterkotlinmultiplatform/protobufs/MessageModelProto.pb.dart';

import 'home_controller.dart';

class HomeView extends StatelessWidget {

  final HomeControllerState state;

  const HomeView(this.state, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
        title: Text("flutter Kotlin Multiplatform"),
    ),
    body: SingleChildScrollView(
        controller:
        ScrollController(keepScrollOffset: true),
    child: SizedBox(
    height: MediaQuery.of(context).size.height,
    child: Column(
      mainAxisAlignment: MainAxisAlignment.center,
    crossAxisAlignment: CrossAxisAlignment.center,
    children: <Widget>[
      TextButton(onPressed: state.loadMessages, child: Text('load messages')),
      Expanded(child: ListView.builder(
      itemCount: state.messageList.length,
        itemBuilder: (context, index) {
        MessageModelProto message = state.messageList[index];
        return MessageItemView(messageId: message.messageId,
            title: message.title,
            message: message.message);
        }))
    ]
    )
    )
    )
    );
  }

}