# Serviço de Sessões de Votos

## Descrição

#### A ideia deste projeto é desenvolver uma API REST de votações em pautas.

#### Documentação da API: https://voting-session-app-91509019c1e2.herokuapp.com/custom-swagger-ui

## Execução do projeto em ambiente local
#### Para garantir que a aplicação execute perfeitamente em ambiente local, é necessário seguir os passos a baixo.
- **MongoDB e RabbitMQ**
  - Alterar o arquivo application.yml, pois esta configurado para usar variaveis de ambiente vindas do ambiente de deploy.


             ```
           spring:
            application:
              name: voting-session-service
            data:
              mongodb:
                uri: mongodb+srv://root:T5SQVhMXftV62I2h@personal-cluster.up7z6.mongodb.net/voting_service_db?retryWrites=true&w=majority&appName=personal-cluster
          #      uri: ${MONGODB_URI}
          #  rabbitmq:
          #    uri:
          #    host: ${RABBITMQ_HOST}
          #    port: ${RABBITMQ_PORT}
          #    username: ${RABBITMQ_USERNAME}
          #    password: ${RABBITMQ_PASSWORD}
          #    virtual-host: ${RABBITMQ_VHOST}
          #    ssl:
          #      enabled: true
            rabbitmq:
              host: localhost
              port: 5672
              username: guest
              password: guest
          springdoc:
            api-docs:
              enabled: true
            swagger-ui:
              path: /custom-swagger-ui

             ```
- **Docker**
  - É necessário possuir o Docker e docker-compose instalados. 
  - Executar o comando para subir o container do RabbitMQ configurado no arquivo docker-compose.yml:
    ```
      sudo docker-compose up -d
    ```

- **Acessando painel do RabbitMQ** 
  - O painel estará disponível na porta 15672
  ```
      http://localhost:15672/
    ```
  - Inserir credenciais
  ```
      Username: guest
      Passowrd: guest
    ```

#### Após realizar os passos acima, execute a aplicação.
    
## Funcionalidades do Projeto

- Cadastro de pautas
- Abertura de sessão de votação em pautas
- Votação em pautas
- Resultado de votos em pautas
- Envio de mensagem para fila configurada 

## Tecnologias utilizadas

- Java
- Spring Framework
- Spring Data MongoDB
- RabbitMQ
- Docker
- Spring Docs OpenAPI
- Junit 5
