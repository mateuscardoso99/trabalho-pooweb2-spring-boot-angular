import 'package:flutter/material.dart';
import 'package:flutter_app/components/DrawerNavigation.dart';
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
  bool escondeSenha = true;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        iconTheme: const IconThemeData(color: Colors.white),
        backgroundColor: Colors.red,
        title: Text(
          widget.titulo,
          style: const TextStyle(
            color: Colors.white
          )
        ),//recebido por parametro quando chamado por meio de LoginPage()
      ),
      drawer: const DrawerNavigation(),
      body: Center(
        child: FutureBuilder<String>(
          future: AuthService().getUser, 
          builder: (context, snapshot) {
            if(snapshot.connectionState == ConnectionState.waiting){
              return const Center(
                child: CircularProgressIndicator(),
              );
            }
            else if(snapshot.hasData){
              final Usuario usuario = Token.deserialize(snapshot.data!)!.usuario;

              return SingleChildScrollView( //quando conteúdo da tela é maior que o tamanho dela, permite rolar
                      child: Form(
                        key: form,
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.center,
                          children: <Widget>[
                            Padding(
                              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                              child: TextFormField(
                                decoration: InputDecoration(
                                  labelText: "Nome",
                                  prefixIcon: const Icon(Icons.email),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  enabledBorder: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10)
                                  ),
                                ),
                                initialValue: usuario.nome,
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'informe o nome';
                                  }
                                  return null;
                                },
                                onChanged: (value) {
                                  setState(() {
                                    nome = value;
                                  });
                                },
                              ),
                            ),

                            Padding(
                              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                              child: TextFormField(
                                decoration: InputDecoration(
                                  labelText: "Email",
                                  prefixIcon: const Icon(Icons.email),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  enabledBorder: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10)
                                  ),
                                ),
                                initialValue: usuario.email,
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'informe o email';
                                  }
                                  return null;
                                },
                                onChanged: (value) {
                                  setState(() {
                                    email = value;
                                  });
                                },
                              ),
                            ),

                            Padding(
                              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                              child: TextFormField(
                                obscureText: true,
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
                                        : const Icon(Icons.visibility_off_outlined)
                                  ),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  enabledBorder: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10)
                                  ),
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
                              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                              child: TextFormField(
                                decoration: InputDecoration(
                                  labelText: "Cidade",
                                  prefixIcon: const Icon(Icons.location_city),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  enabledBorder: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10)
                                  ),
                                ),
                                initialValue: usuario.endereco.cidade,
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'informe a cidade';
                                  }
                                  return null;
                                },
                                onChanged: (value) {
                                  setState(() {
                                    cidade = value;
                                  });
                                },
                              ),
                            ),

                            Padding(
                              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                              child: TextFormField(
                                decoration: InputDecoration(
                                  labelText: "UF",
                                  prefixIcon: const Icon(Icons.nature_outlined),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  enabledBorder: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10)
                                  ),
                                ),
                                initialValue: usuario.endereco.uf,
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'informe a UF';
                                  }
                                  return null;
                                },
                                onChanged: (value) {
                                  uf = value;
                                },
                              ),
                            ),

                            Padding(
                              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                              child: TextFormField(
                                decoration: InputDecoration(
                                  labelText: "Bairro",
                                  prefixIcon: const Icon(Icons.highlight),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  enabledBorder: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10)
                                  ),
                                ),
                                initialValue: usuario.endereco.bairro,
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
                              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                              child: TextFormField(
                                decoration: InputDecoration(
                                  labelText: "Rua",
                                  prefixIcon: const Icon(Icons.rocket),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  enabledBorder: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10)
                                  ),
                                ),
                                initialValue: usuario.endereco.rua,
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'informe a Rua';
                                  }
                                  return null;
                                },
                                onChanged: (value) {
                                  setState(() {
                                    rua = value;
                                  });
                                },
                              ),
                            ),

                            Padding(
                              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                              child: TextFormField(
                                decoration: InputDecoration(
                                  labelText: "Número",
                                  prefixIcon: const Icon(Icons.numbers),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  enabledBorder: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10)
                                  ),
                                ),
                                initialValue: usuario.endereco.numero,
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'informe o Número';
                                  }
                                  return null;
                                },
                                onChanged: (value) {
                                  setState(() {
                                    numero = value;
                                  });
                                },
                              ),
                            ),

                            Padding(
                              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
                              child: TextFormField(
                                decoration: InputDecoration(
                                  labelText: "Complemento",
                                  prefixIcon: const Icon(Icons.house),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  enabledBorder: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10)
                                  ),
                                ),
                                initialValue: usuario.endereco.complemento,
                                onChanged: (value) {
                                  complemento = value;
                                }
                              ),
                            ),

                            Padding(
                              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16.0),
                              child: Center(
                                child: ElevatedButton(
                                  onPressed: () async {
                                    if (form.currentState?.validate() ?? false) {
                                      /*Navigator.push(
                                        context,
                                        MaterialPageRoute(
                                          builder: (context) => HomePage(email: emailController.text)
                                        ),
                                      );*/
                                      var endereco = Endereco(cidade: cidade, uf: uf, bairro: bairro, rua: rua, numero: numero, latitude: '', longitude: '');
                                      var dados = CadastroClienteRequest(nome: nome, email: email, senha: password, endereco: endereco);
                                      var response = await ClienteService().update(dados);
                                      if(response.statusCode == 200){
                                        showDialog(
                                          context: context,
                                          builder: (context) =>
                                            const AlertDialog(
                                              title: Text("Sucesso"),
                                              content: Text("perfil atualizado com sucesso.")
                                            ),
                                        );
                                      }
                                      else{
                                        //var resp = jsonDecode(utf8.decode(response.bodyBytes));

                                        showDialog(
                                          context: context,
                                          builder: (context) =>
                                            const AlertDialog(
                                              title: Text("Erro"),
                                              content: Text("Erro ao criar a conta.")
                                            ),
                                        );
                                      }
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
                    );
            }
            else {
              // if no data, show simple Text
              return const Text("No data available");
            }
          }
        ),
      )
    );
  }
}