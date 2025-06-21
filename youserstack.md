
### AMD 아키텍처로 이미지 생성
koyeb infra는 AMD만 지원

---

### 캐시지우고 다시 빌드(supabase postgres db > pooler connection)
./gradlew clean build

### 빌드시 데이터베이스 연결문제(supabase postgres db > direct connection > ipv4 연결 free plan에서는 미지원)로 에러발생하기 때문에 테스트 미진행하여 바로 빌드
./gradlew clean build -x test

---

### 도커빌드시작
docker build --platform linux/amd64 -t youserstack/toopa:1.0 .

---

### 깃헙 리파지터리 업로드
./gradlew update

