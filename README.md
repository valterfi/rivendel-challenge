# rivendel-challenge
API Rest com SpringBoot usando Java 8

### Tech
  * [Swagger] 
  * [H2 Database] 
  * [Elasticseach 2.4.0] 
  * [Logstash 2.4.0] 

### Pré requisitos

- Instalar Elasticseach 2.4.0
- Configurar Elasticsearh Cluster 

| `${ELASTICSEARCH_HOME}`/config/elasticsearch.yml |
|------------------------------------------------|
| cluster.name: valterfi-cluster                 |

- Instalar Logstash 2.4.0
- Instalar Logstash jdbc input plugin
```sh
$ ${LOGSTASH_HOME}/bin/plugin install logstash-input-jdbc
```
- Setar variável de ambiente `${RIVENDEL_APP_HOME}` com o endereço da app
- Setar variável de ambiente `${RIVENDEL_DATASOURCE_URL}` com o endereço jdbc do database
- Setar variável de ambiente `${RIVENDEL_JDBC_LIBRARY}` com  o endereço do jar do database
- Setar variável de ambiente `${RIVENDEL_JDBC_CLASS}` com a classe do driver jdbc do database

### Instalação

- Iniciar Elasticsearch
```sh
$ cd ${ELASTICSEARCH_HOME}/bin
$ ./elasticsearch
```
- Iniciar Logstash
```sh
$ cd ${LOGSTASH_HOME}/bin
$ ./logstash --allow-env -f ${RIVENDEL_APP_HOME}/src/main/resources/logstash/conf
```
- Iniciar Spring Boot
```sh
$ java -jar ${RIVENDEL_APP_HOME}/target/rivendel-challenge-<version>.jar --spring.datasource.url=${RIVENDEL_DATASOURCE_URL}
```

API disponível ao subir Spring Boot: 

```sh
http://localhost:8080/swagger-ui.html
```

### Alternativa

 - Criar uma imagem docker usando o arquivo Dockerfile versionado na aplicação

[Swagger]: <https://swagger.io/>
[H2 Database]: <http://www.h2database.com/>
[Elasticseach 2.4.0]: <https://www.elastic.co/downloads/past-releases/elasticsearch-2-4-0>
[Logstash 2.4.0]: <https://www.elastic.co/downloads/past-releases/logstash-2-4-0>