import 'dart:convert';

import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter_app/components/BottomTabNavigator.dart';
import 'package:flutter_app/components/HandleErrors.dart';
import 'package:flutter_app/models/endereco.dart';
import 'package:flutter_app/request/cadastro_cliente.dart';
import 'package:flutter_app/services/auth_service.dart';

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
  TextEditingController cidadeController = TextEditingController();
  TextEditingController ufController = TextEditingController();
  TextEditingController bairroController = TextEditingController();
  TextEditingController ruaController = TextEditingController();
  TextEditingController numeroController = TextEditingController();
  TextEditingController complementoController = TextEditingController();
  final FocusNode _focusPassword = FocusNode();
  bool escondeSenha = true;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        iconTheme: const IconThemeData(color: Colors.white),
        backgroundColor: Colors.red,
        title: Text(
          widget.titulo,
          style: const TextStyle(color: Colors.white),
        ), //recebido por parametro quando chamado por meio de LoginPage()
        centerTitle: true,
      ),
      body: SingleChildScrollView(
        child: Form(
          key: form,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Padding(
                padding:
                    const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                child: TextFormField(
                  controller: nomeController,
                  decoration: InputDecoration(
                    labelText: "Nome",
                    labelStyle: const TextStyle(color: Colors.red),
                    prefixIcon: const Icon(Icons.person, color: Colors.red),
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
                      return 'informe o nome';
                    }
                    return null;
                  },
                ),
              ),
              Padding(
                padding:
                    const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
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
                padding:
                    const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                child: TextFormField(
                  controller: passwordController,
                  focusNode: _focusPassword,
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
                  padding: const EdgeInsets.all(10),
                  child: InputDecorator(
                    decoration: InputDecoration(
                        labelText: 'Endereço',
                        labelStyle: TextStyle(
                            color: Colors.red[600],
                            fontSize: 20,
                            fontWeight: FontWeight.bold),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10.0),
                        ),
                        enabledBorder: OutlineInputBorder(
                            borderSide:
                                const BorderSide(color: Colors.red, width: 1.0),
                            borderRadius: BorderRadius.circular(10))),
                    child: Column(children: [
                      Padding(
                        padding: const EdgeInsets.symmetric(
                            horizontal: 8, vertical: 16),
                        child: TextFormField(
                          controller: cidadeController,
                          decoration: InputDecoration(
                            labelText: "Cidade",
                            labelStyle: const TextStyle(color: Colors.red),
                            prefixIcon: const Icon(Icons.location_city,
                                color: Colors.red),
                            border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(10),
                            ),
                            enabledBorder: OutlineInputBorder(
                                borderSide: const BorderSide(
                                    color: Colors.red, width: 1.0),
                                borderRadius: BorderRadius.circular(10)),
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
                        padding: const EdgeInsets.symmetric(
                            horizontal: 8, vertical: 16),
                        child: TextFormField(
                          controller: ufController,
                          decoration: InputDecoration(
                            labelText: "UF",
                            labelStyle: const TextStyle(color: Colors.red),
                            prefixIcon: const Icon(Icons.nature_outlined,
                                color: Colors.red),
                            border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(10),
                            ),
                            enabledBorder: OutlineInputBorder(
                                borderSide: const BorderSide(
                                    color: Colors.red, width: 1.0),
                                borderRadius: BorderRadius.circular(10)),
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
                        padding: const EdgeInsets.symmetric(
                            horizontal: 8, vertical: 16),
                        child: TextFormField(
                          controller: bairroController,
                          decoration: InputDecoration(
                            labelText: "Bairro",
                            labelStyle: const TextStyle(color: Colors.red),
                            prefixIcon:
                                const Icon(Icons.highlight, color: Colors.red),
                            border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(10),
                            ),
                            enabledBorder: OutlineInputBorder(
                                borderSide: const BorderSide(
                                    color: Colors.red, width: 1.0),
                                borderRadius: BorderRadius.circular(10)),
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
                        padding: const EdgeInsets.symmetric(
                            horizontal: 8, vertical: 16),
                        child: TextFormField(
                          controller: ruaController,
                          decoration: InputDecoration(
                            labelText: "Rua",
                            labelStyle: const TextStyle(color: Colors.red),
                            prefixIcon:
                                const Icon(Icons.rocket, color: Colors.red),
                            border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(10),
                            ),
                            enabledBorder: OutlineInputBorder(
                                borderSide: const BorderSide(
                                    color: Colors.red, width: 1.0),
                                borderRadius: BorderRadius.circular(10)),
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
                        padding: const EdgeInsets.symmetric(
                            horizontal: 8, vertical: 16),
                        child: TextFormField(
                          controller: numeroController,
                          decoration: InputDecoration(
                            labelText: "Número",
                            labelStyle: const TextStyle(color: Colors.red),
                            prefixIcon:
                                const Icon(Icons.numbers, color: Colors.red),
                            border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(10),
                            ),
                            enabledBorder: OutlineInputBorder(
                                borderSide: const BorderSide(
                                    color: Colors.red, width: 1.0),
                                borderRadius: BorderRadius.circular(10)),
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
                        padding: const EdgeInsets.symmetric(
                            horizontal: 8, vertical: 16),
                        child: TextFormField(
                          controller: complementoController,
                          decoration: InputDecoration(
                            labelText: "Complemento",
                            labelStyle: const TextStyle(color: Colors.red),
                            prefixIcon:
                                const Icon(Icons.house, color: Colors.red),
                            border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(10),
                            ),
                            enabledBorder: OutlineInputBorder(
                                borderSide: const BorderSide(
                                    color: Colors.red, width: 1.0),
                                borderRadius: BorderRadius.circular(10)),
                          ),
                        ),
                      ),
                    ]),
                  )),
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
                      if (form.currentState!.validate()) {
                        /*Navigator.push(
                                context,
                                MaterialPageRoute(
                                  builder: (context) => HomePage(email: emailController.text)
                                ),
                              );*/

                        var endereco = Endereco(
                            cidade: cidadeController.text,
                            uf: ufController.text,
                            bairro: bairroController.text,
                            rua: ruaController.text,
                            numero: numeroController.text,
                            complemento: complementoController.text);
                        var dados = CadastroClienteRequest(
                            nome: nomeController.text,
                            email: emailController.text,
                            senha: passwordController.text,
                            endereco: endereco);
                        var response = await AuthService().signUp(dados);
                        final Map<String, dynamic> jsonResponse =
                            jsonDecode(utf8.decode(response.bodyBytes));

                        if (response.statusCode == 201) {
                          showDialog(
                            context: context,
                            builder: (context) => const AlertDialog(
                                title: Text("Sucesso"),
                                content: Text("Conta criada com sucesso.")),
                          );
                        } else {
                          final String msg =
                              jsonResponse["message"] ?? "Ocorreu um erro";
                          final List<String> errorsMsg =
                              HandleErrors(response: jsonResponse).errors();

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
                      } else {
                        ScaffoldMessenger.of(context).showSnackBar(
                          const SnackBar(
                              content: Text('Preencha todos os campos')),
                        );
                      }
                    },
                    child: const Text('CRIAR CONTA',
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
                      Container(
                          margin: const EdgeInsets.all(10),
                          child: RichText(
                            text: TextSpan(children: <TextSpan>[
                              TextSpan(
                                text: 'Fazer login',
                                style: const TextStyle(
                                    color: Colors.blue,
                                    fontWeight: FontWeight.bold,
                                    decoration: TextDecoration.underline,
                                    fontSize: 17),
                                recognizer: TapGestureRecognizer()
                                  ..onTap = () {
                                    Navigator.pushAndRemoveUntil(
                                        context,
                                        MaterialPageRoute(
                                            builder: (context) =>
                                                BottomTabNavigator(
                                                    selectedTab: 1)),
                                        (route) => false);
                                  },
                              )
                            ]),
                          ))
                    ])),
              )
            ],
          ),
        ),
      ),
    );
  }
}
