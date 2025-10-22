// 1. Importa as bibliotecas necessárias
const express = require('express');
const meusDados = require('./dados.json'); // Importa o seu arquivo JSON diretamente

// 2. Inicializa o aplicativo Express
const app = express();
const port = 3000; // Define a porta onde o servidor vai rodar

// 3. Cria um "endpoint" (uma rota) para servir o JSON
// Quando alguém acessar http://localhost:3000/api/dados, esta função será executada.
app.get('/api/dados', (req, res) => {
  // res.json() envia o objeto JSON como resposta,
  // configurando o cabeçalho 'Content-Type' para 'application/json' automaticamente.
  res.json(meusDados);
});

// 4. Inicia o servidor para que ele comece a "ouvir" as requisições
app.listen(port, '0.0.0.0', () => {// Escutando em 0.0.0.0 garante que seja acessível externamente (ouve, na mesma porta, diferentes interfaces de rede. Usamos isso pois vamos acessar a partir do emulador)

  console.log(`Servidor rodando com sucesso em http://localhost:${port}`);
  console.log(`Para acessar seus dados, abra: http://localhost:${port}/api/dados`);
});
