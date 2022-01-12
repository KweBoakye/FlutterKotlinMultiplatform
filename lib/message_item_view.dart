

import 'package:flutter/material.dart';

class MessageItemView extends StatelessWidget {

  final int messageId;
  final String message;
  final String title;

  const MessageItemView(
      {Key? key,
      required this.messageId,
      required this.title,
      required this.message}
  ) : super(key: key);


  @override
  Widget build(BuildContext context) {
    return Card(
      child: ListTile(
        leading: Text("$messageId"),
        title: Text(title),
        subtitle: Text(message),
      ),
    );
  }

}