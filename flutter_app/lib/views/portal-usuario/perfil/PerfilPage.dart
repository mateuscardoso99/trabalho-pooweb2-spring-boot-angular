import 'package:flutter/material.dart';
import 'package:flutter_app/components/DrawerNavigation.dart';
import 'package:flutter_app/views/portal-usuario/HomePage.dart';

class PerfilPage extends StatefulWidget {
  const PerfilPage({super.key, required this.titulo});
  final String titulo;
  
  @override
  PerfilPageState createState() => PerfilPageState();
}

class PerfilPageState extends State<PerfilPage> {
  final form = GlobalKey<FormState>();
  TextEditingController nomeController = TextEditingController();
  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  TextEditingController cidadeController = TextEditingController();
  TextEditingController ufController = TextEditingController();
  TextEditingController bairroController = TextEditingController();
  TextEditingController ruaController = TextEditingController();
  TextEditingController numeroController = TextEditingController();
  TextEditingController complementoController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.titulo),//recebido por parametro quando chamado por meio de LoginPage()
      ),
      drawer: const DrawerNavigation(email: "email"),
      body: Center(
        child: SingleChildScrollView( //quando conteúdo da tela é maior que o tamanho dela, permite rolar
          child: Form(
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
                  padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                  child: TextFormField(
                    controller: cidadeController,
                    decoration: const InputDecoration(
                      border: OutlineInputBorder(), labelText: "Cidade"
                    ),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'informe a cidade';
                      }
                      return null;
                    },
                  ),
                ),

                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                  child: TextFormField(
                    controller: ufController,
                    decoration: const InputDecoration(
                      border: OutlineInputBorder(), labelText: "UF"
                    ),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'informe a UF';
                      }
                      return null;
                    },
                  ),
                ),

                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                  child: TextFormField(
                    controller: bairroController,
                    decoration: const InputDecoration(
                      border: OutlineInputBorder(), labelText: "Bairro"
                    ),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'informe o Bairro';
                      }
                      return null;
                    },
                  ),
                ),

                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                  child: TextFormField(
                    controller: ruaController,
                    decoration: const InputDecoration(
                      border: OutlineInputBorder(), labelText: "Rua"
                    ),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'informe a Rua';
                      }
                      return null;
                    },
                  ),
                ),

                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                  child: TextFormField(
                    controller: numeroController,
                    decoration: const InputDecoration(
                      border: OutlineInputBorder(), labelText: "Número"
                    ),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'informe o Número';
                      }
                      return null;
                    },
                  ),
                ),

                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                  child: TextFormField(
                    controller: complementoController,
                    decoration: const InputDecoration(
                      border: OutlineInputBorder(), labelText: "Complemento"
                    ),
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
                      child: const Text('ATUALIZAR'),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      )
    );
  }
}