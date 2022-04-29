FROM java:8
# author
MAINTAINER P_Peaceful
VOLUME /tmp
RUN echo "Asia/Shanghai" > /etc/timezone

EXPOSE 8080
ENV JAVA_OPTS=-Xmx128m
ADD ./target/ruoyi-admin.jar ./app.jar

ENTRYPOINT java $JAVA_OPTS -jar -Duser.timezone=Asia/Shanghai /app.jar
