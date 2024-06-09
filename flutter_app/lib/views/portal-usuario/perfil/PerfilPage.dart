import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_app/components/DrawerNavigation.dart';
import 'package:flutter_app/components/HandleErrors.dart';
import 'package:flutter_app/models/endereco.dart';
import 'package:flutter_app/models/token.dart';
import 'package:flutter_app/models/usuario.dart';
import 'package:flutter_app/request/cadastro_cliente.dart';
import 'package:flutter_app/services/auth_service.dart';
import 'package:flutter_app/services/cliente_service.dart';

class PerfilPage extends StatefulWidget {
  const PerfilPage({super.key, required this.titulo});
  final String titulo;

  @override
  PerfilPageState createState() => PerfilPageState();
}

class PerfilPageState extends State<PerfilPage> {
  final form = GlobalKey<FormState>();
  String nome = '';
  String email = '';
  String password = '';
  String cidade = '';
  String uf = '';
  String bairro = '';
  String rua = '';
  String numero = '';
  String? complemento = '';

  final FocusNode focusNodePassword = FocusNode();
  bool escondeSenha = true;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          iconTheme: const IconThemeData(color: Colors.white),
          backgroundColor: Colors.red,
          title: Text(widget.titulo,
              style: const TextStyle(
                  color: Colors
                      .white)), //recebido por parametro quando chamado por meio de LoginPage()
        ),
        drawer: const DrawerNavigation(),
        body: SingleChildScrollView(
          child: Center(
            child: FutureBuilder<String>(
                future: AuthService().getUser,
                builder: (context, snapshot) {
                  if (snapshot.connectionState == ConnectionState.waiting) {
                    return const Center(
                      child: CircularProgressIndicator(),
                    );
                  } else if (snapshot.hasData) {
                    final Usuario usuario =
                        Token.deserialize(snapshot.data!)!.usuario;

                    nome = usuario.nome;
                    email = usuario.email;
                    cidade = usuario.endereco.cidade;
                    uf = usuario.endereco.uf;
                    bairro = usuario.endereco.bairro;
                    rua = usuario.endereco.rua;
                    numero = usuario.endereco.numero;
                    complemento = usuario.endereco.complemento ?? '';

                    return SingleChildScrollView(
                      //quando conteúdo da tela é maior que o tamanho dela, permite rolar
                      child: Form(
                        key: form,
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.center,
                          children: <Widget>[
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 8, vertical: 16),
                              child: TextFormField(
                                decoration: InputDecoration(
                                  labelText: "Nome",
                                  prefixIcon: const Icon(Icons.person),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  enabledBorder: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(10)),
                                ),
                                initialValue: nome,
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'informe o nome';
                                  }
                                  return null;
                                },
                                onChanged: (value) {
                                  nome = value;
                                },
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 8, vertical: 16),
                              child: TextFormField(
                                decoration: InputDecoration(
                                  labelText: "Email",
                                  prefixIcon: const Icon(Icons.email),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  enabledBorder: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(10)),
                                ),
                                initialValue: email,
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'informe o email';
                                  }
                                  return null;
                                },
                                onChanged: (value) {
                                  email = value;
                                },
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 8, vertical: 16),
                              child: TextFormField(
                                obscureText: escondeSenha,
                                focusNode: focusNodePassword,
                                decoration: InputDecoration(
                                  labelText: "Senha",
                                  prefixIcon: const Icon(Icons.padding_rounded),
                                  suffixIcon: IconButton(
                                      onPressed: () {
                                        setState(() {
                                          escondeSenha = !escondeSenha;
                                        });
                                      },
                                      icon: escondeSenha
                                          ? const Icon(Icons.visibility_outlined)
                                          : const Icon(
                                              Icons.visibility_off_outlined)),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  enabledBorder: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(10)),
                                ),
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'informe a senha';
                                  }
                                  return null;
                                },
                                onChanged: (value) {
                                  password = value;
                                },
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 8, vertical: 16),
                              child: TextFormField(
                                decoration: InputDecoration(
                                  labelText: "Cidade",
                                  prefixIcon: const Icon(Icons.location_city),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  enabledBorder: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(10)),
                                ),
                                initialValue: cidade,
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'informe a cidade';
                                  }
                                  return null;
                                },
                                onChanged: (value) {
                                  cidade = value;
                                },
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 8, vertical: 16),
                              child: TextFormField(
                                decoration: InputDecoration(
                                  labelText: "UF",
                                  prefixIcon: const Icon(Icons.nature_outlined),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  enabledBorder: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(10)),
                                ),
                                initialValue: uf,
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'informe a UF';
                                  }
                                  if(value.length != 2){
                                    return 'tamanho deve ser 2';
                                  }
                                  return null;
                                },
                                onChanged: (value) {
                                  uf = value;
                                },
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 8, vertical: 16),
                              child: TextFormField(
                                decoration: InputDecoration(
                                  labelText: "Bairro",
                                  prefixIcon: const Icon(Icons.highlight),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  enabledBorder: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(10)),
                                ),
                                initialValue: bairro,
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'informe o Bairro';
                                  }
                                  return null;
                                },
                                onChanged: (value) {
                                  bairro = value;
                                },
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 8, vertical: 16),
                              child: TextFormField(
                                decoration: InputDecoration(
                                  labelText: "Rua",
                                  prefixIcon: const Icon(Icons.rocket),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  enabledBorder: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(10)),
                                ),
                                initialValue: rua,
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'informe a Rua';
                                  }
                                  return null;
                                },
                                onChanged: (value) {
                                  rua = value;
                                },
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 8, vertical: 16),
                              child: TextFormField(
                                decoration: InputDecoration(
                                  labelText: "Número",
                                  prefixIcon: const Icon(Icons.numbers),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  enabledBorder: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(10)),
                                ),
                                initialValue: numero,
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'informe o Número';
                                  }
                                  if(value.length > 20){
                                    return 'limite 20 caracteres';
                                  }
                                  return null;
                                },
                                onChanged: (value) {
                                  numero = value;
                                },
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 8, vertical: 16),
                              child: TextFormField(
                                  decoration: InputDecoration(
                                    labelText: "Complemento",
                                    prefixIcon: const Icon(Icons.house),
                                    border: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(10),
                                    ),
                                    enabledBorder: OutlineInputBorder(
                                        borderRadius: BorderRadius.circular(10)),
                                  ),
                                  initialValue: complemento,
                                  onChanged: (value) {
                                    complemento = value;
                                  }),
                            ),
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 8, vertical: 16.0),
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
                                      /*Navigator.push(
                                          context,
                                          MaterialPageRoute(
                                            builder: (context) => HomePage()
                                          ),
                                        );*/
                                      var endereco = Endereco(
                                          cidade: cidade,
                                          uf: uf,
                                          bairro: bairro,
                                          rua: rua,
                                          numero: numero,
                                          latitude: '',
                                          longitude: '');
                                      var dados = CadastroClienteRequest(
                                          nome: nome,
                                          email: email,
                                          senha: password,
                                          endereco: endereco);
                                      var response =
                                          await ClienteService().update(dados);
                                      final Map<String, dynamic> jsonResponse =
                                          jsonDecode(
                                              utf8.decode(response.bodyBytes));

                                      if (response.statusCode == 200) {
                                        if (context.mounted) {
                                          showDialog(
                                            context: context,
                                            builder: (context) =>
                                                const AlertDialog(
                                                    title: Text("Sucesso"),
                                                    shape:
                                                        RoundedRectangleBorder(
                                                            borderRadius:
                                                                BorderRadius.all(
                                                                    Radius
                                                                        .circular(
                                                                            5))),
                                                    backgroundColor: Colors.white,
                                                    content: Column(
                                                      mainAxisSize:
                                                          MainAxisSize.min,
                                                      children: [
                                                        Text(
                                                            "perfil atualizado com sucesso.",
                                                            style: TextStyle(
                                                                color:
                                                                    Colors.green,
                                                                fontWeight:
                                                                    FontWeight
                                                                        .bold,
                                                                fontSize: 20))
                                                      ],
                                                    )),
                                          );
                                        }
                                      } else {
                                        final String msg =
                                            jsonResponse["message"] ??
                                                "Ocorreu um erro";
                                        final List<String> errorsMsg =
                                            HandleErrors(response: jsonResponse)
                                                .errors();

                                        if (context.mounted) {
                                          showDialog(
                                              context: context,
                                              //barrierDismissible: false, // user must tap button!
                                              builder: (BuildContext context) {
                                                return AlertDialog(
                                                  title: Text(msg),
                                                  shape:
                                                      const RoundedRectangleBorder(
                                                          borderRadius:
                                                              BorderRadius.all(
                                                                  Radius.circular(
                                                                      5))),
                                                  backgroundColor: Colors.white,
                                                  content: SingleChildScrollView(
                                                    child: ListBody(children: [
                                                      for (var error in errorsMsg)
                                                        Text(
                                                          error.toString(),
                                                          style: TextStyle(
                                                              color:
                                                                  Colors.red[300],
                                                              fontWeight:
                                                                  FontWeight
                                                                      .bold),
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
                                            content:
                                                Text('Preencha todos os campos')),
                                      );
                                    }
                                  },
                                  child: const Text('ATUALIZAR',
                                      style: TextStyle(
                                          color: Colors.white,
                                          fontWeight: FontWeight.bold)),
                                ),
                              ),
                            ),
                          ],
                        ),
                      ),
                    );
                  } else {
                    // if no data, show simple Text
                    return const Text("No data available");
                  }
                }),
          ),
        )
      );
  }
}
