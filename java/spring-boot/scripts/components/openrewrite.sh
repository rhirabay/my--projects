#!/bin/bash

###########################################
###   OpenRewrite
###########################################
function checkEnvironment() {
    if [ -z "${REWRITE_YML_FILE}" ]; then
      echo "REWRITE_YML_FILE is not set."
      exit 1
    fi
    if [ -z "${OPENREWRITE_PLUGIN_VERSION}" ]; then
      echo "OPENREWRITE_PLUGIN_VERSION is not set."
      exit 1
    fi
    if [ -z "${OPENREWRITE_REWRITE_SPRING_LIB_VERSION}" ]; then
      echo "OPENREWRITE_REWRITE_SPRING_LIB_VERSION is not set."
      exit 1
    fi
    if [ -z "${sed_cmd}" ]; then
      sed_cmd=sed
      if [ $(uname) = 'Darwin' ]; then
        which gsed
        if [ $? -ne 0 ]; then echo 'Please install gsed.'; exit 1; fi
        sed_cmd=gsed
      fi
    fi
}

function cleanOpenRewrite() {
  build_gradle_files=($(find . -name build.gradle))
  for build_gradle_file in ${build_gradle_files[@]}; do
    # rewrite.yml削除
    rm $(dirname ${build_gradle_file})/rewrite.yml
    # 依存ライブラリ削除
    dependencies_block_start=$(${sed_cmd} -n '/^dependencies {/=' ${build_gradle_file})
    dependencies_block_end=$(${sed_cmd} -n '/^}/=' ${build_gradle_file} | awk '{ if ($0 >= '${dependencies_block_start}') print $0 }' | head -1)
    ${sed_cmd} -i "${dependencies_block_start},${dependencies_block_end}{/[ ]*rewrite/d;}" ${build_gradle_file}
    # プラグイン削除
    plugin_block_start=$(${sed_cmd} -n '/^plugins {/=' ${build_gradle_file})
    plugin_block_end=$(${sed_cmd} -n '/^}/=' ${build_gradle_file} | awk '{ if ($0 >= '$plugin_block_start') print $0 }' | head -1)
    ${sed_cmd} -i "${plugin_block_start},${plugin_block_end}{/.*rewrite/d;}" ${build_gradle_file}
    # rewriteブロック削除
    grep "^rewrite" ${build_gradle_file}
    if [ $? -eq 0 ]; then
      rewrite_block_start=$(${sed_cmd} -n '/^rewrite {/=' ${build_gradle_file})
      rewrite_block_end=$(${sed_cmd} -n '/^}/=' ${build_gradle_file} | awk '{ if ($0 >= '$rewrite_block_start') print $0 }' | head -1)
      ${sed_cmd} -i "${rewrite_block_start},${rewrite_block_end}d" ${build_gradle_file}
    fi
  done
}

function setupOpenRewrite() {
    build_gradle_files=($(find . -name build.gradle))
    for build_gradle_file in ${build_gradle_files[@]}; do
      # pluginブロックがない場合はスキップ
      grep "^plugins" ${build_gradle_file}
      if [ $? -ne 0 ]; then
        continue
      fi
      # dependenciesブロックがない場合はスキップ
      grep "^dependencies" ${build_gradle_file}
      if [ $? -ne 0 ]; then
        continue
      fi

      # ファイル追加
      cp ${REWRITE_YML_FILE} $(dirname ${build_gradle_file})/rewrite.yml
      # プラグイン追加
      grep 'org.openrewrite.rewrite' ${build_gradle_file}
      if [ $? -ne 0 ]; then
        plugin_block_start=$(${sed_cmd} -n '/^plugins {/=' ${build_gradle_file})
        plugin_block_end=$(${sed_cmd} -n '/}/=' ${build_gradle_file} | awk '{ if ($0 >= '$plugin_block_start') print $0 }' | head -1)
        ${sed_cmd} -i -e "${plugin_block_end}i \ \ \ \ id('org.openrewrite.rewrite') version('${OPENREWRITE_PLUGIN_VERSION}')" ${build_gradle_file}
      fi
      # 依存を追加
      dependencies_block_start=$(${sed_cmd} -n '/^dependencies {/=' ${build_gradle_file})
      dependencies_block_end=$(${sed_cmd} -n '/^}/=' ${build_gradle_file} | awk '{ if ($0 >= '${dependencies_block_start}') print $0 }' | head -1)
      grep 'rewrite-spring' ${build_gradle_file}
      if [ $? -ne 0 ]; then
        ${sed_cmd} -i -e "${dependencies_block_end}i \ \ \ \ rewrite('org.openrewrite.recipe:rewrite-spring:${OPENREWRITE_REWRITE_SPRING_LIB_VERSION}')" ${build_gradle_file}
        dependencies_block_end=$((${dependencies_block_end} + 1))
      fi

      # rewriteブロックを追加
      echo '' >> ${build_gradle_file}
      echo 'rewrite {' >> ${build_gradle_file}
      echo "    activeRecipe('springboot.migration')" >> ${build_gradle_file}
      echo '}' >> ${build_gradle_file}
    done
}

function executeOpenRewrite() {
    cleanOpenRewrite
    setupOpenRewrite
    ./gradlew -q rewriteRun
    if [ $? -ne 0 ]; then
      echo "error on rewriteRun"
      exit 1
    fi
    cleanOpenRewrite
}