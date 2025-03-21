#!/bin/bash

# Caminho de destino para as chaves
KEYS_DIR="../src/main/resources/keys"
PRIVATE_KEY="$KEYS_DIR/app.key"
PUBLIC_KEY="$KEYS_DIR/app.pub"

# Cria o diretório se não existir
mkdir -p "$KEYS_DIR"

# Gera a chave privada
openssl genrsa -out "$PRIVATE_KEY" 2048

# Gera a chave pública a partir da privada
openssl rsa -in "$PRIVATE_KEY" -pubout -out "$PUBLIC_KEY"

# Exibe as chaves no console (em uma linha)
echo "==========================="
echo "🔐 Chave Privada (uma linha):"
echo "==========================="
awk 'NF { printf "%s", $0 } END { print "" }' "$PRIVATE_KEY"

echo
echo "==========================="
echo "📢 Chave Pública (uma linha):"
echo "==========================="
awk 'NF { printf "%s", $0 } END { print "" }' "$PUBLIC_KEY"

