import unittest

from src.openai.assistants.masking_filter import extract_and_mask, embed


# テストケースの定義
class ExtractAndMaskValuesTest(unittest.TestCase):

    def test_1(self):
        masked_text, extract_values = extract_and_mask("口座番号は1234567です。", extract_values={})
        self.assertEqual(masked_text, "口座番号は{bankAccountNumber_1}です。");
        self.assertEqual(extract_values, {
            "bankAccountNumber_1": "1234567"
        })

    def test_2(self):
        role = {
            "bankAccountNumber": r'[0-9]{7}'
        }
        masked_text, extract_values = extract_and_mask("口座番号は1234567です。", extract_values={
            "bankAccountNumber_1": "7654321"
        })
        self.assertEqual(masked_text, "口座番号は{bankAccountNumber_2}です。");
        self.assertEqual(extract_values, {
            "bankAccountNumber_1": "7654321",
            "bankAccountNumber_2": "1234567"
        })

    def test_3(self):
        role = {
            "bankAccountNumber": r'[0-9]{7}'
        }
        masked_text, extract_values = extract_and_mask("口座番号は1234567です。", extract_values={
            "bankAccountNumber_1": "1234567"
        })
        self.assertEqual(masked_text, "口座番号は{bankAccountNumber_1}です。");
        self.assertEqual(extract_values, {
            "bankAccountNumber_1": "1234567"
        })


class EmbedValuesTest(unittest.TestCase):

    def test(self):
        masked_text = "口座番号は{bankAccountNumber_1}です。"
        extract_values = {
            "bankAccountNumber_1": "1234567"
        }
        text = embed(masked_text, extract_values)
        self.assertEqual(text, "口座番号は1234567です。")


# テストの実行
if __name__ == '__main__':
    unittest.main()
