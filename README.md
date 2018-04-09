# PushBullet Wrapper Coding Challenge

## Configuration

By default the service will listen on port 8080, to change this edit the file src/main/resources/application.properties and change the entry for server.port 

## Running

Run from the command line by using the Maven wrapper script in the project root directory

```sh
$ ./mvnw spring-boot:run
```

## API Details

### Registering Users

To register a user send a POST request to the /api/v1/users/ with following body (note both fields are mandatory):

```
{
	"username": "bbcUser1",
	"accessToken": "<push bullet API key>"
}
```
If successful, the created user object will be returned, status code will be 200:

```
{
	"username": "bbcUser1",
	"accessToken": "<push bullet API key>"
    	"createTime": "2018-04-08T23:40:40",
    	"numbOfNotificationsPush": 0
}
```
If the user already exists you will see, status code will be 400:

```
{
    "timestamp": "2018-04-09T20:11:48.825+0000",
    "status": 400,
    "error": "Bad Request",
    "message": "User chris already exists",
    "path": "/api/v1/users/"
}
```
### Listing Users

To list users, send a GET request to /api/v1/users. Example response is shown below, status code will be 200:

```
[
    {
		"username": "bbcUser1",
		"accessToken": "<push bullet API key>"
        	"createTime": "2018-04-09T00:04:27",
        	"numOfNotificationsPush": 0
    }
]
```

### Sending notification

To send a notification to a user, send a POST request to /api/v1/notifications/{username}. For example:

```
POST /api/v1/notifications/chris

{
	"title": "Hello",
	"body": "Test Message"
}
```

Both title and body are required fields. Below is an example success response, status code will be 200:

```
{
    "title": "Hello",
    "body": "Test Message",
    "identity": "ujC1DgwXeeGsjAsdxpxtRY"
}
```
The identity field is the PushBullet iden field for the push.


If the user does not exist, the following will be displayed, status code will be 404:

```
{
    "timestamp": "2018-04-09T20:25:01.085+0000",
    "status": 404,
    "error": "Not Found",
    "message": "User chris1 does not exist",
    "path": "/api/v1/notification/chris1"
}
```

If the push notification fails, an error will displayed indicating the reason for failure, status code will be 500:

```
{
    "timestamp": "2018-04-09T20:14:57.527+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "<Error Message>",
    "path": "/api/v1/notification/<username>"
}
```
