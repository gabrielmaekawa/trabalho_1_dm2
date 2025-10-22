# Shopping App

Um aplicativo de compras desenvolvido em Android com as seguintes funcionalidades:

## Funcionalidades

### Telas
- **Tela 1 (Itens)**: Lista de produtos com nome, valor e foto (2 colunas)
- **Tela 2 (Compras)**: Lista de compras do usuário logado
- **Tela 3 (Categorias)**: Lista de categorias com descrição e foto (2 colunas)
- **Tela 4 (Info)**: Tela em desenvolvimento
- **Tela 5 (Login)**: Autenticação com Firebase

### Funcionalidades Técnicas
- Navegação via BottomNavigationView
- Fragmentos para cada tela
- Firebase Authentication para login
- Firestore Database para armazenar compras
- Retrofit para consumir API Node.js
- RecyclerView para listas de itens e categorias
- ListView para lista de compras
- Glide para carregamento de imagens

## Configuração

### 1. Firebase Setup
1. Crie um projeto no [Firebase Console](https://console.firebase.google.com/)
2. Adicione um app Android com o package name: `com.example.myapplication`
3. Baixe o arquivo `google-services.json` e substitua o arquivo placeholder no diretório `app/`
4. Ative Authentication (Email/Password) no Firebase Console
5. Ative Firestore Database no Firebase Console

### 2. Servidor Node.js
1. Crie uma pasta para o servidor
2. Baixe o arquivo `dados.json` fornecido
3. Execute os comandos:
```bash
npm init -y
npm install express
```
4. Crie o arquivo `servidor.js` com o código fornecido
5. Execute: `node servidor.js`
6. Teste em: `http://localhost:3000/api/dados`

### 3. Executar o App
1. Certifique-se que o servidor Node.js está rodando
2. Execute o app no emulador Android
3. O app se conectará automaticamente ao servidor local via `http://10.0.2.2:3000/`

## Estrutura do Projeto

```
app/src/main/java/com/example/myapplication/
├── adapter/           # Adapters para RecyclerView e ListView
├── firebase/          # Serviços do Firebase
├── fragments/         # Fragmentos das telas
├── model/            # Modelos de dados
├── network/          # Configuração do Retrofit
└── MainActivity.kt   # Activity principal
```

## Pontuação do Projeto

- ✅ (3,0 pontos) Popular a lista de itens a partir do servidor Node.js (Tela 1)
- ✅ (2,5 pontos) Popular a lista de categorias a partir do servidor Node.js (Tela 3)
- ✅ (2,5 pontos) Registrar a compra por usuário no Firestore Database (ao clicar no item da Tela 1)
- ✅ (2,0 ponto) Construção das telas, layout
- ✅ (1,0 extra) Exibir as compras do usuário logado na Tela 2

## Dependências Principais

- Retrofit 2.11.0
- Firebase BOM 33.5.0
- Glide 4.16.0
- Material Design Components
- Navigation Components
