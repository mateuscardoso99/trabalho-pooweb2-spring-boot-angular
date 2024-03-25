import 'package:http_interceptor/http_interceptor.dart';

class HttpInterceptor implements InterceptorContract {
  @override
  Future<BaseRequest> interceptRequest({required BaseRequest request}) async {
    request.headers.addAll({
        'Content-type': 'application/json',
        'Accept': 'application/json'
    });
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