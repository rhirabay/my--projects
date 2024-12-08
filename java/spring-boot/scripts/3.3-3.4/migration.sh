#!/bin/bash

# スクリプトのディレクトリを取得
SCRIPT_DIR=$(dirname "$0")
COMPONENTS_DIR="${SCRIPT_DIR}/../components"

# OpenRewriteの関数読み込み
source "${COMPONENTS_DIR}/openrewrite.sh"

# OpenRewriteの環境変数設定
REWRITE_YML_FILE="${SCRIPT_DIR}/rewrite.yml"
OPENREWRITE_PLUGIN_VERSION="6.28.1"
OPENREWRITE_REWRITE_SPRING_LIB_VERSION="5.24.1"
# OpenRewriteの実行
executeOpenRewrite


grep -rl "@ConfigurationProperties" . | grep ".java$" | while read -r file; do
    if grep -q "@Validated" "$file"; then
        echo "Found in: $file"
        gsed -i 's/@Validated/@Validated \/\/ TODO: check Bean Validation of Configuration Properties/g' "${file}"
    fi
done
