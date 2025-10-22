@echo off
echo ========================================
echo    SHOPPING APP - SERVIDOR NODE.JS
echo ========================================
echo.

echo Verificando se Node.js esta instalado...
node --version
if %errorlevel% neq 0 (
    echo.
    echo ERRO: Node.js nao encontrado!
    echo.
    echo Por favor, instale o Node.js:
    echo 1. Acesse: https://nodejs.org/
    echo 2. Baixe a versao LTS
    echo 3. Execute o instalador
    echo 4. Marque "Add to PATH"
    echo 5. Reinicie o computador
    echo.
    pause
    exit /b 1
)

echo.
echo Node.js encontrado! Versao:
node --version

echo.
echo Instalando dependencias...
npm install

echo.
echo Iniciando servidor...
echo O servidor estara disponivel em: http://localhost:3000
echo Para testar: http://localhost:3000/api/dados
echo.
echo Pressione Ctrl+C para parar o servidor
echo.

node servidor.js
