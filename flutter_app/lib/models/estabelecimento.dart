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

  factory Estabelecimento.fromJson(Map<String, dynamic> json){
    return switch (json){
      {
        'id': int id, 
        'nome': String nome, 
        'razaoSocial': String razaoSocial, 
        'ativo': bool ativo, 
        'horarioFuncionamento': String horarioFuncionamento,
        'endereco': Map<String, dynamic> endereco
      } => 
      Estabelecimento(
        id: id,
        nome: nome, 
        razaoSocial: razaoSocial, 
        ativo: ativo, 
        horarioFuncionamento: horarioFuncionamento,
        endereco: Endereco.fromJson(endereco)
      ),
      _ => throw const FormatException("erro convert to estabelecimento")
    };
  }
}