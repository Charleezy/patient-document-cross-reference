# Patient Cross Registration Service

Run the application by using maven and installing. Access the endpoints using port 8080

- post for adding documents
localhost:8080/addDocument
issuer "government of canada"
id "Qazzxc123"

gives back the id of the created document

- getting documents
localhost:8080/getDocuments
existingDocumentID "1"

- linking documents
localhost:8080/linkDocument
existingDocumentID "1"
newDocumentID "2"

#To run with maven
Set up a MySQL database with the parameters specified in application.properties

./mvnw spring-boot:run

Or build the jar
./mvnw clean package