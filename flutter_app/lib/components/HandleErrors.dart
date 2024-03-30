class HandleErrors{
  final Map<String, dynamic> response;
  const HandleErrors({required this.response});

  List<String> errors(){
    final List<String> errorsMsg = [];
    if(!response.containsKey("errors")){
      errorsMsg.add("tente novamente");
    }
    else{
      Map<String, dynamic> mapErrors = response["errors"];
      if(mapErrors.containsKey("errors")){
        for(var msg in mapErrors["errors"]){
          errorsMsg.add(msg);
        }
      }                        
    }
    return errorsMsg;
  }

}
