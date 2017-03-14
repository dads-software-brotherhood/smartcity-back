This document is in progress state will be update when there are new methods in the api of the user.

For now it works only with Fiware Kerock Api

# User #

http://server:9000/back-sdk/user

### Obtener usuarios ###

> GET /users/



#### HEADERS ####
	token-auth : token 

#### ERROR ####
	400 - Bad Request

Some content in the request was invalid.

	401 - Unauthorized 	

User must authenticate before making a request.

	403 - Forbidden 	

Policy does not allow current user to do this operation.

#### OK ####

	[
	  {
	    "name": "erik.valdivieso@infotec.mx",
	    "links": {
	      "self": "http://localhost:5000/v3/users/erik-valdivieso-infotec-mx"
	    },
	    "enabled": true,
	    "id": "erik-valdivieso-infotec-mx"
	  },
	  {
	    "name": "ersik.valdivieso@infotec.mx",
	    "links": {
	      "self": "http://localhost:5000/v3/users/ersik-valdivieso-infotec-mx"
	    },
	    "enabled": true,
	    "id": "ersik-valdivieso-infotec-mx"
	  },
	  {
	    "username": "idm",
	    "name": "idm",
	    "links": {
	      "self": "http://localhost:5000/v3/users/idm_user"
	    },
	    "enabled": true,
	    "id": "idm_user"
	  }
	]

### Create ###

> POST /users/

#### HEADERS ####
	token-auth : token 
	Content-Type: application/json
#### JSON ####

	{
	 "user" :   {
	        "enabled": true,
	        "name": "James Doedfsddsfssf3",
	        "username":"James Doe nombredfasdf",
	        "password": "secretsecret"
	    }
	}

#### ERROR ####

	
	400 - Bad Request
 Some content in the request was invalid.

	401 - Unauthorized
User must authenticate before making a request.

	403 - Forbidden
Policy does not allow current user to do this operation.

	409 - Conflict
This operation conflicted with another operation on this resource.


#### OK ####
	{
	  "user": {
	    "enabled": true,
	    "name": "James Doedfsddsfssf3",
	    "links": {
	      "self": "http://localhost:5000/v3/users/james-doe-nombredfasdf-3"
	    },
	    "id": "james-doe-nombredfasdf-3",
	    "username": "James Doe nombredfasdf"
  	  }
	}

### change password having original password###
> POST /users/{user_id}/password

#### HEADERS ####

	token-auth : token 
	Content-Type: application/json

#### JSON ####
	{
	    "user": {
	        "password": "new_secretsecret",
	        "original_password": "secretsecret"
	    }
	}

#### ERROR ####
	400 - Bad Request 	
Some content in the request was invalid.

	401 - Unauthorized 	
User must authenticate before making a request or the original_password is incorrect.

	403 - Forbidden 	
Policy does not allow current user to do this operation.

	404 - Not Found 	
The requested resource could not be found.

	409 - Conflict 	
This operation conflicted with another operation on this resource. 

### UPDATE ###

> PATCH /users/{userid}

#### HEADERS ####
	token-auth : token 
	Content-Type: application/json
#### JSON ####

	{
	 "user" :   {
	        "enabled": true,
	        "name": "James Doe",
	        "username":"James Doe nombre",
	        "password": "secretsecret"
	    }
	}

#### ERROR ####

	
	400 - Bad Request
 Some content in the request was invalid.

	401 - Unauthorized
User must authenticate before making a request.

	403 - Forbidden
Policy does not allow current user to do this operation.

	409 - Conflict
This operation conflicted with another operation on this resource.


#### OK ####
	{
	  "user": {
	    "enabled": true,
	    "name": "James Doe",
	    "links": {
	      "self": "http://localhost:5000/v3/users/james-doe-nombre"
	    },
	    "id": "james-doe",
	    "username": "James Doe nombre"
  	  }
	}
   