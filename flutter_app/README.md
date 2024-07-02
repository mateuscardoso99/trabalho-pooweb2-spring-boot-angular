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

# install
- precisa instalar o Android Studio para obter o Android SDK. Depois de usar o Android Studio para obter o Android SDK, pode continuar usando o VScode para desenvolvimento flutter.

-precisará do Android SDK para poder desenvolver aplicativos Android. 
não precisa instalar o android studio, mas terá que usar as ferramentas de linha de comando, (sdkmanager)
e instalar todos os pacotes necessários manualmente
pacotes: build-tools, emulator, platform-tools, system-images;android-34;default;x86_64, platforms;android-34

-não precisa especificamente do Android Studio, tudo que precisa é do Android SDK, baixá-lo e definir a variável de ambiente para o 
caminho do SDK para que a instalação do flutter reconheça isso.

*android studio instala por padrão o emulador e o sdk android

variáveis de ambiente:
ANDROID_SDK_ROOT = Path to your SDK android folder
ANDROID_HOME = The same as ANDROID_SDK_ROOT

adicionar na variável path:
[Path to your SDK folder]/emulator
[Path to your SDK folder]/platform-tools
[Path to your SDK folder]/tools/bin

adicionar na variável path tanto do usuário como do sistema o caminho do sdk do flutter:
C:\Users\Downloads\flutter\bin

depois rodar flutter doctor pra ver se achou o sdk android

se não instalar o android studio precisa configurar manualmente o emulador..

criar emulador:
-avdmanager create avd --name [nome do emulador] --package "system-images;android-28;default;x86" 
//--package é a versão da imagem do android, nesse exemplo é a api nivel 28

rodar emulador:
-emulator -avd [nome do emulador]

# obs
Não usar a palavra-chave const se não estiver usando valores fixos. Por exemplo, no caso de Text, se a string for constante, Text("something here") deve usar o const, mas se a string de Textfor dinâmica, não usar const antes Text do widget. const Text("something") e Text(anyVariabale). O mesmo acontece com todos os widgets.
se a classe que representa a tela for imutável (não for dinâmica (não tiver valores dinâmicos na tela) ) usar const para chamá-la

pub: gerenciador de pacotes do Dart

import 'package:json_annotation/json_annotation.dart'; gera json automaticamente adicionando os métodos:
Map<String, dynamic> toJson() => _$UserToJson(this); 
factory User.fromJson(Map<String, dynamic> json) => _$UserFromJson(json);
e anotação @JsonSerializable()

fromJson: retorna uma instancia da classe apartir de um json.
1° o json é desserializado e transformado em um Map<String, dynamic>, depois é transformado em um objeto da classe

toJson: converte objeto da classe em Map<String, dynamic>, depois jsonEncode() chamará esse método transformará em json

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

Widgets sem estado são widgets imutáveis, o que significa que não mudam. Qualquer informação que eles carreguem permanece constante durante toda a vida útil do widget

# StatefulWidget
StatefulWidget: os widgets Stateful são praticamente o oposto dos Stateless. Eles contêm estado e isso os torna mutáveis.
Widgets com estado são widgets cujas informações e propriedades podem mudar durante o tempo de execução do widget. Suas propriedades podem ser atualizadas, alteradas ou excluídas enquanto ainda está em tempo de execução

# + estado
um estado é mapeado como uma classe que extende de State, essa classe sobrescreve o método build() esse método recria a interface para aplicar as
mudanças na tela, por extender de State tem acesso ao método setState() para mudar o estado

toda classe que extende de StatefulWidget deve sobrescrever o método createState(), esse método cria um estado para esse widget

# late
variável não nula, que será inicializada posteriormente

# future
O recurso Future permite que você execute o trabalho de forma assíncrona para liberar quaisquer outros segmentos/threads que não devem ser bloqueados, como o segmento/thread da interface do usuário.

Como exemplo, para ilustar, pense na tarefa de recuperar dados de um servidor remoto usando http. Neste cenário temos as seguintes situações:

1 - Quando o Future for concluído com êxito, precisaremos exibir o resultado para o usuário;
2 - Quando o Future for concluído com um erro, precisamos exibir notificações para o usuário;
3 - Quando a solicitação ainda está em andamento, podemos mostrar um controle de carregamento para indicar isso.

Isso geralmente significa que precisamos ter UIs diferentes para os três estados possíveis em que um Future pode estar.

pending - Exibir um spinner de carregamento.
completed with value - Exibir o resultado.
completed with error - Exibir uma notificação de erro.
Para poder tratar esses comportamentos o Flutter tem um widget stateful embutido, chamado FutureBuilder, que se constrói baseado no último instantâneo(snapshot) da interação com o Future.

Assim, usando FutureBuilder podemos determinar o estado atual de um Future e selecionar o que mostrar enquanto está carregando, quando se torna disponível ou quando ocorre um erro.

O Construtor da classe FutureBuilder pode usar até 3 parâmetros:

future:  recebe os dados após algum intervalo e representa um processamento assíncrono;
builder: recebe os dados do futuro e retorna o widget baseado em uma interação assíncrona;
initialData: é opcional, representa o snapshot inicial dos dados antes de um futuro não nulo ser concluído;

Sempre que quiser trabalhar com FutureBuilder, não coloque a função diretamente na propriedade future, pois o setState sempre executa o método build, iniciando novamente a função.

Crie uma variável do tipo Future<Tipo> e inicie a função no InitState de seu widget;

Utilize essa variável na propriedade future, dessa forma não iniciará novamente a função com um setState;

# setState()
para renderizar algo na tela novamente, usar setState(){} pra alterar a propriedade

# components
center: Um widget que centraliza seu filho dentro de si mesmo.

column: widget que exibe seus filhos em uma matriz vertical.
Para fazer com que um filho se expanda para preencher o espaço vertical disponível, envolva o filho em um widget Expanded.
O widget Coluna não rola (e em geral é considerado um erro ter mais filhos em uma Coluna do que cabe na sala disponível). 
Se você tiver uma linha de widgets e quiser que eles possam rolar se não houver espaço suficiente, considere 
usar um ListView.

row: o mesmo de column, só que na horizontal

SingleChildScrollView:
widget é útil quando você tem uma única caixa que normalmente estará totalmente visível, por exemplo, um mostrador 
de relógio em um seletor de tempo, mas você precisa ter certeza de que ele pode ser rolado se o contêiner ficar 
muito pequeno em um eixo (a direção de rolagem ).

ListView: widget de rolagem mais comumente usado. Ele exibe seus filhos um após o outro na direção de rolagem. 
No eixo cruzado, os filhos são obrigados a preencher o ListView.

Stack: widget que posiciona seus filhos em relação às bordas de sua caixa. Esta classe é útil se você deseja 
sobrepor vários filhos de uma forma simples, por exemplo tendo algum texto e uma imagem

TextFormField: campo de texto com integração com a classe Form

GlobalKey: chaves devem ser exclusivos em todo o aplicativo.

LocalKey: As chaves devem ser exclusivas entre os Elements com o mesmo pai.

ValueKey: chave que usa um valor de um tipo específico para se identificar

# https://docs.flutter.dev/




# esse projeto flutter contém:
-login jwt e consumo da api
-arrastar a tela pra atualizar o contéudo
-uso de geolocalização
-uso de mapa com dados dinâmicos
-armazena jwt no storage do celular
