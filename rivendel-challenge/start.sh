#!/bin/bash

# Exit immediately if a command exits with a non-zero status.
set -e

gosu valterfi elasticsearch -d --cluster.name valterfi-cluster 
nohup logstash --allow-env -f ${RIVENDEL_APP_HOME}/src/main/resources/logstash/conf &
exec gosu valterfi java -jar /opt/rivendel-challenge/target/rivendel-challenge-1.1.0.jar
