## Debezium

---

### 1. Usando Postgres

#### Rodando a infraestrutura necessária (ou via sua IDE preferida)
```shell
docker-compose -f src/docker/compose-debezium-postgres.yaml up --build
```

#### Rode a aplicação (ou via sua IDE preferida)
```shell
mvn spring-boot:run 
```

#### Inicie o Connector do Debezium para Postgres
```shell
curl -X POST --location "http://localhost:8083/connectors/" \
    -H "Accept: application/json" \
    -H "Content-Type: application/json" \
    -d @src/connector/register-postgres.json
```

#### Consumindo mensagens do topico do Debezium
```shell
docker-compose -f src/docker/compose-debezium-postgres.yaml exec kafka /kafka/bin/kafka-console-consumer.sh \
    --bootstrap-server kafka:9092 \
    --from-beginning \
    --property print.key=true \
    --topic dbserver1.demo.product
```

#### Destruir a infra criada (ou via sua IDE preferida)
```shell
docker-compose -f src/docker/compose-debezium-postgres.yaml down --remove-orphans
```

---

### 2. Usando MongoDB (em construção)

```shell
# Start the topology as defined in https://debezium.io/docs/tutorial/
export DEBEZIUM_VERSION=1.6
docker-compose -f docker-compose-mongodb.yaml up

# Initialize MongoDB replica set and insert some test data
docker-compose -f docker-compose-mongodb.yaml exec mongodb bash -c '/usr/local/bin/init-inventory.sh'

# Start MongoDB connector
curl -X POST --location "http://localhost:8083/connectors/" \
    -H "Accept:application/json" \ 
    -H  "Content-Type:application/json" \
    -d @register-mongodb.json

# Consume messages from a Debezium topic
docker-compose -f docker-compose-mongodb.yaml exec kafka /kafka/bin/kafka-console-consumer.sh \
    --bootstrap-server kafka:9092 \
    --from-beginning \
    --property print.key=true \
    --topic dbserver1.inventory.customers

# Modify records in the database via MongoDB client
docker-compose -f docker-compose-mongodb.yaml exec mongodb bash \
    -c 'mongo -u $MONGODB_USER -p $MONGODB_PASSWORD --authenticationDatabase admin inventory'

db.customers.insert([
    { _id : NumberLong("1005"), first_name : 'Bob', last_name : 'Hopper', email : 'thebob@example.com', unique_id : UUID() }
]);

# Shut down the cluster
docker-compose -f docker-compose-mongodb.yaml down
```