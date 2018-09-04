# Development

##### Edit Hosts

```
127.0.0.1 db.domain.com
127.0.0.1 api.domain.com
```

##### Execute:

```sh
bash scripts/development.sh start
```

# Deploy

##### Execute:

```sh
bash scripts/production.sh build
bash scripts/production.sh start
```

# Endpoints

Method | Path             | Description
-------|------------------|-------------------
POST   | /auth            | SignIn or SignUp
GET    | /auth/logout     | Logout All Devices
GET    | /server/ping     | Ping
GET    | /docs/postman/v1 | Postman Collection

# Configure Facebook API

Needs Permissions:
- email
- user_birthday
- user_gender

Get Access Token:

```https://developers.facebook.com/tools/explorer```

# Configure Google API

Needs Permissions:

- https://www.googleapis.com/auth/userinfo.email
- https://www.googleapis.com/auth/userinfo.profile 

Get Access Token:

```https://developers.google.com/oauthplayground```

# TODO

- Security Docs
- Swagger Generator
- FCM