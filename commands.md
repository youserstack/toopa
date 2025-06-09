
AMD 아키텍처로 이미지 생성
koyeb infra는 AMD만 지원

캐시지우고 다시 빌드후
./gradlew clean build

도커빌드시작
docker build --platform linux/amd64 -t youserstack/toopa:1.0 .
