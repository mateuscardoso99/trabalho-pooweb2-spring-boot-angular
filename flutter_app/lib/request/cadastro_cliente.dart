import 'package:flutter_app/models/endereco.dart';

class CadastroClienteRequest{
  String nome;
  String email;
  String senha;
  Endereco? endereco;

  CadastroClienteRequest({
    required this.nome,
    required this.email,
    required this.senha,
    this.endereco
  });

  //converte objeto em Map<String, dynamic> que depois será transformado em json
  Map<String, dynamic> toJson() => {
    'nome': nome,
    'email': email,
    'senha': senha,
    //'endereco': Endereco.toMap(endereco!)
  };
}