package main

deny[msg] {
  not input.key == "value"

  msg := "keyにvalueが設定されていません"
}
