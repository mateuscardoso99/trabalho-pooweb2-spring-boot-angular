import 'package:flutter/material.dart';
import 'package:flutter_app/api.dart';
import 'package:flutter_app/components/BottomTabNavigator.dart';
import 'package:flutter_app/views/portal-usuario/HomePage.dart';
import 'package:flutter_app/views/portal-usuario/pedido/MapaPage.dart';
import 'package:flutter_app/views/portal-usuario/perfil/PerfilPage.dart';

class DrawerNavigation extends StatelessWidget {
  const DrawerNavigation({super.key, required this.email});

  final String email;

  @override
  Widget build(BuildContext context) {
    return Drawer(
        child: Column(
          //padding: EdgeInsets.zero,
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
              onTap: () => {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => HomePage(email: email)),
                )
              }
            ),

            ListTile(
              leading: const Icon(Icons.add_circle),
              title: const Text("Novo Pedido"),
              onTap: () => {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => MapaPage()),
                )
              }
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
            ),

            Expanded(
              child: Align(
                alignment: Alignment.bottomCenter,
                child: ListTile(
                  hoverColor: Colors.blue,
                  dense: true,
                  visualDensity: const VisualDensity(vertical: -4),
                  leading: const Icon(
                    Icons.logout,
                    color: Colors.black,
                  ),
                  title: const Text('Sair'),
                  onTap: () {
                    storage.delete(key: 'user');
                    Navigator.pushAndRemoveUntil(
                      context,
                      MaterialPageRoute(builder: (context) => BottomTabNavigator(selectedTab: 1)),
                      (route) => false //rota para voltar, no caso não é pra ter
                    );
                  },
                ),
              ),
            ),
          ],
        )
    );
  }
}