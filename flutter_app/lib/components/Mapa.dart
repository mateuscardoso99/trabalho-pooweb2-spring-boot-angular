import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';

class Mapa extends StatelessWidget {
  const Mapa({super.key});

   Widget build(BuildContext context) {
    return FlutterMap(
        mapController: MapController(),
        options: const MapOptions(),
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