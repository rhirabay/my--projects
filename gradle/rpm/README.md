## RPMパッケージ作成

```shell
./gradlew buildRpm
```

## パッケージの中身を確認

```shell
rpm -lpq build/distributions/*
```