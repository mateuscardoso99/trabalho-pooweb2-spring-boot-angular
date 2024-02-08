class Pedido{
  final BigInt? id;
  final String descricao;
  final StatusPedido statusPedido;
  final DateTime dataHora;
  final String estabelecimento;
  final String nomeCliente;
  final String emailCliente;

  const Pedido({
    this.id,
    required this.descricao,
    required this.dataHora,
    required this.statusPedido,
    required this.estabelecimento,
    required this.nomeCliente,
    required this.emailCliente
  });

  factory Pedido.fromJson(Map<String, dynamic> json){
    return Pedido(
      id: json['id'] as BigInt, 
      descricao: json['descricao'], 
      dataHora: json['dataHora'], 
      statusPedido: json['statusPedido'], 
      estabelecimento: json['estabelecimento'], 
      nomeCliente: json['nomeCliente'],
      emailCliente: json['emailCliente']
    );
  }
}

enum StatusPedido{
  PENDENTE,
  CANCELADO,
  FINALIZADO
}