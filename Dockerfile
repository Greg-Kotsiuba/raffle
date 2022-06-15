FROM eclipse-temurin:17.0.3_7-jdk

ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT java \
    -cp app:app/lib/* \
    -Djava.security.egd=file:/dev/./urandom \
    -XX:+UseG1GC \
    -XX:+UseStringDeduplication \
    -XX:MinRAMPercentage=50 \
    -XX:MaxRAMPercentage=80 \
    $JAVA_OPTS \
    com.gk.springbootskeleton.SkeletonApplication