import 'package:flutter/material.dart';
import 'package:flutter_app/models/estabelecimento.dart';
import 'package:flutter_app/models/token.dart';
import 'package:flutter_app/services/auth_service.dart';
import 'package:flutter_app/services/mapa_service.dart';
import 'package:flutter_app/views/portal-usuario/pedido/CadastroPedidoPage.dart';
import 'package:flutter_map/flutter_map.dart';

class PopupMapa extends StatefulWidget {
  final Marker marker;

  const PopupMapa(this.marker, {super.key});

  @override
  State<StatefulWidget> createState() => PopupMapaState();
}

class PopupMapaState extends State<PopupMapa> {
  final List<IconData> _icons = [
    Icons.star_border,
    Icons.star_half,
    Icons.star
  ];
  int _currentIcon = 0;

  late Future<Estabelecimento> estabelecimento;
  late Future<String> user;

  Future<Estabelecimento> findEstabelecimento(){
    ValueKey key = widget.marker.key as ValueKey;
    return MapaService().findById(key.value);
  }

  Future<String> getUsuarioStorage() async {
    var userStorage = await AuthService().getUser;
    return userStorage;
  }

  @override
  void initState() {
    super.initState();
    estabelecimento = findEstabelecimento();
    user = getUsuarioStorage();
  }

  @override
  Widget build(BuildContext context) {
    return Card(
      child: FutureBuilder(
          future: Future.wait([estabelecimento, user]), //espera os 2 serem resolvidos
          builder: (context, AsyncSnapshot<List<dynamic>> snapshot) {
            if(snapshot.connectionState == ConnectionState.waiting){
              return const Center(
                child: CircularProgressIndicator(),
              );
            }
            else if(snapshot.hasData){
              return InkWell(
                onTap: () => setState(() {
                  _currentIcon = (_currentIcon + 1) % _icons.length;
                }),
                child: Row(
                  mainAxisSize: MainAxisSize.min,
                  children: <Widget>[
                    Padding(
                      padding: const EdgeInsets.only(left: 20, right: 10),
                      child: Icon(_icons[_currentIcon]),
                    ),
                    _cardDescription(context, snapshot),
                  ],
                ),
              );
            }
            else{
              return const Text("Estabelecimento não encontrado");
            }
          }
      )    
    );
  }

  Widget _cardDescription(BuildContext context, AsyncSnapshot<List<dynamic>> snapshot) {
    Estabelecimento estabelecimento = snapshot.data?[0] as Estabelecimento;
    String userStorage = snapshot.data?[1] as String;
    Token? token = Token.deserialize(userStorage);

    return Padding(
      padding: const EdgeInsets.all(10),
      child: Container(
        constraints: const BoxConstraints(minWidth: 100, maxWidth: 200),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisAlignment: MainAxisAlignment.start,
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            Text(
              estabelecimento.nome,
              overflow: TextOverflow.fade,
              softWrap: false,
              style: const TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 14.0,
                color: Colors.red
              ),
            ),
            const Padding(padding: EdgeInsets.symmetric(vertical: 4.0)),
            Text(
              'Endereço: ${estabelecimento.endereco.rua}, ${estabelecimento.endereco.numero}',
              style: const TextStyle(fontSize: 12.0),
            ),
            Text(
              'Horário de funcionamento: ${estabelecimento.horarioFuncionamento}',
              style: const TextStyle(fontSize: 12.0),
            ),

            if(token != null)
              ElevatedButton(
                onPressed: () => {
                  Navigator.push(context, MaterialPageRoute(builder: (context) => CadastroPedidoPage(estabelecimento: estabelecimento)))
                }, 
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.red,
                  foregroundColor: Colors.white,
                  textStyle: const TextStyle(
                    fontWeight: FontWeight.bold
                  )
                ),
                child: const Text("Realizar pedido")              
              )
          ],
        ),
      ),
    );
  }
}