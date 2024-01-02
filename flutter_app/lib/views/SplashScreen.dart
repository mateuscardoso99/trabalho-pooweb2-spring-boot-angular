import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_app/views/login/Login.dart';

//StatefulWidget: os widgets Stateful são praticamente o oposto dos Stateless. Eles contêm estado e isso os torna mutáveis.
class SplashScreen extends StatefulWidget {
  const SplashScreen({super.key});

  @override  
  SplashScreenState createState() => SplashScreenState();  
}

class SplashScreenState extends State<SplashScreen> {  
  @override  
  void initState() {  
    super.initState();  
    Timer(const Duration(seconds: 5),  
            ()=>Navigator.pushReplacement(context,  
            MaterialPageRoute(builder: (context) => const LoginPage())  
         )  
    );  
  }

  //gera a tela
  @override  
  Widget build(BuildContext context) {  
    return Container(  
        color: Colors.yellow,  
        child:FlutterLogo(size:MediaQuery.of(context).size.height)  
    );  
  }  
}  