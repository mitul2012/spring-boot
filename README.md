# Spring-boot-mongoDB CRUD operation
Spring boot CRUD operation on mongoDB with Rest APis microservices, It also publishing newly added user to kafka topic with publisher and consumer in place,How to serialize/deserilize custom objects in kafka, whole project runs as docker cotainer with all required deendent tools like mongo, kafka everything.

#Required tools to run project
1. java 8 or later version
2. Docker
3. Docker compose


#Run project
1. start docker
2. go to project root directory in terminal and run command "docker compose up"  That's all!

Above command create 4 docker containers..
1. Demo(spring boot service)
2. mongoDB
3. kafka
4. zookeeper
5. init-kafka(to create topic(myTopic) in kafka.


#Following are the endpoints
1. http://localhost:8090/auth  -- Generate JWT token
2. http://localhost:8090/save  -- Save User document in mongoDB
3. http://localhost:8090/getall/0  --return first page with 5 user document.
4. http://localhost:8090/get/1  --Return user for given id
5. http://localhost:8090/delete/2  - delete user document in mongoDB

#User RequestBody for save user is...
{
    "id":"1",
    "firstName":"Mitul",
    "lastName":"sanghani",
    "userName":"test",
    "password":"test123",
    "roles":[
        {
            "userId":"1",
            "role":"admin"
        },{
            "userId":"1",
            "role":"user"
        }
    ]
}

Note : except /auth and /save endpoint all other endpoint need to pass JWT token in Authorization header with Bearer prefix.
        JWT token can be generated using /auth endpoint.


