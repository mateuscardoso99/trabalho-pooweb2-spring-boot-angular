import 'dart:convert';

import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter_app/main.dart';
import 'package:flutter_app/models/token.dart';
import 'package:flutter_app/views/portal-usuario/HomePage.dart';
import 'package:flutter_app/views/portal-usuario/criar-conta/CriarContaPage.dart';
import 'package:flutter_app/services/auth_service.dart';

//StatefulWidget: os widgets Stateful são praticamente o oposto dos Stateless. Eles contêm estado e isso os torna mutáveis.
//LoginPage é um Widget por isso tem um estado associado a ele
class LoginPage extends StatefulWidget {
  const LoginPage({super.key, required this.titulo});
  final String titulo;

  @override  
  LoginState createState() => LoginState();  
}

//setState(): função integrada no Flutter que permite aos desenvolvedores atualizar o estado de um widget específico e causar uma nova renderização da UI, comunica ao Flutter que ele precisa reconstruir a tela para que a alteração seja exibida corretamente.
class LoginState extends State<LoginPage> {
  //criando uma chave para um formulário. No nosso contexto, é o formulário de login. Você o está criando para identificar o formulário de maneira exclusiva. Está definido como final, para que não mude.
  final form = GlobalKey<FormState>();

  //Um controller em nosso contexto é usado para ler os valores da entrada. Usando um controlador, você poderá controlar o componente associado.
  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();


  //método build constrói a UI
  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      appBar: AppBar(
        // TRY THIS: Try changing the color here to a specific color (to
        // Colors.amber, perhaps?) and trigger a hot reload to see the AppBar
        // change color while the other colors stay the same.
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.

        title: Text(widget.titulo),//recebido por parametro quando chamado por meio de LoginPage()
      ),
      body: Form(
        key: form,
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent.
        child: Column(
          // Column is also a layout widget. It takes a list of children and
          // arranges them vertically. By default, it sizes itself to fit its
          // children horizontally, and tries to be as tall as its parent.
          //
          // Column has various properties to control how it sizes itself and
          // how it positions its children. Here we use mainAxisAlignment to
          // center the children vertically; the main axis here is the vertical
          // axis because Columns are vertical (the cross axis would be
          // horizontal).
          //
          // TRY THIS: Invoke "debug painting" (choose the "Toggle Debug Paint"
          // action in the IDE, or press "p" in the console), to see the
          // wireframe for each widget.
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
              child: TextFormField(
                controller: emailController,
                decoration: const InputDecoration(
                  border: OutlineInputBorder(), labelText: "Email"
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'informe o email';
                  }
                  return null;
                },
              ),
            ),

            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
              child: TextFormField(
                controller: passwordController,
                obscureText: true,
                decoration: const InputDecoration(
                  border: OutlineInputBorder(), labelText: "Password"
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'informe a senha';
                  }
                  return null;
                },
              ),
            ),

            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16.0),
              child: Center(
                child: ElevatedButton(
                  onPressed: () async {
                    //if (emailController.text == "joao@gmail.com" && passwordController.text == "1234") {
                      
                    // } else {
                    //   ScaffoldMessenger.of(context).showSnackBar(
                    //     const SnackBar(
                    //       content: Text('Email ou senha incorretos')),
                    //     );
                    // }

                    var email = emailController.text;
                    var password = passwordController.text;
                    var resp = await AuthService().login("joao@gmail.com", "12345");
                    if(resp.statusCode == 200) {
                      Token token = Token.fromJson(jsonDecode(resp.body));
                      storage.write(key: "user", value: Token.serialize(token));
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => HomePage(email: emailController.text)
                        )
                      );
                    } else {
                      showDialog(
                        context: context,
                        builder: (context) =>
                          const AlertDialog(
                            title: Text("Erro"),
                            content: Text("email ou senha incorretos")
                          )
                      );
                    }
                  },
                  child: const Text('ENTRAR'),
                ),
              ),
            ),

            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16.0),
              child: Center(
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  mainAxisSize: MainAxisSize.max,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  verticalDirection: VerticalDirection.down,
                  textDirection: TextDirection.ltr,
                  children: [
                    const Text('Não tem uma conta?'),
                    Container(
                      margin: const EdgeInsets.all(10),
                      child: RichText(
                        text: TextSpan(
                          children: <TextSpan>[
                            TextSpan(
                              text: 'Cadastre-se',
                              style: const TextStyle(
                                color: Colors.blue,
                                decoration: TextDecoration.underline
                              ),
                              recognizer: TapGestureRecognizer()..onTap = () {
                                Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                    builder: (context) => const CriarContaPage(titulo: "Criar conta")
                                  ),
                                );
                              },
                            )
                          ]
                        ),
                        
                        
                      )
                    )
                  ]
                )
              ),
            )
          ],
        ),
      ),
    );
  }
}