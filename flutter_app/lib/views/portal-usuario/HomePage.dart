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
  late Future<List<Pedido>> pedidos;

  @override
  void initState() {
    super.initState();
    pedidos = ClienteService().getPedidos();
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

  Widget buildPedidos(List<Pedido> pedidos) {
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
                height: 200,
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
                  ],
                )));
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        iconTheme: const IconThemeData(color: Colors.white),
        title: const Text('Home Page', style: TextStyle(color: Colors.white)),
        backgroundColor: Colors.red,
      ),
      drawer: const DrawerNavigation(),
      body: Center(
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
      ),
    );
  }
}
