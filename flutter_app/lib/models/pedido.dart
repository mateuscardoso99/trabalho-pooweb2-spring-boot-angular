class Pedido{
  final int? id;
  final String descricao;
  final String statusPedido;
  final String dataHora;
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
    return switch (json){
      {
        'id': int id, 
        'descricao': String descricao, 
        'dataHora': String dataHora, 
        'statusPedido': String statusPedido, 
        'estabelecimento': String estabelecimento, 
        'nomeCliente': String nomeCliente,
        'emailCliente': String emailCliente
      } => 
      Pedido(
        id: id,
        descricao: descricao, 
        dataHora: dataHora, 
        statusPedido: statusPedido, 
        estabelecimento: estabelecimento, 
        nomeCliente: nomeCliente, 
        emailCliente: emailCliente
      ),
      _ => throw const FormatException("erro convert to pedido")
    };
  }
}

enum StatusPedido{
  PENDENTE,
  CANCELADO,
  FINALIZADO
}