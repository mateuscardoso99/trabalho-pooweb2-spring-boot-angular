import 'package:flutter/material.dart';
import 'package:flutter_app/components/DrawerNavigation.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key, required this.email});

  final String email;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Home Page'),
      ),
      drawer: DrawerNavigation(email: email),
      body: Column(
        children: [
          Text(email),
          Center(
            child: ElevatedButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: const Text("Go back!"),
            ),
          ),
        ],
      )
    );
  }
}