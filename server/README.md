# Servidor Node.js - Shopping App

Este é o servidor backend para o aplicativo de compras Android.

## Configuração

1. Navegue até a pasta `server`
2. Execute os comandos:
```bash
npm install
npm start
```

3. O servidor estará rodando em `http://localhost:3000`
4. Teste a API em: `http://localhost:3000/api/dados`

## Estrutura

- `servidor.js` - Servidor Express principal
- `dados.json` - Dados dos produtos e categorias
- `package.json` - Dependências do projeto

## Endpoints

- `GET /api/dados` - Retorna todos os dados (produtos e categorias)

## Nota

Certifique-se de que o servidor esteja rodando antes de executar o aplicativo Android, pois ele se conecta via `http://10.0.2.2:3000/` (IP do host no emulador Android).
