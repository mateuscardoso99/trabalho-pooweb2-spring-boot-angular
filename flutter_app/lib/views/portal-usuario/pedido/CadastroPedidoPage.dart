import 'package:flutter/material.dart';
import 'package:flutter_app/components/DrawerNavigation.dart';
import 'package:flutter_app/models/estabelecimento.dart';
import 'package:flutter_app/request/cadastro_pedido.dart';
import 'package:flutter_app/services/cliente_service.dart';
import 'package:flutter_app/views/portal-usuario/HomePage.dart';

class CadastroPedidoPage extends StatefulWidget {
  const CadastroPedidoPage({super.key, required this.estabelecimento});
  final Estabelecimento estabelecimento;

  @override
  CadastroPedidoPageState createState() => CadastroPedidoPageState();
}

class CadastroPedidoPageState extends State<CadastroPedidoPage> {
  final form = GlobalKey<FormState>();
  String descricao = '';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        iconTheme: const IconThemeData(color: Colors.white),
        backgroundColor: Colors.red,
        title: Text('Pedido para ${widget.estabelecimento.nome}',
            style: const TextStyle(color: Colors.white)),
      ),
      drawer: const DrawerNavigation(),
      body: Form(
        key: form,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
              child: TextFormField(
                maxLines: 5,
                decoration: InputDecoration(
                  labelText: "Descrição",
                  prefixIcon: const Icon(Icons.email),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(10),
                  ),
                  enabledBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(10),
                  ),
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'informe a descrição do pedido';
                  }
                  return null;
                },
                onChanged: (value) {
                  setState(() {
                    descricao = value;
                  });
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
                      var pedido = CadastroPedidoRequest(
                          descricao: descricao,
                          idEstabelecimento: widget.estabelecimento.id!);
                      var response =
                          await ClienteService().salvarPedido(pedido);
                      if (response.statusCode == 201) {
                        if (context.mounted) {
                          showDialog(
                            context: context,
                            builder: (context) => AlertDialog(
                              title: const Text("Sucesso"),
                              shape: const RoundedRectangleBorder(
                                  borderRadius:
                                      BorderRadius.all(Radius.circular(5))),
                              backgroundColor: Colors.white,
                              content: const Text(
                                  "pedido realizado com sucesso.",
                                  style: TextStyle(
                                      color: Colors.green,
                                      fontWeight: FontWeight.bold,
                                      fontSize: 20)),
                              actions: <Widget>[
                                TextButton(
                                  child: const Text('OK'),
                                  onPressed: () {
                                    Navigator.push(
                                      context,
                                      MaterialPageRoute(
                                          builder: (context) =>
                                              const HomePage()),
                                    );
                                  },
                                ),
                              ],
                            ),
                          );
                        }
                      } else {
                        if (context.mounted) {
                          showDialog(
                            context: context,
                            builder: (context) => const AlertDialog(
                                title: Text("Erro"),
                                shape: const RoundedRectangleBorder(
                                    borderRadius:
                                        BorderRadius.all(Radius.circular(5))),
                                backgroundColor: Colors.white,
                                content: Text("Erro ao criar pedido.",
                                    style: TextStyle(
                                        color: Colors.green,
                                        fontWeight: FontWeight.bold,
                                        fontSize: 20))),
                          );
                        }
                      }
                    } else {
                      ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(content: Text('Preencha a descrição')),
                      );
                    }
                  },
                  child: const Text('SALVAR',
                      style: TextStyle(
                          color: Colors.white, fontWeight: FontWeight.bold)),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
