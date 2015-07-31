# oauth.java #

*A simple, clean, and generic implementation of the OAuth specifications*

[![Build Status](https://travis-ci.org/thomas-p-wilson/oauth.java.svg)](https://travis-ci.org/thomas-p-wilson/oauth.java)[![Coverage Status](https://coveralls.io/repos/thomas-p-wilson/oauth.java/badge.svg?branch=master&service=github)](https://coveralls.io/github/thomas-p-wilson/oauth.java?branch=master)

*oauth.java* aims to provide a generic library that implements the OAuth specification logic. It makes no assumptions about your project, the frameworks you use, or any HTTP libraries you use. Numerous OAuth libraries exist, but most of them either predate the specs, are absolutely monstrous, or both. *oauth.java* provides spec-compliant functionality in a small package, with only two dependencies (SLF4J-API for logging, Joda for datetime handling). The goal is to make it trivial to integrate *oauth.java* in any project, and simple to write wrappers for your favourite libraries.

## Goals ##

- [RFC 6749](https://tools.ietf.org/html/rfc6749) and [RFC 6750](https://tools.ietf.org/html/rfc6750) compliant
- Minimal dependencies (currently just SLF4J-API and Joda)
- Framework-agnostic. Can be implemented anywhere.

## Getting Started ##

Getting started is pretty straight forward, as is the aim of the project. That's not to say it isn't without work, but I've done my best to keep it as quick and to the point as possible.

### Implement The Services ###

Most importantly, you need a way to keep track of authorizations, your users, third-part applications, and other important information used during the authorization process. This is done through four services:

- **TokenService**: Keeps track of all access tokens granted by the authorization server.
- **CodeService**: Keeps track of all authorization codes granted by a resource owner. This is *not* necessary if you're not planning on using any code-based authentication.
- **ClientService**: Keeps track of all third-party applications which may be granted access to your application.
- **UserService**: Keeps track of all your application's users, who may authorize third-party applications to access their resources within your application.

You can check out the `com.thomaspwilson.oauth2.provider.examples` package for example service implementations.

### Implement The Endpoints ###

Generally, there are two endpoints involved in authorizing third-party applications to use your application data. The first endpoint is the `/token` endpoint. This is the endpoint from which clients receive their access tokens in order to access your application.

   Examples forthcoming :)

The second endpoint is the `/authorize` endpoint. There are actually two parts to this. 1) You need a page to allow the user to authorize or deny the request, and 2) you need an endpoint that takes that authorization and converts it into an authorization code.

   Examples forthcoming :)

## Contributing ##

If you hit a wall, find something that isn't documented, find a bug, or just find something that you think could be done better, take a second to open an issue. If it's gratitude you're after, feel free to submit a pull request fixing the issue that you (or someone else) opened an issue for.

## License ##

Licensed under the Apache 2 license. See the `LICENSE` file for the complete license.

## Changelog ##

### 0.1.0-SNAPSHOT ###

- *Initial implementation*
- Authorization Code Grant
- Client Credentials Grant
- Resource Owner Password Credentials Grant
- Refresh Tokens
- Bearer Tokens

## Roadmap ##

### 0.1.0 ###

- Implicit Grant

### 0.2.0 ###

- OAuth2 client
- MAC tokens
- SAML2 tokens
- JWT tokens

### 0.3.0 ###

- Dynamic client registration

### 0.4.0 ###

- OAuth1 provider and client