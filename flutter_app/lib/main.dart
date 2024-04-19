import 'package:flutter/material.dart';
import 'package:flutter_app/models/token.dart';
import 'package:flutter_app/services/auth_service.dart';
import 'package:flutter_app/views/SplashScreen.dart';
import 'package:flutter_app/views/portal-usuario/HomePage.dart';

void main() {
  runApp(const MyApp());
}

//StatelessWidget: Este tipo de widget não possibilita alterações dinâmicas, entenda-o como algo completamente estático. Eles são amplamente utilizados para a criação de estruturas não mutáveis nos aplicativos (telas, menus, imagens etc.), ou seja, tudo que não envolva entradas de dados dos usuários, acessos a APIs e coisas que mudem ao longo do processo.
class MyApp extends StatelessWidget {
  const MyApp({super.key});

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
        //colorScheme: ColorScheme.fromSeed(seedColor: Colors.red),
        useMaterial3: true,
      ),
      home: FutureBuilder(
          future: AuthService().getUser,
          builder: (context, snapshot) {
            if (!snapshot.hasData) return const CircularProgressIndicator();
            if (snapshot.data != '') {
              var userStorage = Token.deserialize(snapshot.data!);
              //var jwt = str?.split(".");

              if (userStorage == null || userStorage.token == '') {
                return const SplashScreen();
              } else {
                //var payload = json.decode(ascii.decode(base64.decode(base64.normalize(userStorage.token))));
                //if(DateTime.fromMillisecondsSinceEpoch(payload["exp"]*1000).isAfter(DateTime.now())) {
                return const HomePage();
                //} else {
                // return BottomTabNavigator(selectedTab: 1);
                //}
              }
            } else {
              return const SplashScreen();
            }
          }),
    );
  }
}
