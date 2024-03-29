//import 'package:json_annotation/json_annotation.dart'; gera json automaticamente adicionando os métodos
//Map<String, dynamic> toJson() => _$UserToJson(this); 
//factory User.fromJson(Map<String, dynamic> json) => _$UserFromJson(json);
//e anotação @JsonSerializable()

class CadastroPedidoRequest{
  String descricao;
  int idEstabelecimento;

  CadastroPedidoRequest({
    required this.descricao,
    required this.idEstabelecimento
  });

  //converte objeto em Map<String, dynamic> que depois será transformado em json
  Map<String, dynamic> toJson() => {
    'descricao': descricao,
    'idEstabelecimento': idEstabelecimento,
  };
}