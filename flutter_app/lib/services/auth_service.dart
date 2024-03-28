import 'dart:convert';

import 'package:flutter_app/api.dart';
import 'package:flutter_app/request/cadastro_cliente.dart';
import 'package:flutter_app/request/login.dart';
import 'package:flutter_app/services/base_service.dart';
import 'package:http/http.dart' as http;

class AuthService extends BaseService{

  Future<http.Response> login(String email, String senha) async {
    var request = LoginRequest(email: email, senha: senha);
    var res = await client.post(
      Uri.parse("$urlApi/login"),
      body: jsonEncode(request)
    );
    return res;
  }

  Future<http.Response> signUp(CadastroClienteRequest dados) async {
    var res = await client.post(
      Uri.parse("$urlApi/register"),
      body: jsonEncode(dados)
    );
    return res;    
  }
}