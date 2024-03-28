import 'dart:convert';

import 'package:flutter_app/api.dart';
import 'package:flutter_app/models/cliente.dart';
import 'package:flutter_app/models/pedido.dart';
import 'package:flutter_app/services/base_service.dart';

class ClienteService extends BaseService{
  final String urlAPI = "$urlApi/cliente";

  Future<Cliente> find() async{
    final response = await client.get(Uri.parse(urlAPI));
    if (response.statusCode == 200) {
      final json = jsonDecode(response.body);
      return Cliente.fromJson(json);
    }
    else{
      return Future.error("error");
    }
  }

  Future<Cliente> update(Cliente cliente) async{
    final response = await client.put(Uri.parse("$urlAPI/update"));
    if (response.statusCode == 200) {
      final json = jsonDecode(response.body);
      return Cliente.fromJson(json);
    }
    else{
      return Future.error("error");
    }
  }

  Future<void> desativarConta() async{
    final response = await client.put(Uri.parse("$urlAPI/desativar"));
    if (response.statusCode == 200) {
      print("sucesso");
    }
    else{
      throw "Unable to delete post.";
    }
  }

  Future<void> ativarConta() async{
    final response = await client.put(Uri.parse("$urlAPI/ativar"));
    if (response.statusCode == 200) {
      print("sucesso");
    }
    else{
      throw "Unable to delete post.";
    }
  }

  Future<List<Pedido>> getPedidos() async{
    final response = await client.get(Uri.parse("$urlAPI/pedidos"));
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(response.body);
      final Iterable json = jsonResponse['data'];
      final List<Pedido> pedidos = json.map((e) => Pedido.fromJson(e)).toList();
      return pedidos;
    }
    else{
      return Future.error("error");
    }
  }

  Future<Pedido> findPedido(BigInt id) async{
    final response = await client.get(Uri.parse("$urlAPI/pedidos/$id"));
    if (response.statusCode == 200) {
      final json = jsonDecode(response.body);
      return Pedido.fromJson(json);
    }
    else{
      return Future.error("error");
    }
  }

  Future<Pedido> salvarPedido(Pedido pedido) async{
    final response = await client.post(Uri.parse("$urlAPI/pedidos"),body: jsonEncode(pedido));
    if (response.statusCode == 200) {
      final json = jsonDecode(response.body);
      return Pedido.fromJson(json);
    }
    else{
      return Future.error("error");
    }
  }

  Future<void> desativarPedido(BigInt id) async{
    final response = await client.put(Uri.parse("$urlAPI/pedidos/desativar/$id"));
    if (response.statusCode == 200) {
      print("sucesso");
    }
    else{
      throw "Unable to delete post.";
    }
  }

  Future<void> ativarPedido(BigInt id) async{
    final response = await client.put(Uri.parse("$urlAPI/pedidos/ativar/$id"));
    if (response.statusCode == 200) {
      print("sucesso");
    }
    else{
      throw "Unable to delete post.";
    }
  }
}