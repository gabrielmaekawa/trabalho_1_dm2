# 🔥 Configuração Completa do Firebase

## Problema: "API key not valid"

O erro acontece porque o arquivo `google-services.json` atual é apenas um placeholder. Você precisa configurar um projeto Firebase real.

## 🚀 **PASSO A PASSO COMPLETO:**

### **Passo 1: Criar Projeto Firebase**

1. **Acesse:** https://console.firebase.google.com/
2. **Clique em:** "Criar um projeto" ou "Add project"
3. **Nome do projeto:** "Shopping App" (ou qualquer nome)
4. **Google Analytics:** Pode ativar ou desativar (opcional)
5. **Clique em:** "Criar projeto"

### **Passo 2: Adicionar App Android**

1. **No painel do projeto,** clique no ícone Android 📱
2. **Package name:** `com.example.myapplication` (exatamente assim)
3. **Nickname:** "Shopping App Android"
4. **SHA-1:** Deixe vazio por enquanto (opcional)
5. **Clique em:** "Registrar app"

### **Passo 3: Baixar google-services.json**

1. **Clique em:** "Baixar google-services.json"
2. **Salve o arquivo** em uma pasta segura
3. **SUBSTITUA** o arquivo placeholder em:
   ```
   app/google-services.json
   ```
4. **IMPORTANTE:** Não edite o arquivo, use exatamente como baixado

### **Passo 4: Ativar Authentication**

1. **No Firebase Console,** vá em "Authentication"
2. **Clique em:** "Começar" ou "Get started"
3. **Vá na aba:** "Sign-in method"
4. **Clique em:** "Email/Senha"
5. **Ative:** "Email/Senha"
6. **Clique em:** "Salvar"

### **Passo 5: Criar Firestore Database**

1. **No Firebase Console,** vá em "Firestore Database"
2. **Clique em:** "Criar banco de dados"
3. **Modo:** "Iniciar no modo de teste" (para desenvolvimento)
4. **Localização:** Escolha a mais próxima (ex: us-central1)
5. **Clique em:** "Próximo"

### **Passo 6: Configurar Regras do Firestore (CRÍTICO)**

1. **Na aba "Regras"** do Firestore
2. **SUBSTITUA** as regras por:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Regra permissiva para desenvolvimento
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

3. **Clique em:** "Publicar"

## 🧪 **TESTE DA CONFIGURAÇÃO:**

### **Teste 1: Compilar o Projeto**
```bash
# No Android Studio
Build → Clean Project
Build → Rebuild Project
```

### **Teste 2: Executar o App**
1. **Execute no emulador**
2. **Vá para a tela de Login** (Tela 5)
3. **Tente criar uma conta** com email e senha
4. **Deve funcionar** sem erro "API key not valid"

### **Teste 3: Verificar Login**
1. **Após criar conta,** deve aparecer "Logado como: seu@email.com"
2. **Navegue para outras telas**
3. **Volte para Login** - deve mostrar botão "Sair"

## 🔍 **VERIFICAÇÕES IMPORTANTES:**

### **Arquivo google-services.json:**
- ✅ Deve estar em `app/google-services.json`
- ✅ Deve ter uma API key real (não "AIzaSyBxxxxxxxx...")
- ✅ Deve ter o package name correto: `com.example.myapplication`

### **Firebase Console:**
- ✅ Projeto criado
- ✅ App Android registrado
- ✅ Authentication ativo (Email/Senha)
- ✅ Firestore criado
- ✅ Regras configuradas

### **Android Studio:**
- ✅ Plugin Google Services ativo
- ✅ Dependências Firebase corretas
- ✅ Projeto compila sem erro

## 🚨 **SOLUÇÕES PARA PROBLEMAS COMUNS:**

### **Erro: "API key not valid"**
- ❌ **Problema:** google-services.json incorreto
- ✅ **Solução:** Baixar arquivo real do Firebase Console

### **Erro: "Project not found"**
- ❌ **Problema:** Project ID incorreto
- ✅ **Solução:** Verificar se o projeto foi criado corretamente

### **Erro: "Authentication failed"**
- ❌ **Problema:** Authentication não ativo
- ✅ **Solução:** Ativar Email/Senha no Firebase Console

### **Erro: "Permission denied"**
- ❌ **Problema:** Regras do Firestore muito restritivas
- ✅ **Solução:** Usar regras permissivas para desenvolvimento

## 📱 **TESTE FINAL:**

1. **Criar conta** no app
2. **Fazer login** com a conta criada
3. **Navegar** entre todas as telas
4. **Testar compra** de um item
5. **Verificar** se a compra aparece na lista

## ✅ **RESULTADO ESPERADO:**

- ✅ Login funciona sem "API key not valid"
- ✅ Contas são criadas no Firebase
- ✅ Dados são salvos no Firestore
- ✅ Navegação entre telas funciona
- ✅ Todas as funcionalidades operacionais

## 📞 **SE AINDA HOUVER PROBLEMAS:**

1. **Verifique** se seguiu todos os passos
2. **Confirme** que o google-services.json é o arquivo real
3. **Teste** criar um novo projeto Firebase
4. **Verifique** se o package name está correto

O erro "API key not valid" será resolvido assim que você configurar o Firebase corretamente! 🎉

