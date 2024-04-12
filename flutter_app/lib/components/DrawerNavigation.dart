import 'package:flutter/material.dart';
import 'package:flutter_app/api.dart';
import 'package:flutter_app/components/BottomTabNavigator.dart';
import 'package:flutter_app/models/token.dart';
import 'package:flutter_app/models/usuario.dart';
import 'package:flutter_app/services/auth_service.dart';
import 'package:flutter_app/views/portal-usuario/HomePage.dart';
import 'package:flutter_app/views/portal-usuario/pedido/MapaPage.dart';
import 'package:flutter_app/views/portal-usuario/perfil/PerfilPage.dart';

class DrawerNavigation extends StatefulWidget {
  const DrawerNavigation({super.key});

  @override
  DrawerNavigationState createState() => DrawerNavigationState();
}

class DrawerNavigationState extends State<DrawerNavigation> {
  late Future<String> userStorage;

  @override
  void initState() {
    super.initState();
    userStorage = AuthService().getUser;
  }

  @override
  Widget build(BuildContext context) {
    return Drawer(
        child: FutureBuilder(
            future: userStorage,
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return const Center(
                  child: CircularProgressIndicator(),
                );
              } else if (snapshot.hasData) {
                final Usuario? usuario =
                    Token.deserialize(snapshot.data!)!.usuario;

                if (usuario == null) {
                  return const Column();
                }

                return Column(
                  //padding: EdgeInsets.zero,
                  children: <Widget>[
                    UserAccountsDrawerHeader(
                      accountEmail: Text(usuario.email),
                      accountName: Text(usuario.nome),
                      currentAccountPicture: CircleAvatar(
                        child: Text(usuario.nome),
                      ),
                      decoration: const BoxDecoration(
                        color: Color.fromARGB(255, 119, 0, 0),
                      ),
                    ),
                    ListTile(
                        leading: const Icon(Icons.shopping_basket),
                        title: const Text("Pedidos"),
                        onTap: () => {
                              Navigator.push(
                                context,
                                MaterialPageRoute(
                                    builder: (context) =>
                                        HomePage(email: usuario.email)),
                              )
                            }),
                    ListTile(
                        leading: const Icon(Icons.add_circle),
                        title: const Text("Novo Pedido"),
                        onTap: () => {
                              Navigator.push(
                                context,
                                MaterialPageRoute(
                                    builder: (context) =>
                                        const MapaPage(showTitle: true)),
                              )
                            }),
                    ListTile(
                        leading: const Icon(Icons.person),
                        title: const Text("Perfil"),
                        onTap: () => {
                              Navigator.push(
                                context,
                                MaterialPageRoute(
                                    builder: (context) => const PerfilPage(
                                        titulo: "Editar Perfil")),
                              )
                            }),
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
                                MaterialPageRoute(
                                    builder: (context) =>
                                        BottomTabNavigator(selectedTab: 1)),
                                (route) =>
                                    false //rota para voltar, no caso não é pra ter
                                );
                          },
                        ),
                      ),
                    ),
                  ],
                );
              } else {
                return const Column();
              }
            }));
  }
}
