ENV_FILE=.env
FOLDER=docker-compose

KEYCLOAK_FILE=$(FOLDER)/docker-compose.keycloak.yml
MONGODB_FILE=$(FOLDER)/docker-compose.mongodb.yml

.PHONY: up down restart

up:
	docker-compose --env-file $(ENV_FILE) -f $(KEYCLOAK_FILE) \
		-f $(MONGODB_FILE) up -d

down:
	docker-compose --env-file $(ENV_FILE) -f $(MONGODB_FILE) \
		 -f $(KEYCLOAK_FILE) down

restart: down up
