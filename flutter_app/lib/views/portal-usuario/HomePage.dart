import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_app/components/DrawerNavigation.dart';
import 'package:flutter_app/services/cliente_service.dart';
import 'package:intl/intl.dart';

import '../../models/pedido.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  HomePageState createState() => HomePageState();
}

class HomePageState extends State<HomePage> {
  //late Future<List<Pedido>> pedidos;
  List<Pedido> pedidos = [];

  @override
  void initState() {
    super.initState();
    refreshData();
  }

  Future<void> refreshData() async { 
    // Add new items or update the data here 
    pedidos = [];
    final response = await ClienteService().getPedidos();

    setState(() { 
      for(var i=0; i<response.length; i++){
        pedidos.add(response[i]);
      }
    }); 
  } 

  Color situacao(String situacao) {
    if (situacao == "PENDENTE") {
      return Colors.red;
    } else if (situacao == "FINALIZADO") {
      return Colors.green;
    } else {
      return Colors.orange;
    }
  }

  /*Widget buildPedidos(List<Pedido> pedidos) {
    // ListView Builder to show data in a list
    return ListView.builder(
      itemCount: pedidos.length,
      itemBuilder: (context, index) {
        final pedido = pedidos[index];
        return Card(
            margin: const EdgeInsets.all(10),
            color: const Color.fromARGB(255, 218, 218, 218),
            elevation: 8.0,
            child: Container(
                padding:
                    const EdgeInsets.symmetric(vertical: 16, horizontal: 16),
                height: pedido.statusPedido.name == "PENDENTE" ? 250 : 200,
                width: MediaQuery.of(context).size.width, //width 100%
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Expanded(
                            child: Image(
                              image: AssetImage('assets/icon.png'),
                              width: 80,
                              height: 80,
                            )),
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.end,
                          children: [
                            // Container(
                            //   margin: EdgeInsets.only(top: 8),
                            //   width: 150,
                            //   color: Colors.black54,
                            //   height: 2,
                            // ),
                            const SizedBox(height: 4),
                            Row(
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                  Container(
                                    margin: const EdgeInsets.only(right: 4),
                                    child: const Icon(
                                      Icons.access_time_outlined,
                                      size: 25.0,
                                      weight: 15,
                                    ),
                                  ),
                                Text(
                                  DateFormat("dd/MM/yyyy HH:mm")
                                      .format(pedido.dataHora),
                                  style:
                                      const TextStyle(fontWeight: FontWeight.w700),
                                )
                              ],
                            )
                          ],
                        ),
                      ],
                    ),
                    const SizedBox(height: 20),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            SizedBox(
                              width: MediaQuery.of(context).size.width * 0.5, //width 100%,
                              child: Text(
                              pedido.descricao,
                              style: const TextStyle(
                                fontSize: 16,
                                fontStyle: FontStyle.italic
                              ),
                              overflow: TextOverflow.ellipsis,
                            ),
                            ),
                            const SizedBox(height: 4),
                            Row(
                              children: [
                                Container(
                                  margin: const EdgeInsets.only(right: 4),
                                  child: const Icon(
                                      Icons.store,
                                      size: 25.0,
                                      weight: 15,
                                    ),
                                ),
                                Text(pedido.estabelecimento),
                              ],
                            )
                          ],
                        ),
                        const SizedBox(width: 32),
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.end,
                          children: [
                            Container(
                              padding: const EdgeInsets.all(5),
                              color: situacao(pedido.statusPedido.name),
                              child: Text(
                                pedido.statusPedido.name,
                                style: const TextStyle(
                                  color: Colors.white,
                                  fontSize: 16,
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                            )
                          ],
                        )
                      ],
                    ),
                    if(pedido.statusPedido.name == "PENDENTE")
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            SizedBox(
                              width: MediaQuery.of(context).size.width * 0.5, //width 100%,
                              child: ElevatedButton(
                                child: const Text("CANCELAR PEDIDO"),
                                style: ElevatedButton.styleFrom(
                                  backgroundColor: Colors.amber[700],
                                  foregroundColor: Colors.black,
                                  textStyle: const TextStyle(
                                    fontWeight: FontWeight.bold
                                  ),
                                  shape: RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(5),
                                  ),
                                ),
                                onPressed: () async {
                                  var resp = await ClienteService().desativarPedido(pedido.id!);
                                  final Map<String, dynamic> jsonResponse = jsonDecode(utf8.decode(resp.bodyBytes));
                                  if(resp.statusCode == 200){
                                    showDialog(
                                      context: context,
                                      builder: (context) => AlertDialog(
                                          title: const Text("Sucesso"),
                                          content: const Text("Pedido cancelado com sucesso.", style: TextStyle(
                                            color: Colors.green,
                                            fontWeight: FontWeight.bold,
                                            fontSize: 20)
                                          )
                                      ),
                                    );
                                  }
                                  else{
                                    showDialog(
                                      context: context,
                                      builder: (context) => AlertDialog(
                                          title: const Text("Erro"),
                                          content: const Text("Erro ao cancelar pedido.", style: TextStyle(
                                            color: Colors.red,
                                            fontWeight: FontWeight.bold,
                                            fontSize: 20)
                                          )
                                      ),
                                    );
                                  }
                                },
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ],
                )));
      },
    );
  }*/

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        iconTheme: const IconThemeData(color: Colors.white),
        title: const Text('Home Page', style: TextStyle(color: Colors.white)),
        backgroundColor: Colors.red,
      ),
      drawer: const DrawerNavigation(),
      /*body: Center(
        child: FutureBuilder<List<Pedido>>(
          future: pedidos,
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              // until data is fetched, show loader
              return const Center(
                child: CircularProgressIndicator(),
              );
            } else if (snapshot.hasData && !snapshot.data!.isEmpty) {
              // once data is fetched, display it on screen (call buildPedidos())
              final pedidos = snapshot.data!;
              return buildPedidos(pedidos);
            } else {
              // if no data, show simple Text
              return const Text("Nenhum pedido encontrado.",
                  style: TextStyle(fontStyle: FontStyle.italic));
            }
          },
        ),

        // Padding(
        //   padding: const EdgeInsets.symmetric(horizontal: 18, vertical: 16),
        //   child: SingleChildScrollView(
        //     child: Column(
        //       children: [

        //       ],
        //     ),
        //   )
        // )
      ),*/
      body: RefreshIndicator(
        onRefresh: refreshData,
        child: pedidos.isEmpty 
              ? const Center(child: Text("Nenhum pedido encontrado.", style: TextStyle(fontStyle: FontStyle.italic, fontSize: 18))) 
              : 
        ListView.builder(
          itemCount: pedidos.length,
          itemBuilder: (context, index) {
            final pedido = pedidos[index];
            return Card(
                margin: const EdgeInsets.all(10),
                color: const Color.fromARGB(255, 218, 218, 218),
                elevation: 8.0,
                child: Container(
                    padding:
                        const EdgeInsets.symmetric(vertical: 16, horizontal: 16),
                    height: pedido.statusPedido.name == "PENDENTE" ? 250 : 200,
                    width: MediaQuery.of(context).size.width, //width 100%
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Expanded(
                                child: Image(
                                  image: AssetImage('assets/icon.png'),
                                  width: 80,
                                  height: 80,
                                )),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.end,
                              children: [
                                // Container(
                                //   margin: EdgeInsets.only(top: 8),
                                //   width: 150,
                                //   color: Colors.black54,
                                //   height: 2,
                                // ),
                                const SizedBox(height: 4),
                                Row(
                                  crossAxisAlignment: CrossAxisAlignment.center,
                                  children: [
                                      Container(
                                        margin: const EdgeInsets.only(right: 4),
                                        child: const Icon(
                                          Icons.access_time_outlined,
                                          size: 25.0,
                                          weight: 15,
                                        ),
                                      ),
                                    Text(
                                      DateFormat("dd/MM/yyyy HH:mm")
                                          .format(pedido.dataHora),
                                      style:
                                          const TextStyle(fontWeight: FontWeight.w700),
                                    )
                                  ],
                                )
                              ],
                            ),
                          ],
                        ),
                        const SizedBox(height: 20),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          crossAxisAlignment: CrossAxisAlignment.center,
                          children: [
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                SizedBox(
                                  width: MediaQuery.of(context).size.width * 0.5, //width 100%,
                                  child: Text(
                                  pedido.descricao,
                                  style: const TextStyle(
                                    fontSize: 16,
                                    fontStyle: FontStyle.italic
                                  ),
                                  overflow: TextOverflow.ellipsis,
                                ),
                                ),
                                const SizedBox(height: 4),
                                Row(
                                  children: [
                                    Container(
                                      margin: const EdgeInsets.only(right: 4),
                                      child: const Icon(
                                          Icons.store,
                                          size: 25.0,
                                          weight: 15,
                                        ),
                                    ),
                                    Text(pedido.estabelecimento),
                                  ],
                                )
                              ],
                            ),
                            const SizedBox(width: 32),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.end,
                              children: [
                                Container(
                                  padding: const EdgeInsets.all(5),
                                  color: situacao(pedido.statusPedido.name),
                                  child: Text(
                                    pedido.statusPedido.name,
                                    style: const TextStyle(
                                      color: Colors.white,
                                      fontSize: 16,
                                      fontWeight: FontWeight.bold,
                                    ),
                                  ),
                                )
                              ],
                            )
                          ],
                        ),
                        if(pedido.statusPedido.name == "PENDENTE")
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          crossAxisAlignment: CrossAxisAlignment.center,
                          children: [
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                SizedBox(
                                  width: MediaQuery.of(context).size.width * 0.5, //width 100%,
                                  child: ElevatedButton(
                                    child: const Text("CANCELAR PEDIDO"),
                                    style: ElevatedButton.styleFrom(
                                      backgroundColor: Colors.amber[700],
                                      foregroundColor: Colors.black,
                                      textStyle: const TextStyle(
                                        fontWeight: FontWeight.bold
                                      ),
                                      shape: RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(5),
                                      ),
                                    ),
                                    onPressed: () async {
                                      var resp = await ClienteService().desativarPedido(pedido.id!);
                                      final Map<String, dynamic> jsonResponse = jsonDecode(utf8.decode(resp.bodyBytes));
                                      if(resp.statusCode == 200){
                                        showDialog(
                                          context: context,
                                          builder: (context) => AlertDialog(
                                              title: const Text("Sucesso"),
                                              content: const Text("Pedido cancelado com sucesso.", style: TextStyle(
                                                color: Colors.green,
                                                fontWeight: FontWeight.bold,
                                                fontSize: 20)
                                              ),
                                              actions: <Widget>[
                                                TextButton(
                                                  child: const Text('OK'),
                                                  onPressed: () => Navigator.pop(context,true)
                                                ),
                                              ]
                                          ),
                                        );
                                      }
                                      else{
                                        showDialog(
                                          context: context,
                                          builder: (context) => AlertDialog(
                                              title: const Text("Erro"),
                                              content: const Text("Erro ao cancelar pedido.", style: TextStyle(
                                                color: Colors.red,
                                                fontWeight: FontWeight.bold,
                                                fontSize: 20)
                                              ),
                                              actions: <Widget>[
                                                TextButton(
                                                  child: const Text('OK'),
                                                  onPressed: () => Navigator.pop(context,true)
                                                ),
                                              ]
                                          ),
                                        );
                                      }
                                    },
                                  ),
                                ),
                              ],
                            ),
                          ],
                        ),
                      ],
                    )));
          },
        ),
      ),
    );
  }
}
