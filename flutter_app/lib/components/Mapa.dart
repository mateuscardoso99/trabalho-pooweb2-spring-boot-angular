import 'package:flutter/material.dart';
import 'package:flutter_app/components/ExamplePopup.dart';
import 'package:flutter_app/models/estabelecimento.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:flutter_map_marker_popup/flutter_map_marker_popup.dart';
import 'package:latlong2/latlong.dart';

class Mapa extends StatefulWidget {

  final double lat;
  final double lng;
  final List<Estabelecimento> estabelecimentos;
  const Mapa({super.key, required this.lat, required this.lng, required this.estabelecimentos});
  
  @override
  MapaState createState() => MapaState();
}

class MapaState extends State<Mapa>{
  late List<Marker> markers;

  @override
  void initState() {
    super.initState();
    markers = buildMarkers();
  }

  List<Marker> buildMarkers(){
    return widget.estabelecimentos.map(
      (est) => Marker(
        key: ValueKey(est.id),
        point: LatLng(double.parse(est.endereco.latitude), double.parse(est.endereco.longitude)), 
        child: const Icon(
          Icons.place,
          color: Colors.red
        )
      )
    ).toList();
  }

  @override
   Widget build(BuildContext context) {
    return FlutterMap(
        mapController: MapController(),
        options: MapOptions(
          initialCenter: LatLng(widget.lat, widget.lng),
          initialZoom: 10.5,
        ),
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
                builder: (BuildContext context, Marker marker) => ExamplePopup(marker)
              )
            )
          )
        ],
    );
  }
}