@rem
@rem Copyright 2007-2022 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@echo off
setlocal

set DIR_NAME=%~dp0
if "%DIR_NAME%" == "" set DIR_NAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIR_NAME%

set MAVEN_WRAPPER_JAR_PATH=%APP_HOME%.mvn\wrapper\maven-wrapper.jar
set MAVEN_WRAPPER_PROPERTIES_PATH=%APP_HOME%.mvn\wrapper\maven-wrapper.properties

if not exist "%MAVEN_WRAPPER_JAR_PATH%" (
  echo Downloading Maven Wrapper...
  for /f "tokens=1,2 delims==" %%a in ('type "%MAVEN_WRAPPER_PROPERTIES_PATH%" ^| findstr "wrapperUrl"') do (
    set WRAPPER_URL=%%b
  )
  if exist "%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" (
    powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; (New-Object System.Net.WebClient).DownloadFile('%WRAPPER_URL%'.Trim(), '%MAVEN_WRAPPER_JAR_PATH%')"
  ) else (
    echo PowerShell is not available. Please install the Maven Wrapper manually.
    exit /B 1
  )
)

set MAVEN_OPTS=
if not "%MAVEN_OPTS%" == "" goto MAVEN_OPTS_SET
if exist "%DIR_NAME%.mvn\maven.config" (
  for /f "usebackq tokens=*" %%a in (`type "%DIR_NAME%.mvn\maven.config"`) do set MAVEN_OPTS=%%a
)
:MAVEN_OPTS_SET

@rem Execute Maven
"%JAVA_HOME%\bin\java.exe" %MAVEN_OPTS% -jar "%MAVEN_WRAPPER_JAR_PATH%" %* 