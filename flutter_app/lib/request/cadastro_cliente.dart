import 'package:flutter_app/models/endereco.dart';

class CadastroClienteRequest{
  String nome;
  String email;
  String senha;
  Endereco endereco;

  CadastroClienteRequest({
    required this.nome,
    required this.email,
    required this.senha,
    required this.endereco
  });
}