# Run the Auction webapp locally with Docker Compose

This repository contains a simple Docker Compose setup to run a MySQL database and a Tomcat container hosting the built WAR.

Prerequisites
- Docker and Docker Compose installed on your machine.
- Java and Maven to build the WAR (or use the included artifact if present).

Quick start
1. Build the WAR locally (from project root):

```powershell
mvn package -DskipTests=true
```

2. Start the stack (builds the Tomcat image that copies the WAR):

```powershell
docker compose up --build -d
```

3. Open the app in your browser at: http://localhost:8080/

Database
- MySQL is exposed on port 3306; initial DB and sample data are provided by `db/init.sql`.
- Credentials (used in `src/main/webapp/META-INF/context.xml`):
  - DB: auction_db
  - User: auction_user
  - Password: auction_pass

Notes
- The WAR is copied into Tomcat as ROOT.war so the app is served at `/`.
- Uploaded images are mounted from `./uploads` into the Tomcat webapps uploads folder. Consider storing uploads outside the webapp in production.
- If your Docker installation uses the legacy `docker-compose` command, run `docker-compose up --build -d` instead.

Security
- `db/init.sql` and `src/main/webapp/META-INF/context.xml` include credentials for convenience only. Don't commit real credentials; use secrets or environment variables in production.
