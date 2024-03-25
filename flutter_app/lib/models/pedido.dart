class Pedido{
  final int? id;
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

  //retorna uma instancia da classe apartir de um json
  //1° o json é desserializado e transformado em um Map<String, dynamic>, depois é transformado em um objeto da classe
  factory Pedido.fromJson(Map<String, dynamic> json){
    return Pedido(
      id: json['id'] as int, 
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