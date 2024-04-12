import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_app/components/PopupMapa.dart';
import 'package:flutter_app/models/estabelecimento.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:flutter_map_marker_popup/flutter_map_marker_popup.dart';
import 'package:latlong2/latlong.dart';

class Mapa extends StatefulWidget {
  final LatLng latLng;
  final List<Estabelecimento> estabelecimentos;
  const Mapa({super.key, required this.latLng, required this.estabelecimentos});

  @override
  MapaState createState() => MapaState();
}

class MapaState extends State<Mapa> {
  late List<Marker> markers;
  double currentZoom = 11.0;
  MapController mapController = MapController();
  late MapPosition currentPosition;

  @override
  void initState() {
    super.initState();
    currentPosition = MapPosition(center: widget.latLng);
    markers = buildMarkers();
  }

  void aumentarZoom() {
    if (currentZoom < 25) {
      currentZoom += 1;
    }
    mapController.move(currentPosition.center!, currentZoom);
  }

  void diminuirZoom() {
    if (currentZoom > 2) {
      currentZoom -= 1;
    }
    mapController.move(currentPosition.center!, currentZoom);
  }

  void onMapPositionChange(MapPosition position, bool v) {
    setState(() {
      currentPosition = position;
    });
  }

  List<Marker> buildMarkers() {
    return widget.estabelecimentos
        .map((est) => Marker(
            key: ValueKey(est.id),
            point: LatLng(double.parse(est.endereco.latitude ?? ''),
                double.parse(est.endereco.longitude ?? '')),
            child: const Icon(Icons.place, color: Colors.red)))
        .toList();
  }

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        FlutterMap(
          mapController: mapController,
          options: MapOptions(
              onPositionChanged: onMapPositionChange,
              initialCenter: widget.latLng,
              initialZoom: currentZoom,
              minZoom: 2,
              maxZoom: 25),
          children: [
            TileLayer(
              urlTemplate: 'https://tile.openstreetmap.org/{z}/{x}/{y}.png',
              userAgentPackageName: 'dev.fleaflet.flutter_map.example',
              // Plenty of other options available!
            ),
            PopupMarkerLayer(
                options: PopupMarkerLayerOptions(
                    markers: markers,
                    popupDisplayOptions: PopupDisplayOptions(
                        builder: (BuildContext context, Marker marker) =>
                            PopupMapa(marker)))),
          ],
        ),
        Positioned(
            bottom: 5,
            right: 2,
            child: Column(
              children: [
                Container(
                  width: 35, // <-- Your width
                  height: 35, // <-- Your height
                  decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(3),
                      color: const Color.fromARGB(255, 57, 86, 150),
                      boxShadow: [
                        BoxShadow(
                            color: Color.fromARGB(255, 168, 168, 168),
                            spreadRadius: 3),
                      ]),
                  child: IconButton(
                    color: Colors.white,
                    padding: const EdgeInsets.all(0.0),
                    icon: const Icon(
                      Icons.add_outlined,
                      size: 25.0,
                      weight: 15,
                    ),
                    onPressed: aumentarZoom,
                  ),
                ),
                Container(
                  width: 35, // <-- Your width
                  height: 35, // <-- Your height
                  margin: const EdgeInsets.only(top: 5),
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(5),
                    color: const Color.fromARGB(255, 57, 86, 150),
                    boxShadow: [
                      BoxShadow(
                          color: const Color.fromARGB(255, 170, 170, 170),
                          spreadRadius: 3),
                    ],
                  ),
                  child: IconButton(
                    padding: const EdgeInsets.all(0.0),
                    color: Colors.white,
                    icon: const Icon(
                      Icons.remove_sharp,
                      size: 25.0,
                      weight: 15,
                    ),
                    onPressed: diminuirZoom,
                  ),
                ),
              ],
            )),
      ],
    );
  }
}
