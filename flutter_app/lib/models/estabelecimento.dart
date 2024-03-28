import 'package:flutter_app/models/endereco.dart';

class Estabelecimento{
  final int? id;
  final String nome;
  final String razaoSocial;
  final String horarioFuncionamento;
  final bool ativo;
  final Endereco endereco;

  const Estabelecimento({
    this.id,
    required this.nome,
    required this.razaoSocial,
    required this.ativo,
    required this.horarioFuncionamento,
    required this.endereco
  });
}