#!/bin/sh
# ----------------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
# ----------------------------------------------------------------------------

MAVEN_PROJECT_BASE_DIR="$( cd "$( dirname "$0" )" >/dev/null && pwd )"

MAVEN_WRAPPER_JAR="${MAVEN_PROJECT_BASE_DIR}/.mvn/wrapper/maven-wrapper.jar"

if [ ! -f "${MAVEN_WRAPPER_JAR}" ]; then
  echo "Could not find ${MAVEN_WRAPPER_JAR}"
  echo "Please run 'mvn -N wrapper:wrapper' to generate the required files."
  exit 1
fi

MAVEN_CMD_LINE_ARGS=("$@")
if echo "${MAVEN_CMD_LINE_ARGS[@]}" | grep -q -e "-Dmaven.multiModuleProjectDirectory="; then
  MAVEN_CMD_LINE_ARGS=(${MAVEN_CMD_LINE_ARGS[@]})
else
  MAVEN_CMD_LINE_ARGS=("-Dmaven.multiModuleProjectDirectory=${MAVEN_PROJECT_BASE_DIR}" "${MAVEN_CMD_LINE_ARGS[@]}")
fi

echo ""
echo "Java home: ${JAVA_HOME}"
echo "Java version: ${JAVA_VERSION}"
echo "Maven home: ${M2_HOME}"
echo "Maven version: ${MAVEN_VERSION}"

"${JAVA_HOME}/bin/java" ${MAVEN_OPTS} -jar "${MAVEN_WRAPPER_JAR}" "${MAVEN_CMD_LINE_ARGS[@]}" 