#!/bin/bash

pushd build/libs

java -XX:SharedArchiveFile=application.jsa -Xlog:class+load:file=cds.log -jar app/app.jar

popd
