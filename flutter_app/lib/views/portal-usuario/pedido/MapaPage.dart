import 'package:flutter/material.dart';
import 'package:flutter_app/components/DrawerNavigation.dart';
import 'package:flutter_app/components/Mapa.dart';
import 'package:geolocator/geolocator.dart';

class MapaPage extends StatefulWidget {
  const MapaPage({super.key});

  @override
  MapaPageState createState() => MapaPageState();
}

class MapaPageState extends State<MapaPage> {
  Position? currentPosition; //'?' significa que variavel pode ser null

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Location"),
      ),
      drawer: const DrawerNavigation(email: "email"),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            if (currentPosition != null) Text(
              "LAT: ${currentPosition?.latitude}, LNG: ${currentPosition?.longitude}"
            ),
            ElevatedButton(
              child: const Text("Get location"),
              onPressed: () {
                getCurrentLocation();
              },
            ),
            const Expanded(
              flex: 1,
              child: Text("Escolha um estabelecimento")
            ),
            const Expanded(
              flex: 6,
              child: FractionallySizedBox(
                widthFactor: 0.9,
                heightFactor: 0.9,
                child: Mapa()
              ),
            ),
          ],
        ),
      ),
    );
  }

  getCurrentLocation() {
    Geolocator
      .getCurrentPosition(desiredAccuracy: LocationAccuracy.best, forceAndroidLocationManager: true)
      .then((Position position) {
        setState(() {
          currentPosition = position;
        });
      }).catchError((e) {
        print(e);
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