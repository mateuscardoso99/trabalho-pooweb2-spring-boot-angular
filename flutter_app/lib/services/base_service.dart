import 'package:flutter_app/services/interceptor.dart';
import 'package:http_interceptor/http_interceptor.dart';

class BaseService{
  Client client = InterceptedClient.build(interceptors: [
      HttpInterceptor(),
  ]);
}