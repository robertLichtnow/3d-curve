# Builds the project
mvn package -Dmaven.test.skip=true

# Runs the project
java -cp target/curves-1.0-SNAPSHOT.jar edu.udc.cg.App
