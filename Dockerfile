FROM openjdk:17-jdk-slim

# 워킹디렉토리 설정
WORKDIR /app

# 빌드한 .jar 파일을 컨테이너로 복사
COPY build/libs/toopa-0.0.1-SNAPSHOT.jar toopa.jar

# 환경변수
# ENV SPRING_PROFILES_ACTIVE=prod

# 애플리케이션 실행
# 배포환경, 운영환경 : JVM 시스템 프로퍼티로 환경변수를 지정 (-D : Define)
ENTRYPOINT ["java", "-jar", "/app/toopa.jar"]
# ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app/toopa.jar"]
# 개발환경 : 커맨드라인 인자로 환경변수를 지정 : --spring.profiles.active=prod (최고우선순위)
# ENTRYPOINT ["java", "-jar", "/app/toopa.jar","--spring.profiles.active=prod"]

# 포트 8080 개방
EXPOSE 8080
