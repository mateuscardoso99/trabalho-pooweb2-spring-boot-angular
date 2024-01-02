# flutter_app

A new Flutter project.

## Getting Started

This project is a starting point for a Flutter application.

A few resources to get you started if this is your first Flutter project:

- [Lab: Write your first Flutter app](https://docs.flutter.dev/get-started/codelab)
- [Cookbook: Useful Flutter samples](https://docs.flutter.dev/cookbook)

For help getting started with Flutter development, view the
[online documentation](https://docs.flutter.dev/), which offers tutorials,
samples, guidance on mobile development, and a full API reference.


# pastas: 
android: este diretório contém todos os arquivos e configurações específicos do Android, incluindo AndroidManifest.xml, arquivos de compilação Gradle e outros ativos necessários para criar a versão Android do seu aplicativo.

ios: Da mesma forma, o diretório ios contém todos os arquivos específicos do iOS, incluindo o arquivo Info.plist, o projeto Xcode e os ativos necessários para construir a versão iOS do seu aplicativo.

lib: O diretório lib é onde você passará a maior parte do tempo escrevendo código. Ele abriga o código Dart que constitui a funcionalidade principal do seu aplicativo Flutter. O ponto de entrada principal do seu aplicativo, normalmente denominado main.dart, reside aqui. Você criará widgets, telas e lógica personalizados neste diretório.

test: onde ficam os testes

web: Se você planeja criar uma versão web do seu aplicativo Flutter, encontrará o diretório web aqui. Ele contém o código específico da web e os recursos necessários para executar seu aplicativo em um navegador da web.

pubspec.yaml: gerencia as dependencias

# widgets
No Flutter, tudo é um widget. Widgets são basicamente componentes de interface de usuário usados ​​para criar a interface de usuário do aplicativo.

# scaffold
O Scaffold é um widget no Flutter usado para implementar a estrutura básica do layout visual do material design. 
todas as telas partem apartir do Scaffold
ex: 
main.dart:
@override
Widget build(BuildContext context) {
    return const MaterialApp(
        home: ScaffoldExample(),
    );
}

tela qualquer:
@override
  Widget build(BuildContext context) {
    return Scaffold(
        //
    )
}

# gestures
Os widgets Flutter suportam interação por meio de um widget especial, GestureDetector . GestureDetector é um widget invisível que tem a capacidade de capturar interações do usuário, como tocar, arrastar, etc., de seu widget filho. Muitos widgets nativos do Flutter suportam interação por meio do uso do GestureDetector

# estado
Um widget com estado é um tipo de widget no Flutter que pode alterar sua aparência e comportamento com base nas interações do usuário ou em outros fatores. Sempre que o estado de um widget com estado muda, o Flutter reconstrói a árvore de widgets para refletir essas alterações.

Quando você cria um widget com estado, o Flutter chama o método createState() para criar um objeto State correspondente que gerencia o estado do widget

Os widgets Flutter oferecem suporte à manutenção do estado , fornecendo um widget especial, StatefulWidget . O widget precisa ser derivado do widget StatefulWidget para oferecer suporte à manutenção do estado e todos os outros widgets devem ser derivados do StatefulWidget . Os widgets Flutter são reativos no nativo. Isso é semelhante ao reactjs e o StatefulWidget será renderizado automaticamente sempre que seu estado interno for alterado. A nova renderização é otimizada encontrando a diferença entre a interface de usuário do widget antigo e o novo e renderizando apenas as alterações necessárias

cada widget tem um estado associado a ele

# layers
O conceito mais importante da estrutura Flutter é que a estrutura é agrupada em múltiplas categorias em termos de complexidade e claramente organizada em camadas de complexidade decrescente. Uma camada é construída usando sua camada de nível seguinte imediato. A camada superior é o widget específico para Android e iOS . A próxima camada contém todos os widgets nativos flutuantes. A próxima camada é a camada de renderização , que é um componente de renderização de baixo nível e renderiza tudo no aplicativo flutter. As camadas vão até o código específico da plataforma central

# StatelessWidget
StatelessWidget: Este tipo de widget não possibilita alterações dinâmicas, entenda-o como algo completamente estático. 
Eles são amplamente utilizados para a criação de estruturas não mutáveis nos aplicativos (telas, menus, imagens etc.), 
ou seja, tudo que não envolva entradas de dados dos usuários, acessos a APIs e coisas que mudem ao longo do processo.

# StatefulWidget
StatefulWidget: os widgets Stateful são praticamente o oposto dos Stateless. Eles contêm estado e isso os torna mutáveis.

# + estado
um estado é mapeado como uma classe que extende de State, essa classe sobrescreve o método build() esse método recria a interface para aplicar as
mudanças na tela, por extender de State tem acesso ao método setState() para mudar o estado

toda classe que extende de StatefulWidget deve sobrescrever o método createState(), esse método cria um estado para esse widget