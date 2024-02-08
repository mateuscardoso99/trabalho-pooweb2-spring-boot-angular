
class Endereco {
  final BigInt? id;
  final String cidade;
  final String uf;
  final String bairro;
  final String rua;
  final String numero;
  final String complemento;
  final String latitude;
  final String longitude;

  const Endereco({
    this.id,
    required this.cidade,
    required this.uf,
    required this.bairro,
    required this.rua,
    required this.numero,
    required this.complemento,
    required this.latitude,
    required this.longitude,
  });
}
