import 'package:flutter/material.dart';
import 'package:flutter_app/views/portal-usuario/perfil/PerfilPage.dart';

class DrawerNavigation extends StatelessWidget {
  const DrawerNavigation({super.key, required this.email});

  final String email;

  @override
  Widget build(BuildContext context) {
    return Drawer(
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
              onTap: () => {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const PerfilPage(titulo: "Editar Perfil")),
                )
              }
            )
          ],
        )
    );
  }
}