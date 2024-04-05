class Endereco {
  final int? id;
  final String cidade;
  final String uf;
  final String bairro;
  final String rua;
  final String numero;
  final String? complemento;
  final String? latitude;
  final String? longitude;

  Endereco({
    this.id,
    required this.cidade,
    required this.uf,
    required this.bairro,
    required this.rua,
    required this.numero,
    this.complemento,
    this.latitude,
    this.longitude,
  });

  //retorna uma instancia da classe apartir de um json
  //1° o json é desserializado e transformado em um Map<String, dynamic>, depois é transformado em um objeto da classe
  factory Endereco.fromJson(Map<String, dynamic> json) {
    return Endereco(
        id: json['id'] as int,
        cidade: json['cidade'].toString(),
        bairro: json['bairro'],
        rua: json['rua'],
        numero: json['numero'],
        uf: json['uf'],
        complemento: json['complemento'],
        latitude: json['latitude'],
        longitude: json['longitude']);
  }

  static Map<String, dynamic> toMap(Endereco e) => <String, dynamic>{
        'id': e.id,
        'cidade': e.cidade,
        'uf': e.uf,
        'bairro': e.bairro,
        'rua': e.rua,
        'numero': e.numero,
        'complemento': e.complemento,
        'latitude': e.latitude,
        'longitude': e.longitude,
      };
}
