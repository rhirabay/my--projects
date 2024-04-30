#!/bin/bash

# jar生成
./gradlew bootJar

pushd build/libs

java -Djarmode=tools -jar app.jar extract

java -XX:ArchiveClassesAtExit=application.jsa -Dspring.context.exit=onRefresh -jar app/app.jar

popd
