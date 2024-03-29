import 'dart:convert';

import 'package:flutter_app/api.dart';
import 'package:flutter_app/models/estabelecimento.dart';
import 'package:flutter_app/services/base_service.dart';

class MapaService extends BaseService{
  final String urlAPI = "$urlApi/public/estabelecimentos";

  Future<List<Estabelecimento>> findAll() async{
    final response = await client.get(Uri.parse(urlAPI));
    if (response.statusCode == 200) {
      final json = jsonDecode(utf8.decode(response.bodyBytes));
      final Iterable iterable = json['data'];
      List<Estabelecimento> e = iterable.map((e) => Estabelecimento.fromJson(e)).toList();
      return e;
    }
    else{
      return Future.error("error");
    }
  }

  Future<Estabelecimento> findById(id) async{
    final response = await client.get(Uri.parse("$urlAPI/$id"));
    if (response.statusCode == 200) {
      final json = jsonDecode(utf8.decode(response.bodyBytes));
      return Estabelecimento.fromJson(json['data']);
    }
    else{
      return Future.error("error");
    }
  }
}