import 'package:flutter_app/models/pedido.dart';
import 'package:flutter_app/models/usuario.dart';

class Cliente extends Usuario{
  final List<Pedido> pedidos;

  Cliente({
    required this.pedidos,
    required super.nome, 
    required super.email, 
    required super.ativo, 
    required super.permissoes, 
    required super.endereco
  });

  //retorna uma instancia da classe apartir de um json
  //1° o json é desserializado e transformado em um Map<String, dynamic>, depois é transformado em um objeto da classe
  factory Cliente.fromJson(Map<String, dynamic> json){
    return Cliente(
      pedidos: json['pedidos'] as List<Pedido>, 
      nome: json['nome'].toString(), 
      email: json['email'].toString(), 
      ativo: json['ativo'], 
      permissoes: json['permissoes'], 
      endereco: json['endereco']
    );
  }
}