all:
	mvn clean;
	mvn install;

run:
	java -jar target/Game.jar --enemiesCount=10 --wallsCount=10 --size=10 --profile=production
# java -jar target/Game.jar --enemiesCount=10 --wallsCount=10 --size=30 --profile=production

dev:
	java -jar target/Game.jar --enemiesCount=4 --wallsCount=20 --size=10 --profile=development
