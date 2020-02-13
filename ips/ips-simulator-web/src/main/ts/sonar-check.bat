@echo off
call sonar-scanner -Dsonar.projectKey=ips-ui -Dsonar.sources=./src/iochord -Dsonar.host.url=http://apps-sonarqube-sonarqube:9000 -Dsonar.login=e8008f23a68aa7c49851cd5947a043288453f5af