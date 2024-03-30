
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
  late String title;

  final List _pages = [
    const MapaPage(showTitle: false),
    const LoginPage(titulo: "Login")
  ];

  @override
  void initState(){
    super.initState();
    setTitle();
  }

  setTitle(){
    if(widget.selectedTab == 0){
      title = "Escolha um estabelecimento";
    }
    else{
      title = "Login";
    }
  }

  _changeTab(int index) {
    setState(() {
      widget.selectedTab = index;
      setTitle();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.red,
        title: Text(
          title,
          style: const TextStyle(
            color: Colors.white
          )
        ),
        centerTitle: true,
      ),
      body: _pages[widget.selectedTab],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: widget.selectedTab,
        onTap: (index) => _changeTab(index),
        backgroundColor: Colors.red,
        selectedLabelStyle: const TextStyle(fontWeight: FontWeight.bold),
        selectedItemColor: Colors.yellow[600],
        unselectedItemColor: Colors.white,
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.map), label: "Mapa"),
          BottomNavigationBarItem(icon: Icon(Icons.person), label: "Login")
        ],
      ),
    );
  }
}