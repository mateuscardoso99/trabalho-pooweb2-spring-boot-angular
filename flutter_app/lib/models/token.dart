import 'package:flutter_app/models/usuario.dart';

class Token{
  final String token;
  final Usuario usuario;

  const Token({
    required this.token,
    required this.usuario
  });
}