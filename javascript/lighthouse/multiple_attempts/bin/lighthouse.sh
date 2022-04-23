#!/bin/bash

BIN_PATH=$(dirname $0)

REPORT_DIR=${BIN_PATH}/../report/
SUMMARY_REPORT=${REPORT_DIR}/summary_$(date +%s).log

if [[ "x${TEST_URL}" == "x" ]]; then
  echo -n 'TEST_URL : '
  read TEST_URL
fi

function measure() {
    # cf. https://github.com/GoogleChrome/lighthouse#cli-options
    lighthouse \
      ${TEST_URL} \
      --preset="desktop" --quiet --output=html --output=json \
      --extra-headers=${BIN_PATH}/header.json \
      --chrome-flags="--headless --no-sandbox --ignore-certificate-errors"
}

echo '計測開始'
echo "TEST_URL -> ${TEST_URL}"

#初回実行結果は読み捨て（初回実行時にサーバサイドのロードが走ることがあるため）
measure
rm *.html *.json

for i in $(seq 1 3)
do
  echo "${i}回目"
  measure
  # 結果からLCPを抽出
  LCP=$(cat *.json | jq '.audits."largest-contentful-paint".numericValue' | cut -d '.' -f 1)
  echo "${i}回目: ${LCP} ms" >> ${SUMMARY_REPORT}

  mv *.html ${REPORT_DIR}
  mv *.json ${REPORT_DIR}
done

# 平均値を算出
LCP_AVG=$(cat ${SUMMARY_REPORT} | cut -d ' ' -f 2 | awk '{ x++; sum+=$1 } END { print sum/x }')
echo "平均: ${LCP_AVG} ms" >> ${SUMMARY_REPORT}

echo '計測終了'
echo "レポート：${SUMMARY_REPORT}"