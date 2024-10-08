#!/bin/bash

grep -rl "@ConfigurationProperties" . | grep ".java$" | while read -r file; do
    if grep -q "@Validated" "$file"; then
        echo "Found in: $file"
        gsed -i 's/@Validated/@Validated \/\/ TODO: check Bean Validation of Configuration Properties/g' "${file}"
    fi
done