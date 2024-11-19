### Start app

./gradlew bootRun

### Test app

curl --location 'localhost:8080/' --header 'Content-Type: application/json' --data '{
"topics": {
"reading": 20,
"math": 50,
"science": 30,
"history": 15,
"art": 10
}
}'
