import re

masking_patterns = {
    'bankAccountNumber': r'[0-9]{7}'
}


def extract_and_mask(text: str,
                     extract_values: dict
                     ) -> (str, dict):
    _extract_values = extract_values.copy()
    masked_text = text
    for key, pattern in masking_patterns.items():
        matches = re.finditer(pattern, text)
        for match in matches:
            match_value = match.group()
            # 辞書に既に値が存在するかチェック
            for stored_key, stored_value in _extract_values.items():
                if stored_value == match_value and stored_key.startswith(key):
                    replace_key = stored_key
                    break
            else:
                # 一意のキーを作成し、抽出とマスキングを実行
                replace_key = f"{key}_{len([k for k in _extract_values if k.startswith(key)]) + 1}"
                _extract_values[replace_key] = match_value

            masked_text = masked_text.replace(match_value, "{" + replace_key + "}")

    return masked_text, _extract_values


def embed(text: str, extract_values: dict) -> str:
    if extract_values is None:
        return text

    # extract_values の各キーと置換対象を反映
    for key, value in extract_values.items():
        placeholder = "{" + key + "}"
        text = text.replace(placeholder, value)
    return text
