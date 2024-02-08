import 'dart:convert';

import 'package:flutter_app/models/cliente.dart';
import 'package:flutter_app/models/pedido.dart';
import 'package:http/http.dart' as http;

class ClienteService{
  final String urlAPI = "http://localhost:8080/api/cliente";

  Future<Cliente> find() async{
    final response = await http.get(Uri.parse(urlAPI));
    if (response.statusCode == 200) {
      final json = jsonDecode(response.body);
      return Cliente.fromJson(json);
    }
    else{
      return Future.error("error");
    }
  }

  Future<Cliente> update(Cliente cliente) async{
    final response = await http.put(Uri.parse(urlAPI));
    if (response.statusCode == 200) {
      final json = jsonDecode(response.body);
      return Cliente.fromJson(json);
    }
    else{
      return Future.error("error");
    }
  }

  Future<void> desativarConta() async{
    final response = await http.put(Uri.parse("$urlAPI/desativar"));
    if (response.statusCode == 200) {
      print("sucesso");
    }
    else{
      throw "Unable to delete post.";
    }
  }

  Future<void> ativarConta() async{
    final response = await http.put(Uri.parse("$urlAPI/ativar"));
    if (response.statusCode == 200) {
      print("sucesso");
    }
    else{
      throw "Unable to delete post.";
    }
  }

  Future<List<Pedido>> getPedidos() async{
    final response = await http.get(Uri.parse("$urlAPI/pedidos"));
    if (response.statusCode == 200) {
      final Iterable json = jsonDecode(response.body);
      return json.map((e) => Pedido.fromJson(e)).toList();
    }
    else{
      return Future.error("error");
    }
  }

  Future<Pedido> findPedido(BigInt id) async{
    final response = await http.get(Uri.parse("$urlAPI/pedidos/$id"));
    if (response.statusCode == 200) {
      final json = jsonDecode(response.body);
      return Pedido.fromJson(json);
    }
    else{
      return Future.error("error");
    }
  }

  Future<Pedido> salvarPedido(Pedido pedido) async{
    final response = await http.post(Uri.parse("$urlAPI/pedidos"),body: jsonEncode(pedido));
    if (response.statusCode == 200) {
      final json = jsonDecode(response.body);
      return Pedido.fromJson(json);
    }
    else{
      return Future.error("error");
    }
  }

  Future<void> desativarPedido(BigInt id) async{
    final response = await http.put(Uri.parse("$urlAPI/pedidos/desativar/$id"));
    if (response.statusCode == 200) {
      print("sucesso");
    }
    else{
      throw "Unable to delete post.";
    }
  }

  Future<void> ativarPedido(BigInt id) async{
    final response = await http.put(Uri.parse("$urlAPI/pedidos/ativar/$id"));
    if (response.statusCode == 200) {
      print("sucesso");
    }
    else{
      throw "Unable to delete post.";
    }
  }
}