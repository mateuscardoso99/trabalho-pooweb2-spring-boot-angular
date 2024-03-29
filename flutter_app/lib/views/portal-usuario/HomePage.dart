import 'package:flutter/material.dart';
import 'package:flutter_app/components/DrawerNavigation.dart';
import 'package:flutter_app/services/cliente_service.dart';
import 'package:intl/intl.dart';

import '../../models/pedido.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key, required this.email});

  final String email;

  @override
  HomePageState createState() => HomePageState();
}

class HomePageState extends State<HomePage>{
  late Future<List<Pedido>> pedidos;

  @override
  void initState() {
    super.initState();
    pedidos = ClienteService().getPedidos();
  }

  Widget buildPedidos(List<Pedido> pedidos) {
    // ListView Builder to show data in a list
    return ListView.builder(
      itemCount: pedidos.length,
      itemBuilder: (context, index) {
        final pedido = pedidos[index];
        return Card(
          color: Colors.grey[300],
          elevation: 8.0,
          child: Container(
            padding: const EdgeInsets.symmetric(vertical: 16, horizontal: 16),
            height: 200,
            width: MediaQuery.of(context).size.width, //width 100%
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Expanded(
                      child: CircleAvatar(
                        radius: 50, //we give the image a radius of 50
                        backgroundImage: NetworkImage('https://webstockreview.net/images/male-clipart-professional-man-3.jpg'),
                      )
                    ),
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
                        Text(DateFormat("dd/MM/yyyy HH:mm").format(pedido.dataHora))
                      ],
                    ),
                  ],
                ),
                const SizedBox(height: 20),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          pedido.descricao,
                          style: const TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        const SizedBox(height: 4),
                        Text(pedido.estabelecimento),
                      ],
                    ),
                    const SizedBox(width: 32),
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.end,
                      children: [
                        Text(
                          pedido.statusPedido.name,
                          style: const TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        const SizedBox(height: 4),
                        Text(pedido.nomeCliente),
                      ],
                    )
                  ],
                ),
              ],
            )
          )
        );
      },
    );
  }
  
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        iconTheme: const IconThemeData(color: Colors.white),
        title: const Text(
          'Home Page',
          style: TextStyle(
            color: Colors.white
          )
        ),
        backgroundColor: Colors.red,
      ),
      drawer: const DrawerNavigation(),
      body: Center(
        child: FutureBuilder<List<Pedido>>(
          future: pedidos,
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              // until data is fetched, show loader
              return const CircularProgressIndicator();
            } else if (snapshot.hasData) {
              // once data is fetched, display it on screen (call buildPedidos())
              final pedidos = snapshot.data!;
              return buildPedidos(pedidos);
            } else {
              // if no data, show simple Text
              return const Text("No data available");
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