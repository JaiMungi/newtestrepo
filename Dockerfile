# Use an official Tomcat image as a base
#FROM tomcat:10.1.34

# Copy the WAR file to the Tomcat webapps directory
#COPY out/SampleWebProject_war.war /usr/local/tomcat/webapps/ROOT.war

# Expose the port the app runs on
#EXPOSE 8089

# Use official Tomcat 9 image as base
FROM tomcat:10.1.34-jdk11

# Remove default ROOT webapp
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy your WAR file to Tomcat webapps directory as ROOT.war for root context
COPY target/SampleWebProject_war.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080 (Tomcat default)
EXPOSE 8080

# Start Tomcat server (default command)
CMD ["catalina.sh", "run"]