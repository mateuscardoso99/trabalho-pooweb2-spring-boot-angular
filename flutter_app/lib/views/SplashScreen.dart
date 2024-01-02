import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_app/views/Login.dart';

//StatefulWidget: os widgets Stateful são praticamente o oposto dos Stateless. Eles contêm estado e isso os torna mutáveis.
class SplashScreen extends StatefulWidget {
  const SplashScreen({super.key});

  @override  
  SplashScreenState createState() => SplashScreenState();  
}

class SplashScreenState extends State<LoginPage> {  
  @override  
  void initState() {  
    super.initState();  
    Timer(const Duration(seconds: 5),  
            ()=>Navigator.pushReplacement(context,  
            MaterialPageRoute(builder:  
                (context) => const FormWidget(title: 'Flutter Demo Home Page')  
            )  
         )  
    );  
  }
  @override  
  Widget build(BuildContext context) {  
    return Container(  
        color: Colors.yellow,  
        child:FlutterLogo(size:MediaQuery.of(context).size.height)  
    );  
  }  
}  