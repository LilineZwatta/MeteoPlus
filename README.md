# 🌤️ MeteoPlus – Microservices météo avec alertes

MeteoPlus est une application distribuée composée de deux microservices Spring Boot permettant :

- la consultation de la météo actuelle via l'API OpenWeatherMap
- la gestion de villes favorites et d'alertes météo
- la communication synchrone (REST) et asynchrone (JMS/ActiveMQ)
- la sécurité via OAuth2 (Keycloak)
- la documentation API via Swagger
- des tests unitaires sur les contrôleurs

---

## 🔧 Architecture

| Service               | Description |
|-----------------------|-------------|
| `meteo-service`       | Microservice principal (API météo, villes favorites, sécurité) |
| `stat-service`        | Microservice alertes (stockage, réception via JMS) |
| `ActiveMQ` (Docker)   | Bus de messages asynchrone |

---

## ▶️ Lancement rapide

### 1. Prérequis

- Java 17+
- Maven
- Docker
- IntelliJ (ou tout IDE compatible Spring Boot)

### 2. Variables d’environnement

Créer les variables d'environnement suivantes (dans votre système ou dans IntelliJ : `Run/Debug Config > Env`)

```

API\_URL=[https://api.openweathermap.org/data/2.5/weather](https://api.openweathermap.org/data/2.5/weather)
API\_KEY=VOTRE\_CLÉ\_API\_OPENWEATHERMAP

````

### 3. Lancer ActiveMQ via Docker

```bash
docker run -d --name activemq -p 61616:61616 -p 8161:8161 rmohr/activemq
````

* Interface web : [http://localhost:8161](http://localhost:8161)
* Identifiants par défaut : admin / admin

### 4. Lancer Keycloak (authentification)

```bash
docker run -d --name keycloak -p 8085:8080 \
  -e KEYCLOAK_ADMIN=admin \
  -e KEYCLOAK_ADMIN_PASSWORD=admin \
  quay.io/keycloak/keycloak:22.0.0 start-dev
```

Puis allez sur [http://localhost:8085](http://localhost:8085) et configurez :

* un **Realm**
* un **Client** (`client_id = meteoplus`, accès public)
* un **utilisateur** avec rôle

### 5. Lancer les services

Dans deux terminaux séparés (ou depuis IntelliJ) :

```bash
cd meteo-service
mvn spring-boot:run
```

```bash
cd stat-service
mvn spring-boot:run
```

---

## 🔐 Authentification OAuth2 (Keycloak)

Une fois connecté à Keycloak :

1. Obtenez un token avec votre utilisateur :

   * Via Postman ou en accédant à :

     ```
     POST http://localhost:8085/realms/<realm>/protocol/openid-connect/token
     ```
2. Ajoutez le token Bearer dans Postman pour accéder aux endpoints sécurisés.

---

## 🔁 Communication

* **Synchrone** : `meteo-service` appelle `stat-service` pour lire les alertes via REST.
* **Asynchrone** : `meteo-service` envoie des alertes (via JMS) à `stat-service`, qui les stocke.

---

## 🧪 Tests unitaires

Des tests ont été ajoutés dans :

```
/src/test/java/ch.hearc.jee2024.meteoservice.controller/
```

Ils utilisent `MockMvc` pour valider les routes publiques des contrôleurs.

---

## 📘 Documentation Swagger

Accessible à l'adresse :

* [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) (meteo-service)
* [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html) (stat-service)

---

## 🗃️ Base de données

Les services utilisent une base H2 en mémoire (avec console disponible) :

* meteo-service : [http://localhost:8080/h2](http://localhost:8080/h2)
* stat-service : [http://localhost:8081/h2](http://localhost:8081/h2)

Changer `application.properties` pour rendre la base **persistante** si nécessaire :

```properties
spring.datasource.url=jdbc:h2:file:./data/alertsdb
```

---

## ✅ Objectifs réalisés

* [x] Deux microservices REST indépendants
* [x] Authentification sécurisée via OAuth2 / JWT (Keycloak)
* [x] Intégration OpenWeatherMap API
* [x] Communication REST & JMS
* [x] Sauvegarde en base relationnelle (H2 ou persistente)
* [x] Documentation Swagger
* [x] Tests unitaires des endpoints

---

## 📌 Auteurs

Projet réalisé dans le cadre du cours JEE-Spring à l'HE-Arc.

- Julien Annen
- Simon Berthoud
- Marilyn Teuscher
