This document is in progress state will be update when there are new methods in the api of the user.

For now it works only with Fiware Kerock Api

# User #

http://server:9000/back-sdk

### Get Users ###

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
	    "name": "erikxxxxx@infotec.mx",
	    "links": {
	      "self": "http://localhost:5000/v3/users/erik-xxxxx-infotec-mx"
	    },
	    "enabled": true,
	    "id": "erik-xxxxx-infotec-mx"
	  },
	  {
	    "name": "erikxxxxx@infotec.mx",
	    "links": {
	      "self": "http://localhost:5000/v3/users/ersik-xxxxx-infotec-mx"
	    },
	    "enabled": true,
	    "id": "erik-xxxxx-infotec-mx"
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

### Search ###

> GET /users/{id}

#### HEADERS ####
	token-auth : token 
	

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

> GET /users/{name}/byName

#### HEADERS ####
	token-auth : token 
	

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

> GET /users/{username}/byUsername

#### HEADERS ####
	token-auth : token 
	

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
   

###DELETE###
> DELETE /users/{id}

#### HEADERS ####
	token-auth : token 
	

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




# Roles #

# User #

http://server:9000/back-sdk

### Get roles ###

> GET /roles/



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
    "id": "3f659e1539ad410a8ed04f8400cdfc55",
    "links": {
      "self": "http://localhost:5000/v3/roles/3f659e1539ad410a8ed04f8400cdfc55"
    },
    "name": "developer23"
  },
  {
    "id": "585027bc35544b89b623bd96ba71caa4",
    "links": {
      "self": "http://localhost:5000/v3/roles/585027bc35544b89b623bd96ba71caa4"
    },
    "name": "basic"
  },
  {
    "id": "8308e12d35444b568c59cceb721d583f",
    "links": {
      "self": "http://localhost:5000/v3/roles/8308e12d35444b568c59cceb721d583f"
    },
    "name": "community"
  },
  {
    "id": "8373de77b80d4fdf8bb17246af7e940a",
    "links": {
      "self": "http://localhost:5000/v3/roles/8373de77b80d4fdf8bb17246af7e940a"
    },
    "name": "admin"
  },
  {
    "id": "c192a21d2c464434a9e6a6ae7c11c16b",
    "links": {
      "self": "http://localhost:5000/v3/roles/c192a21d2c464434a9e6a6ae7c11c16b"
    },
    "name": "member"
  },
  {
    "id": "e9c953bb036047a99bfa4f432012fddc",
    "links": {
      "self": "http://localhost:5000/v3/roles/e9c953bb036047a99bfa4f432012fddc"
    },
    "name": "trial"
  },
  {
    "id": "f426f6636e044dce95c94974bbe0a25c",
    "links": {
      "self": "http://localhost:5000/v3/roles/f426f6636e044dce95c94974bbe0a25c"
    },
    "name": "owner"
  }
]

### Create ###

> POST /roles/

#### HEADERS ####
	token-auth : token 
	Content-Type: application/json
#### JSON ####

	{
	    "role": {
	        "name": "developer23"
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
	  "role": {
	    "id": "0ef3f115d8694f778620425bdf2e51bc",
	    "links": {
	      "self": "http://localhost:5000/v3/roles/0ef3f115d8694f778620425bdf2e51bc"
	    },
	    "name": "developer2"
	  }
	}

### Search ###

> GET /roles/{id}

#### HEADERS ####
	token-auth : token 
	

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
	  "role": {
	    "id": "0ef3f115d8694f778620425bdf2e51bc",
	    "links": {
	      "self": "http://localhost:5000/v3/roles/0ef3f115d8694f778620425bdf2e51bc"
	    },
	    "name": "developer2"
	  }
	}

> GET /roles/{name}/byName

#### HEADERS ####
	token-auth : token 
	

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
	  "role": {
	    "id": "0ef3f115d8694f778620425bdf2e51bc",
	    "links": {
	      "self": "http://localhost:5000/v3/roles/0ef3f115d8694f778620425bdf2e51bc"
	    },
	    "name": "developer2"
	  }
	}


### UPDATE ###

> PATCH /roles/{userid}

#### HEADERS ####
	token-auth : token 
	Content-Type: application/json
#### JSON ####

	{
	    "role": {
	        "name": "Developer"
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
	  "role": {
	    "id": "0b1d83b4d81f4321bf233a4c94a6e7f3",
	    "links": {
	      "self": "http://localhost:5000/v3/roles/0b1d83b4d81f4321bf233a4c94a6e7f3"
	    },
	    "name": "Developer"
	  }
	}
   
###DELETE###
> DELETE /roles/{id}

#### HEADERS ####
	token-auth : token 
	

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

