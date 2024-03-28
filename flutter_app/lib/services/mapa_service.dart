import 'dart:convert';

import 'package:flutter_app/main.dart';
import 'package:flutter_app/models/estabelecimento.dart';
import 'package:flutter_app/services/base_service.dart';
import 'package:http/http.dart';

class MapaService extends BaseService{
  final String urlAPI = "$urlApi/public/estabelecimentos";

  Future<List<Estabelecimento>> findAll() async{
    final response = await client.get(Uri.parse(urlAPI));
    if (response.statusCode == 200) {
      final json = jsonDecode(response.body);
      return List.empty();
    }
    else{
      return Future.error("error");
    }
  }
}