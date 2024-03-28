import 'package:flutter_app/models/endereco.dart';

class Usuario {
  final int? id;
  final String nome;
  final String email;
  final bool ativo;
  final List<String> permissoes;
  final Endereco endereco;

  const Usuario({
    this.id,
    required this.nome,
    required this.email,
    required this.ativo,
    required this.permissoes,
    required this.endereco
  });

  factory Usuario.fromJson(Map<String, dynamic> json){
    return Usuario(
      id: json['id'] as int, //igual ao long do java
      nome: json['nome'], 
      email: json['email'], 
      ativo: json['ativo'], 
      permissoes: List.castFrom<dynamic, String>(json['permissoes']), 
      endereco: Endereco.fromJson(json['endereco'])
    );
  }

  //pra salvar no storage, precisa serializar usando toMap()
  static Map<String, dynamic> toMap(Usuario u) => 
    <String, dynamic> {
      'id': u.id,
      'nome': u.nome,
      'email': u.email,
      'ativo': u.ativo,
      'permissoes': List<String>.from(u.permissoes),
      'endereco': Endereco.toMap(u.endereco)
    };
}

enum Permissao{
  ADMIN_SISTEMA,
  ADMIN_EMPRESA,
  ADMIN_ESTABELECIMENTO,
  CLIENTE
}