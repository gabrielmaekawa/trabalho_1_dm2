// Script para testar se o servidor está funcionando
const http = require('http');

function testServer() {
    console.log('🧪 Testando servidor...');
    
    const options = {
        hostname: 'localhost',
        port: 3000,
        path: '/api/dados',
        method: 'GET'
    };

    const req = http.request(options, (res) => {
        console.log(`✅ Servidor respondendo: ${res.statusCode}`);
        
        let data = '';
        res.on('data', (chunk) => {
            data += chunk;
        });
        
        res.on('end', () => {
            try {
                const jsonData = JSON.parse(data);
                console.log(`📦 Itens encontrados: ${jsonData.itens?.length || 0}`);
                console.log(`📂 Categorias encontradas: ${jsonData.categorias?.length || 0}`);
                
                if (jsonData.categorias && jsonData.categorias.length > 0) {
                    console.log('🎯 Primeiras 3 categorias:');
                    jsonData.categorias.slice(0, 3).forEach((cat, index) => {
                        console.log(`   ${index + 1}. ${cat.descricao} - ${cat.foto}`);
                    });
                } else {
                    console.log('❌ Nenhuma categoria encontrada!');
                }
                
                if (jsonData.itens && jsonData.itens.length > 0) {
                    console.log('🛍️ Primeiros 3 itens:');
                    jsonData.itens.slice(0, 3).forEach((item, index) => {
                        console.log(`   ${index + 1}. ${item.nome} - R$ ${item.valor}`);
                    });
                } else {
                    console.log('❌ Nenhum item encontrado!');
                }
                
            } catch (error) {
                console.log('❌ Erro ao parsear JSON:', error.message);
                console.log('📄 Resposta bruta:', data.substring(0, 200) + '...');
            }
        });
    });

    req.on('error', (error) => {
        console.log('❌ Erro ao conectar com o servidor:', error.message);
        console.log('💡 Certifique-se de que o servidor está rodando na porta 3000');
        console.log('💡 Execute: node servidor.js');
    });

    req.setTimeout(5000, () => {
        console.log('⏰ Timeout - servidor não respondeu em 5 segundos');
        req.destroy();
    });

    req.end();
}

testServer();

