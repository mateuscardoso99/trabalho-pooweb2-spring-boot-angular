import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_app/components/Mapa.dart';
import 'package:flutter_app/models/estabelecimento.dart';
import 'package:flutter_app/services/mapa_service.dart';
import 'package:geolocator/geolocator.dart';
import 'package:latlong2/latlong.dart';

class MapaPage extends StatefulWidget {
  const MapaPage({super.key, required this.showTitle});

  final bool showTitle;
  
  @override
  MapaPageState createState() => MapaPageState();
}

class MapaPageState extends State<MapaPage> {
  LatLng? currentPosition;
  late Future<List<Estabelecimento>> estabsFuture;
  Timer searchOnStoppedTyping = Timer(const Duration(milliseconds: 2000), () {});
  List<String> cidades = [];
  bool isLoading = false;
  bool jaPesquisouCidade = false;

  @override
  void initState() {//roda quando o componente é carregado
    super.initState();
    getEstabelecimentos(null);
    _determinePosition();
  }

  getCidades(String value) async{
    setState(() {
      isLoading = true;
    });

    var result = await MapaService().findCidades(value);

    setState(() {
      cidades = result;
      isLoading = false;
    });
  }

  getEstabelecimentos(String? cidade){
    var result;
    if(cidade == null) {
      result = MapaService().findAll();
    } else {
      setState(() {
        jaPesquisouCidade = true;
      });
      result = MapaService().findByCidade(cidade);
    }
    setState(() {
      estabsFuture = result;
      cidades = [];
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: widget.showTitle ? AppBar(
        iconTheme: const IconThemeData(color: Colors.white),
        backgroundColor: Colors.red,
        title: const Text(
          "Escolha um estabelecimento",
          style: TextStyle(
            color: Colors.white
          )
        ),
      ) : null,
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            TextField(
              onChanged: (value) {
                if(value.length > 2){
                  const duration = Duration(milliseconds:1000);
                  setState(() => searchOnStoppedTyping.cancel()); // clear timer
                  setState(() => searchOnStoppedTyping = Timer(duration, () => getCidades(value)));
                }
              },
              style: const TextStyle(
                color: Color(0xff020202),
                fontSize: 20,
                fontWeight: FontWeight.w400,
                letterSpacing: 0.5,
              ),
              decoration: InputDecoration(
                filled: true,
                fillColor: const Color(0xfff1f1f1),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8),
                  borderSide: BorderSide.none,
                ),
                hintText: "Busque uma cidade",
                hintStyle: const TextStyle(
                    color: Colors.black54,
                    fontSize: 20,
                    fontWeight: FontWeight.w400,
                    letterSpacing: 0.5,
                    decorationThickness: 6),
                prefixIcon: const Icon(Icons.search),
                prefixIconColor: Colors.red,
                suffixIcon: isLoading ? const CircularProgressIndicator() : null
              )
            ),
            const SizedBox(
              height: 20,
            ),
            
            cidades.isNotEmpty ?
              Expanded(
                flex: 1,
                child: ListView.builder(
                  itemCount: cidades.length,
                  itemBuilder: (context, index) {
                    final data = cidades[index];
                    return ListTile(
                      onTap: () => {
                        getEstabelecimentos(cidades[index])
                      },
                      leading: const Icon(Icons.place),
                      title: Text(data)
                    );
                  }
                )
              ) : const Text("Nenhum estabelecimento encontrado nesta cidade", style: TextStyle(fontStyle: FontStyle.italic)),
            /*if (currentPosition != null)
              Text(
                  "LAT: ${currentPosition?.latitude}, LNG: ${currentPosition?.longitude}"),
            ElevatedButton(
              child: const Text("Get location"),
              onPressed: () {
                _determinePosition();
              },
            ),*/
            Expanded(
              flex: 1,
              child: FractionallySizedBox(
                  //widthFactor: 0.9,
                  //heightFactor: 0.9,
                  child: (currentPosition != null) 
                      ?
                        FutureBuilder<List<Estabelecimento>>(
                          future: estabsFuture,
                          builder: (context, snapshot) {
                            late List<Estabelecimento> estabs = [];

                            if (snapshot.connectionState == ConnectionState.waiting) {
                              // until data is fetched, show loader
                              return const Center(
                                child: CircularProgressIndicator(),
                              );
                            } else if (snapshot.hasData) {
                              // once data is fetched, display it on screen (call buildPedidos())
                              estabs = snapshot.data!;
                              
                              if(jaPesquisouCidade){
                                return Mapa(
                                  latLng: LatLng(double.parse(estabs[0].endereco.latitude), double.parse(estabs[0].endereco.longitude)),
                                  estabelecimentos: estabs
                                );
                              }
                              return Mapa(
                                latLng: currentPosition!,
                                estabelecimentos: estabs
                              );
                            } else {
                              return Mapa(
                                latLng: currentPosition!,
                                estabelecimentos: estabs
                              );
                            }
                          },
                        )
                    : Column(
                        children: [
                          const Text("É preciso da permissão de localização"),
                          ElevatedButton(
                            onPressed: _determinePosition, 
                            child: const Text("Permitir acesso a localização"),
                          )
                        ],
                      )
              ),
            ),
          ],
        ),
      ),
    );
  }

  getCurrentLocation() {
    Geolocator.getCurrentPosition(
            desiredAccuracy: LocationAccuracy.best,
            forceAndroidLocationManager: true)
        .then((Position position) {
      setState(() {
        currentPosition = LatLng(position.latitude, position.longitude);
      });
    }).catchError((e) {
      print(e);
    });
  }

  void _determinePosition() async {
    bool serviceEnabled;
    LocationPermission permission;

    // Test if location services are enabled.
    serviceEnabled = await Geolocator.isLocationServiceEnabled();
    if (!serviceEnabled) {
      // Location services are not enabled don't continue
      // accessing the position and request users of the
      // App to enable the location services.
      return Future.error('Location services are disabled.');
    }

    permission = await Geolocator.checkPermission();
    if (permission == LocationPermission.denied) {
      permission = await Geolocator.requestPermission();
      if (permission == LocationPermission.denied) {
        // Permissions are denied, next time you could try
        // requesting permissions again (this is also where
        // Android's shouldShowRequestPermissionRationale
        // returned true. According to Android guidelines
        // your App should show an explanatory UI now.
        return Future.error('Location permissions are denied');
      }
    }

    if (permission == LocationPermission.deniedForever) {
      // Permissions are denied forever, handle appropriately.
      return Future.error(
          'Location permissions are permanently denied, we cannot request permissions.');
    }

    // When we reach here, permissions are granted and we can
    // continue accessing the position of the device.
    await Geolocator.getCurrentPosition().then((Position position) {
      setState(() {
        currentPosition = LatLng(position.latitude, position.longitude);
      });
    });
  }
}

//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       appBar: AppBar(
//         title: const Text('Estabelecimentos'),
//       ),
//       body: const Center(
//         child: SingleChildScrollView(
//           child: Column(
//             children: [
//               Text("ww"),
//               SizedBox(
//                 width: 500,
//                 height: 400,
//                 child: Mapa()
//               ),
//             ],
//           )
//         ),
//       )

//     );
//   }
// }
