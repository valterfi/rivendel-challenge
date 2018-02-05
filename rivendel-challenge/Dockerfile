FROM drhetnet/ubuntu1604java8:v1

ENV ELASTICSEARCH_VERSION 2.4.0
ENV LOGSTASH_VERSION 2.4.0

ENV CONFIG_ROOT /opt/config
ENV LOG_ROOT /opt/logs

ENV ELASTICSEARCH_HOME /opt/elasticsearch
ENV ELASTICSEARCH_ORIG_CONFIG_DIRECTORY ${ELASTICSEARCH_HOME}/config
ENV ELASTICSEARCH_CONFIG_FILE ${ELASTICSEARCH_ORIG_CONFIG_DIRECTORY}/elasticsearch.yml
ENV ELASTICSEARCH_LOG_DIRECTORY ${LOG_ROOT}/elasticsearch
ENV ELASTICSEARCH_CONFIG_DIRECTORY ${CONFIG_ROOT}/elasticsearch

ENV LOGSTASH_HOME /opt/logstash
ENV LOGSTASH_LOG_DIRECTORY ${LOG_ROOT}/logstash/
ENV LOGSTASH_CONFIG_DIRECTORY ${CONFIG_ROOT}/logstash

MAINTAINER Valterfi, https://github.com/valterfi

WORKDIR /opt

# Create the valterfi user and valterfi system group.
RUN groupadd -r valterfi && useradd -r -g valterfi valterfi

ENV RIVENDEL_APP_HOME /opt/rivendel-challenge

# Add src to working directory
COPY pom.xml ${RIVENDEL_APP_HOME}/pom.xml
COPY src ${RIVENDEL_APP_HOME}/src
COPY lib/h2-1.4.193.jar ${RIVENDEL_APP_HOME}/lib/h2-1.4.193.jar

ENV RIVENDEL_DATASOURCE_URL jdbc:h2:file:${RIVENDEL_APP_HOME}/src/main/resources/h2/rivendeldb;AUTO_SERVER=TRUE
ENV RIVENDEL_DATASOURCE_URL ${RIVENDEL_DATASOURCE_URL}';AUTO_SERVER=TRUE'
ENV RIVENDEL_JDBC_LIBRARY ${RIVENDEL_APP_HOME}/lib/h2-1.4.193.jar
ENV RIVENDEL_JDBC_CLASS org.h2.Driver

# Install gosu as to allow us to run programs with a specific user.
RUN apt-get update && apt-get install -y curl libossp-uuid-dev wget ca-certificates git make maven && rm -rf /var/lib/apt/lists/* && \
    curl -o /usr/local/bin/gosu -SL 'https://github.com/tianon/gosu/releases/download/1.1/gosu' && \
    chmod +x /usr/local/bin/gosu && \
    apt-get purge -y --auto-remove curl	

# Copy the script used to launch the ELK-stack when a container is started.
COPY ./start.sh /opt/

# Download Elasticsearch, Logstash.
RUN wget https://download.elastic.co/elasticsearch/elasticsearch/elasticsearch-${ELASTICSEARCH_VERSION}.tar.gz && \
    wget https://download.elastic.co/logstash/logstash/logstash-${LOGSTASH_VERSION}.tar.gz  && \
# Create home directories.
    mkdir ${ELASTICSEARCH_HOME} && \
    mkdir ${LOGSTASH_HOME} && \
# Extract Elasticsearch and Logstash to respective directories.
    tar -xzf elasticsearch*.tar.gz  -C ${ELASTICSEARCH_HOME} --strip-components=1 && \
    tar -xzf logstash*.tar.gz  -C ${LOGSTASH_HOME} --strip-components=1 && \
# Remove archives.
    rm *.tar.gz && \
# Create log directories.
    mkdir -p ${LOGSTASH_LOG_DIRECTORY} && \
    mkdir -p ${ELASTICSEARCH_LOG_DIRECTORY} && \
# Create external configuration directories.
    mkdir -p ${ELASTICSEARCH_CONFIG_DIRECTORY} && \
    mkdir -p ${LOGSTASH_CONFIG_DIRECTORY} && \
# Set owner of log directories to the user that runs the applications.
    chown -hR valterfi:valterfi ${LOGSTASH_LOG_DIRECTORY}  && \
    chown -hR valterfi:valterfi ${ELASTICSEARCH_LOG_DIRECTORY}  && \
# Set owner of configuration directories to the user that runs the applications.
    chown -hR valterfi:valterfi ${LOGSTASH_CONFIG_DIRECTORY}  && \
    chown -hR valterfi:valterfi ${ELASTICSEARCH_CONFIG_DIRECTORY}  && \
# Set owner of Elasticsearch directory so that data directory can be created.
    chown -hR valterfi:valterfi ${ELASTICSEARCH_HOME} && \
    chown -hR valterfi:valterfi ${RIVENDEL_APP_HOME} && \
# Install Logstash JDBC plug-in.
    ${LOGSTASH_HOME}/bin/plugin install logstash-input-jdbc && \
# Make the start-script executable.
    chmod +x /opt/start.sh && \
# Build JAR
    mvn package -DskipTests=true -f ${RIVENDEL_APP_HOME}

EXPOSE 9200 5000

# Add Elasticsearch, Logstash bin directories to path.
ENV PATH ${ELASTICSEARCH_HOME}/bin:$PATH
ENV PATH ${LOGSTASH_HOME}/bin:$PATH

# Launch the ELK stack when a container is started.
CMD ["/opt/start.sh"]