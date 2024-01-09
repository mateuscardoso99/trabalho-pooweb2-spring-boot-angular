import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter_app/views/login/LoginPage.dart';
import 'package:flutter_app/views/portal-usuario/HomePage.dart';

class CriarContaPage extends StatefulWidget {
  const CriarContaPage({super.key, required this.titulo});
  final String titulo;

  @override
  CriarContaPageState createState() => CriarContaPageState();
}

class CriarContaPageState extends State<CriarContaPage> {
  final form = GlobalKey<FormState>();
  TextEditingController nomeController = TextEditingController();
  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.titulo),//recebido por parametro quando chamado por meio de LoginPage()
      ),
      body: Form(
        key: form,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
              child: TextFormField(
                controller: nomeController,
                decoration: const InputDecoration(
                  border: OutlineInputBorder(), labelText: "Nome"
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'informe o nome';
                  }
                  return null;
                },
              ),
            ),

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
                  border: OutlineInputBorder(), labelText: "Senha"
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
                  onPressed: () {
                    if (form.currentState!.validate()) {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => HomePage(email: emailController.text)
                        ),
                      );
                    } else {
                      ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(
                          content: Text('Preencha todos os campos')),
                        );
                    }
                  },
                  child: const Text('CRIAR CONTA'),
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
                    Container(
                      margin: const EdgeInsets.all(10),
                      child: RichText(
                        text: TextSpan(
                          children: <TextSpan>[
                            TextSpan(
                              text: 'ir para login',
                              style: const TextStyle(
                                color: Colors.blue,
                                decoration: TextDecoration.underline
                              ),
                              recognizer: TapGestureRecognizer()..onTap = () {
                                Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                    builder: (context) => const LoginPage(titulo: "Login")
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