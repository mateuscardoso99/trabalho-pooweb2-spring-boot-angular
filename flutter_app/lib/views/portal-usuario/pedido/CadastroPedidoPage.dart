import 'package:flutter/material.dart';
import 'package:flutter_app/components/DrawerNavigation.dart';
import 'package:flutter_app/models/estabelecimento.dart';
import 'package:flutter_app/request/cadastro_pedido.dart';
import 'package:flutter_app/services/cliente_service.dart';

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
        title: Text(
          'Pedido para ${widget.estabelecimento.nome}',
          style: const TextStyle(
            color: Colors.white
          )
        ),
      ),
      drawer: const DrawerNavigation(),
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
                    maxLines: 8,
                    decoration: const InputDecoration(
                      border: OutlineInputBorder(), labelText: "Descrição"
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
                  padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16.0),
                  child: Center(
                    child: ElevatedButton(
                      onPressed: () async {
                        if (form.currentState!.validate()) {
                          var pedido = CadastroPedidoRequest(descricao: descricao, idEstabelecimento: widget.estabelecimento.id!);
                          var response = await ClienteService().salvarPedido(pedido);
                          if(response.statusCode == 201){
                            showDialog(
                              context: context,
                              builder: (context) =>
                              const AlertDialog(
                                title: Text("Sucesso"),
                                content: Text("pedido reaalizado com sucesso.")
                              ),
                            );
                          }
                          else{
                            //var resp = jsonDecode(response.body);
                            showDialog(
                              context: context,
                              builder: (context) =>
                                const AlertDialog(
                                  title: Text("Erro"),
                                  content: Text("Erro ao criar pedido.")
                              ),
                            );
                          }
                        } else {
                          ScaffoldMessenger.of(context).showSnackBar(
                            const SnackBar(
                              content: Text('Preencha todos os campos')
                            ),
                          );
                        }
                      },
                      child: const Text('SALVAR'),
                    ),
                  ),
                ),
              ],
            ),
          ),
        )
      ),
    );
  }
}