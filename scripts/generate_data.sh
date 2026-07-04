#!/bin/bash
# Script para validar y regenerar datos de la app NMS Guide
# Útil cuando se modifican los JSON manualmente

set -e

DATA_DIR="app/src/main/assets/data"

echo "🔍 Validando datos de NMS Guide..."
echo "================================"

# 1. Validar que todos los JSON sean válidos
echo ""
echo "📄 Validando sintaxis JSON..."
VALID=true
for f in "$DATA_DIR"/*.json; do
    if jq empty "$f" 2>/dev/null; then
        echo "   ✅ $(basename $f)"
    else
        echo "   ❌ $(basename $f) — INVÁLIDO"
        VALID=false
    fi
done

if [ "$VALID" = false ]; then
    echo ""
    echo "❌ Hay errores de sintaxis. Corrígelos antes de continuar."
    exit 1
fi

# 2. Verificar integridad de categorías
echo ""
echo "📂 Verificando integridad de categorías..."
CATS=$(jq -r '.categories[].id' "$DATA_DIR/categories.json")
MISSING=false
for cat in $CATS; do
    if [ -f "$DATA_DIR/${cat}.json" ]; then
        ARTICLES=$(jq '.articles | length' "$DATA_DIR/${cat}.json")
        echo "   ✅ $cat ($ARTICLES artículos)"
    else
        echo "   ❌ Falta $cat.json"
        MISSING=true
    fi
done

if [ "$MISSING" = true ]; then
    echo ""
    echo "❌ Faltan archivos de categoría."
    exit 1
fi

# 3. Resumen
echo ""
echo "📊 Resumen:"
echo "   Categorías: $(jq '.categories | length' "$DATA_DIR/categories.json")"
TOTAL=0
for cat in $CATS; do
    COUNT=$(jq '.articles | length' "$DATA_DIR/${cat}.json")
    TOTAL=$((TOTAL + COUNT))
done
echo "   Artículos totales: $TOTAL"
echo "   Archivos JSON: $(ls "$DATA_DIR"/*.json | wc -l)"

echo ""
echo "🎉 Todo en orden! Haz commit y push para activar CI."
echo "================================"
