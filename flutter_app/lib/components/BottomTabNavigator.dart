
import 'package:flutter/material.dart';
import 'package:flutter_app/views/login/LoginPage.dart';
import 'package:flutter_app/views/portal-usuario/pedido/MapaPage.dart';

class BottomTabNavigator extends StatefulWidget {
  BottomTabNavigator({Key? key, required this.selectedTab}) : super(key: key);

  int selectedTab = 0;

  @override
  BottomTabNavigatorState createState() =>
      BottomTabNavigatorState();
}

class BottomTabNavigatorState extends State<BottomTabNavigator> {

  final List _pages = [
    MapaPage(),
    const LoginPage(titulo: "Login")
  ];

  _changeTab(int index) {
    setState(() {
      widget.selectedTab = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: _pages[widget.selectedTab],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: widget.selectedTab,
        onTap: (index) => _changeTab(index),
        selectedItemColor: Colors.red,
        unselectedItemColor: Colors.grey,
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.map), label: "Mapa"),
          BottomNavigationBarItem(icon: Icon(Icons.person), label: "Login")
        ],
      ),
    );
  }
}