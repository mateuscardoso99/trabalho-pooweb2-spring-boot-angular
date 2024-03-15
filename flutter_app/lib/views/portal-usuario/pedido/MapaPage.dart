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
  Position? currentPosition;

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
                _determinePosition();
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
                child: Mapa(lat: 675.67, lng: -56)
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
        currentPosition = position;
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