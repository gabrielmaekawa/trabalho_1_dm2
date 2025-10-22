// Servidor Node.js simplificado sem dependências externas
const http = require('http');
const fs = require('fs');
const path = require('path');

const port = 3000;

// Função para servir arquivos estáticos
function serveFile(res, filePath, contentType) {
    fs.readFile(filePath, (err, data) => {
        if (err) {
            res.writeHead(404, { 'Content-Type': 'text/plain' });
            res.end('Arquivo não encontrado');
            return;
        }
        res.writeHead(200, { 'Content-Type': contentType });
        res.end(data);
    });
}

// Função para carregar dados JSON
function loadJsonData() {
    try {
        const data = fs.readFileSync(path.join(__dirname, 'dados.json'), 'utf8');
        return JSON.parse(data);
    } catch (err) {
        console.error('Erro ao carregar dados.json:', err.message);
        return { itens: [], categorias: [] };
    }
}

const server = http.createServer((req, res) => {
    // Configurar CORS para permitir requisições do Android
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE');
    res.setHeader('Access-Control-Allow-Headers', 'Content-Type');

    const url = req.url;
    const method = req.method;

    console.log(`${method} ${url}`);

    if (method === 'GET' && url === '/api/dados') {
        const data = loadJsonData();
        res.writeHead(200, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify(data));
    } else if (method === 'GET' && url === '/') {
        res.writeHead(200, { 'Content-Type': 'text/html' });
        res.end(`
            <html>
                <head><title>Shopping App - Servidor</title></head>
                <body>
                    <h1>Shopping App - Servidor Node.js</h1>
                    <p>Servidor rodando na porta ${port}</p>
                    <h2>Endpoints disponíveis:</h2>
                    <ul>
                        <li><a href="/api/dados">GET /api/dados</a> - Dados dos produtos e categorias</li>
                    </ul>
                </body>
            </html>
        `);
    } else {
        res.writeHead(404, { 'Content-Type': 'text/plain' });
        res.end('Endpoint não encontrado');
    }
});

server.listen(port, '0.0.0.0', () => {
    console.log('='.repeat(50));
    console.log('🛍️  SHOPPING APP - SERVIDOR NODE.JS');
    console.log('='.repeat(50));
    console.log(`✅ Servidor rodando com sucesso!`);
    console.log(`🌐 URL: http://localhost:${port}`);
    console.log(`📱 Para Android: http://10.0.2.2:${port}`);
    console.log(`🔗 Teste: http://localhost:${port}/api/dados`);
    console.log('='.repeat(50));
    console.log('Pressione Ctrl+C para parar o servidor');
    console.log('');
});

// Tratamento de erros
server.on('error', (err) => {
    if (err.code === 'EADDRINUSE') {
        console.error(`❌ Erro: Porta ${port} já está em uso!`);
        console.error('Tente fechar outros programas que possam estar usando esta porta.');
    } else {
        console.error('❌ Erro no servidor:', err.message);
    }
    process.exit(1);
});

// Tratamento de sinais para encerramento limpo
process.on('SIGINT', () => {
    console.log('\n🛑 Parando servidor...');
    server.close(() => {
        console.log('✅ Servidor parado com sucesso!');
        process.exit(0);
    });
});
