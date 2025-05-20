# Use official Tomcat base image with JDK 11 (update version if needed)
FROM tomcat:9-jdk11-openjdk

# Remove default ROOT webapps (optional) to keep only your app
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the WAR file into the Tomcat webapps directory as ROOT.war
COPY app.war /usr/local/tomcat/webapps/ROOT.war

# Expose Tomcat default port
EXPOSE 8080

# Run Tomcat server
CMD ["catalina.sh", "run"]