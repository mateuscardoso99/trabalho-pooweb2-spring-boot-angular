class LoginRequest{
  String email;
  String senha;

  LoginRequest({
    required this.email,
    required this.senha
  });

  //converte objeto em Map<String, dynamic> que depois será transformado em json
  //método jsonEncode chama esse método automaticamente
  Map<String, dynamic> toJson() => {
    'email': email,
    'senha': senha,
  };
}