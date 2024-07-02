import 'dart:convert';

import 'package:flutter_app/api.dart';
import 'package:flutter_app/models/cliente.dart';
import 'package:flutter_app/models/pedido.dart';
import 'package:flutter_app/request/cadastro_cliente.dart';
import 'package:flutter_app/request/cadastro_pedido.dart';
import 'package:flutter_app/services/base_service.dart';
import 'package:http/http.dart';

class ClienteService extends BaseService{
  final String urlAPI = "$urlApi/cliente";

  Future<Cliente> find() async{
    final response = await client.get(Uri.parse(urlAPI));
    if (response.statusCode == 200) {
      final json = jsonDecode(utf8.decode(response.bodyBytes));
      return Cliente.fromJson(json);
    }
    else{
      return Future.error("error");
    }
  }

  Future<Response> update(CadastroClienteRequest cliente) async{
    final response = await client.put(
      Uri.parse(urlAPI),
      body: jsonEncode(cliente)
    );
    return response;
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
      var jsonResponse = jsonDecode(utf8.decode(response.bodyBytes));
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
      final json = jsonDecode(utf8.decode(response.bodyBytes));
      return Pedido.fromJson(json);
    }
    else{
      return Future.error("error");
    }
  }

  Future<Response> salvarPedido(CadastroPedidoRequest pedido) async{
    return await client.post(Uri.parse("$urlAPI/pedidos"),body: jsonEncode(pedido));
  }

  Future<Response> desativarPedido(int id) async{
    return await client.delete(Uri.parse("$urlAPI/pedidos/desativar/$id"));
  }

  Future<void> ativarPedido(int id) async{
    final response = await client.put(Uri.parse("$urlAPI/pedidos/ativar/$id"));
    if (response.statusCode == 200) {
      print("sucesso");
    }
    else{
      throw "Unable to delete post.";
    }
  }
}