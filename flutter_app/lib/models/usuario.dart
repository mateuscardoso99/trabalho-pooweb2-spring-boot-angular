
import 'package:flutter_app/models/endereco.dart';

class Usuario {
  final BigInt? id;
  final String nome;
  final String email;
  final bool ativo;
  final List<Permissao> permissoes;
  final Endereco endereco;

  const Usuario({
    this.id,
    required this.nome,
    required this.email,
    required this.ativo,
    required this.permissoes,
    required this.endereco
  });
}

enum Permissao{
  ADMIN_SISTEMA,
  ADMIN_EMPRESA,
  ADMIN_ESTABELECIMENTO,
  CLIENTE
}