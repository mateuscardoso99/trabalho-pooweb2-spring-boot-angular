import 'package:flutter_app/api.dart';
import 'package:flutter_app/models/token.dart';
import 'package:http_interceptor/http_interceptor.dart';

class HttpInterceptor implements InterceptorContract {
  @override
  Future<BaseRequest> interceptRequest({required BaseRequest request}) async {
    request.headers.addAll({
        'Content-type': 'application/json',
        'Accept': 'application/json'
    });
    final String? userStorage = await storage.read(key: "user");
    if(userStorage != null){
      final String token = Token.deserialize(userStorage).token;
      request.headers.putIfAbsent("Authorization", () => "Bearer $token");
    }
    return request;
  }

  @override
  Future<BaseResponse> interceptResponse({required BaseResponse response}) async {
      print(response.toString());
      return response;
  }
  
  @override
  Future<bool> shouldInterceptRequest() async => true;
  
  @override
  Future<bool> shouldInterceptResponse() async => true;

}