import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_app/components/BottomTabNavigator.dart';
import 'package:flutter_app/models/token.dart';
import 'package:flutter_app/views/SplashScreen.dart';
import 'package:flutter_app/views/portal-usuario/HomePage.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

const urlApi = 'http://localhost:8080';
final storage = FlutterSecureStorage();

void main() {
  runApp(const MyApp());
}

//StatelessWidget: Este tipo de widget não possibilita alterações dinâmicas, entenda-o como algo completamente estático. Eles são amplamente utilizados para a criação de estruturas não mutáveis nos aplicativos (telas, menus, imagens etc.), ou seja, tudo que não envolva entradas de dados dos usuários, acessos a APIs e coisas que mudem ao longo do processo.
class MyApp extends StatelessWidget {
  const MyApp({super.key});

  Future<String> get jwtOrEmpty async {
    var userStorage = await storage.read(key: "user");
    if(userStorage == null) return "";
    return userStorage;
  }

  //gera a tela
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // TRY THIS: Try running your application with "flutter run". You'll see
        // the application has a purple toolbar. Then, without quitting the app,
        // try changing the seedColor in the colorScheme below to Colors.green
        // and then invoke "hot reload" (save your changes or press the "hot
        // reload" button in a Flutter-supported IDE, or press "r" if you used
        // the command line to start the app).
        //
        // Notice that the counter didn't reset back to zero; the application
        // state is not lost during the reload. To reset the state, use hot
        // restart instead.
        //
        // This works for code too, not just values: Most code changes can be
        // tested with just a hot reload.
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.blue),
        useMaterial3: true,
      ),
      home: FutureBuilder(
        future: jwtOrEmpty,            
        builder: (context, snapshot) {
          if(!snapshot.hasData) return const CircularProgressIndicator();
          if(snapshot.data != "") {
            var userStorage = Token.deserialize(snapshot.data!);
            //var jwt = str?.split(".");

            if(userStorage == null) {
              return const SplashScreen();
            } else {
              var payload = json.decode(ascii.decode(base64.decode(base64.normalize(userStorage.token))));
              if(DateTime.fromMillisecondsSinceEpoch(payload["exp"]*1000).isAfter(DateTime.now())) {
                return const HomePage(email: "teste");
              } else {
                return BottomTabNavigator(selectedTab: 1);
              }
            }
          } else {
            return const SplashScreen();
          }
        }
      ),
    );
  }
}