FROM tomcat:9.0-jdk11

# Copy the built WAR into Tomcat's webapps as ROOT.war so the app is served at /
COPY target/auction.war /usr/local/tomcat/webapps/ROOT.war

# Copy entrypoint that waits for the DB to be ready before starting Tomcat
COPY tomcat-entrypoint.sh /usr/local/tomcat/bin/tomcat-entrypoint.sh
RUN chmod +x /usr/local/tomcat/bin/tomcat-entrypoint.sh

# Ensure uploads directory exists and is writable
RUN mkdir -p /usr/local/tomcat/webapps/uploads && \
    chown -R root:root /usr/local/tomcat/webapps/uploads

EXPOSE 8080

CMD ["/usr/local/tomcat/bin/tomcat-entrypoint.sh"]
