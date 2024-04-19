import 'dart:convert';

import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter_app/api.dart';
import 'package:flutter_app/components/HandleErrors.dart';
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

  final FocusNode _focusNodePassword = FocusNode();
  bool escondeSenha = true;

  //quando o state é desmontado, (tela é fechada)
  @override
  void dispose() {
    _focusNodePassword.dispose();
    emailController.dispose();
    passwordController.dispose();
    super.dispose();
  }

  //método build constrói a UI
  @override
  Widget build(BuildContext context) {
    //PODE ESCREVER CONDIÇÕES AQUI
    /*if (_boxLogin.get("loginStatus") ?? false) {
      return HomePage();
    }*/

    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
        //appBar: AppBar(
        // TRY THIS: Try changing the color here to a specific color (to
        // Colors.amber, perhaps?) and trigger a hot reload to see the AppBar
        // change color while the other colors stay the same.
        //backgroundColor: Theme.of(context).colorScheme.onPrimary,
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.

        //title: Text(widget.titulo),//recebido por parametro quando chamado por meio de LoginPage()
        //),
        body: SingleChildScrollView(
      child: Form(
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
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
              child: TextFormField(
                controller: emailController,
                decoration: InputDecoration(
                  labelText: "Email",
                  labelStyle: const TextStyle(color: Colors.red),
                  prefixIcon: const Icon(Icons.email, color: Colors.red),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(10),
                  ),
                  enabledBorder: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(10),
                      borderSide:
                          const BorderSide(color: Colors.red, width: 1.0)),
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
                focusNode: _focusNodePassword,
                obscureText: escondeSenha,
                decoration: InputDecoration(
                  labelText: "Senha",
                  labelStyle: const TextStyle(color: Colors.red),
                  prefixIcon:
                      const Icon(Icons.padding_rounded, color: Colors.red),
                  suffixIcon: IconButton(
                      onPressed: () {
                        setState(() {
                          escondeSenha = !escondeSenha;
                        });
                      },
                      icon: escondeSenha
                          ? const Icon(Icons.visibility_outlined,
                              color: Colors.red)
                          : const Icon(Icons.visibility_off_outlined,
                              color: Colors.red)),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(10),
                  ),
                  enabledBorder: OutlineInputBorder(
                      borderSide:
                          const BorderSide(color: Colors.red, width: 1.0),
                      borderRadius: BorderRadius.circular(10)),
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
              padding:
                  const EdgeInsets.symmetric(horizontal: 8, vertical: 16.0),
              child: Center(
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.red,
                    minimumSize: const Size.fromHeight(50),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(5),
                    ),
                  ),
                  onPressed: () async {
                    if (form.currentState?.validate() ?? false) {
                      final String email = emailController.text;
                      final String password = passwordController.text;
                      var resp = await AuthService().login(email, password);
                      final Map<String, dynamic> jsonResponse =
                          jsonDecode(utf8.decode(resp.bodyBytes));

                      if (resp.statusCode == 200) {
                        Token token = Token.fromJson(jsonResponse);
                        await storage.write(
                            key: "user", value: Token.serialize(token));
                        Navigator.pushReplacement(
                            context,
                            MaterialPageRoute(
                                builder: (context) => const HomePage()));
                      } else {
                        final String msg =
                            jsonResponse["message"] ?? "Ocorreu um erro";
                        final List<String> errorsMsg =
                            HandleErrors(response: jsonResponse).errors();

                        if (context.mounted) {
                          showDialog(
                              context: context,
                              //barrierDismissible: false, // user must tap button!
                              builder: (BuildContext context) {
                                return AlertDialog(
                                  title: Text(msg),
                                  shape: const RoundedRectangleBorder(
                                      borderRadius:
                                          BorderRadius.all(Radius.circular(5))),
                                  backgroundColor: Colors.white,
                                  content: SingleChildScrollView(
                                    child: ListBody(children: [
                                      for (var error in errorsMsg)
                                        Text(
                                          error.toString(),
                                          style: TextStyle(
                                              color: Colors.red[300],
                                              fontWeight: FontWeight.bold),
                                        )
                                    ]),
                                  ),
                                );
                              });
                        }
                      }
                    } else {
                      ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(
                            content: Text('Preencha todos os campos')),
                      );
                    }
                  },
                  child: const Text('ENTRAR',
                      style: TextStyle(
                          color: Colors.white, fontWeight: FontWeight.bold)),
                ),
              ),
            ),
            Padding(
              padding:
                  const EdgeInsets.symmetric(horizontal: 8, vertical: 16.0),
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
                          text: TextSpan(children: <TextSpan>[
                            TextSpan(
                              text: 'Cadastre-se',
                              style: const TextStyle(
                                  color: Colors.red,
                                  fontWeight: FontWeight.bold,
                                  decoration: TextDecoration.underline,
                                  fontSize: 17),
                              recognizer: TapGestureRecognizer()
                                ..onTap = () {
                                  Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                        builder: (context) =>
                                            const CriarContaPage(
                                                titulo: "Cadastro")),
                                  );
                                },
                            )
                          ]),
                        ))
                  ])),
            )
          ],
        ),
      ),
    ));
  }
}
