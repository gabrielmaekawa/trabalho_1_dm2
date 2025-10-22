# 🔥 Configuração do Firebase

## Problema: Permission Denied

O erro "permission denied" indica que as regras do Firestore estão bloqueando o acesso. Siga estes passos para resolver:

## 1. Configurar Firebase Console

### Passo 1: Criar Projeto
1. Acesse: https://console.firebase.google.com/
2. Clique em "Criar um projeto"
3. Nome: "Shopping App" (ou qualquer nome)
4. Ative/desative Google Analytics conforme preferir
5. Clique em "Criar projeto"

### Passo 2: Adicionar App Android
1. No painel do projeto, clique no ícone Android
2. **Package name**: `com.example.myapplication`
3. **Nickname**: "Shopping App Android"
4. **SHA-1**: (opcional para desenvolvimento)
5. Clique em "Registrar app"

### Passo 3: Baixar google-services.json
1. Baixe o arquivo `google-services.json`
2. **SUBSTITUA** o arquivo placeholder em `app/google-services.json`
3. **IMPORTANTE**: Mantenha o arquivo exato, não edite

## 2. Ativar Authentication

### Passo 1: Configurar Authentication
1. No Firebase Console, vá em "Authentication"
2. Clique em "Começar"
3. Vá na aba "Sign-in method"
4. Ative "Email/Senha"
5. Clique em "Salvar"

## 3. Configurar Firestore Database

### Passo 1: Criar Database
1. No Firebase Console, vá em "Firestore Database"
2. Clique em "Criar banco de dados"
3. **Modo**: "Iniciar no modo de teste" (para desenvolvimento)
4. Escolha uma localização (ex: us-central1)
5. Clique em "Próximo"

### Passo 2: Configurar Regras (IMPORTANTE)
1. Vá na aba "Regras" do Firestore
2. **SUBSTITUA** as regras padrão por:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Regras para compras do usuário
    match /purchases/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
      
      match /user_purchases/{purchaseId} {
        allow read, write: if request.auth != null && request.auth.uid == userId;
      }
    }
    
    // Regra permissiva para desenvolvimento (REMOVER EM PRODUÇÃO)
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

3. Clique em "Publicar"

## 4. Testar a Configuração

### Passo 1: Executar o App
1. Compile e execute o app no emulador
2. Vá para a tela de Login (Tela 5)
3. Crie uma conta ou faça login
4. Navegue para a tela de Compras (Tela 2)
5. Deve funcionar sem erro "permission denied"

### Passo 2: Testar Compra
1. Vá para a tela de Itens (Tela 1)
2. Clique em um item
3. Confirme a compra
4. Deve aparecer "Compra realizada com sucesso!"

## 5. Solução de Problemas

### Erro: "User not logged in"
- Certifique-se de fazer login na tela de Login primeiro

### Erro: "Permission denied"
- Verifique se as regras do Firestore foram aplicadas corretamente
- Certifique-se de que o Authentication está ativo

### Erro: "Failed to get document"
- Verifique se o Firestore Database foi criado
- Certifique-se de que o google-services.json está correto

## 6. Arquivos Importantes

- `app/google-services.json` - Configuração do Firebase
- `firestore.rules` - Regras do Firestore (para referência)
- `FirebaseAuthService.kt` - Serviço de autenticação
- `FirestoreService.kt` - Serviço do Firestore

## 7. Notas de Segurança

⚠️ **IMPORTANTE**: As regras atuais são permissivas para desenvolvimento. Em produção, use regras mais restritivas:

```javascript
// Regras de produção mais seguras
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /purchases/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
      
      match /user_purchases/{purchaseId} {
        allow read, write: if request.auth != null && request.auth.uid == userId;
      }
    }
  }
}
```

