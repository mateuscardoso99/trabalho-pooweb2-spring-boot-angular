import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong2/latlong.dart';

class Mapa extends StatelessWidget {

  final double lat;
  final double lng;
  const Mapa({super.key, required this.lat, required this.lng});

  @override
   Widget build(BuildContext context) {
    return FlutterMap(
        mapController: MapController(),
        options: MapOptions(
          initialCenter: LatLng(lat, lng),
          initialZoom: 9.2,
        ),
        children: [
          TileLayer(
            urlTemplate: 'https://tile.openstreetmap.org/{z}/{x}/{y}.png',
            userAgentPackageName: 'dev.fleaflet.flutter_map.example',
            // Plenty of other options available!
          ),
        ],
    );
  }
}