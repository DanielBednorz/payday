#!/bin/bash

wiremockDir="resources/wiremock"
wiremockVersion="2.35.0"
wiremockPort="5555"
wiremockFile=wiremock-standalone-${wiremockVersion}.jar

echo -e "\\nStarting wiremock...\\n"

if [ -d "$wiremockDir" ]; then
  cd $wiremockDir
else
  mkdir $wiremockDir
  cd $wiremockDir
fi

java -jar $wiremockFile --port $wiremockPort --global-response-templating --verbose
