import 'package:flutter/material.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key, required this.email});

  final String email;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Home Page'),
      ),
      drawer: Drawer(
        child: ListView(
          padding: EdgeInsets.zero,
          children: <Widget>[
            UserAccountsDrawerHeader(
              accountEmail: Text(email),
              accountName: Text(email),
              currentAccountPicture: const CircleAvatar(
                child: Text("AA"),
              ),
            ),

            ListTile(
              leading: const Icon(Icons.shopping_basket),
              title: const Text("Pedidos"),
              onTap: () => {}
            ),

            ListTile(
              leading: const Icon(Icons.person),
              title: const Text("Perfil"),
              onTap: () => {}
            )
          ],
        ),
      ),
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