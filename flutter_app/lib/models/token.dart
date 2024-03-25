import 'dart:convert';

import 'package:flutter_app/models/usuario.dart';

class Token{
  final String token;
  final Usuario usuario;

  const Token({
    required this.token,
    required this.usuario
  });

  //retorna uma instancia da classe apartir de um json
  //1° o json é desserializado e transformado em um Map<String, dynamic>, depois é transformado em um objeto da classe
  factory Token.fromJson(Map<String, dynamic> json){
    return Token(
      token: json['token'], 
      usuario: Usuario.fromJson(json['usuario'])
    );
  }

  //pra salvar no storage, precisa serializar usando toMap()
  static Map<String, dynamic> toMap(Token t) => 
    <String, dynamic> {
      'token': t.token,
      'usuario': Usuario.toMap(t.usuario)
    };

  static String serialize(Token t) => json.encode(Token.toMap(t));
  static Token deserialize(String json) => Token.fromJson(jsonDecode(json));//jsonDecode retorna um Map<String, dynamic>
}