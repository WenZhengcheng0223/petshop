FROM java:8
# author
MAINTAINER P_Peaceful
VOLUME /tmp
RUN echo "Asia/Shanghai" > /etc/timezone

EXPOSE 8080
ADD ./target/ruoyi-admin.jar ./app.jar

ENTRYPOINT java -jar -Duser.timezone=Asia/Shanghai /app.jar
