![Songfind](/img/songfind-banner.png)

[![build](https://github.com/alxlenc/songfind/actions/workflows/maven.yml/badge.svg)](https://github.com/alxlenc/songfind/actions/workflows/maven.yml)  ![coverage](.github/badges/jacoco.svg)  [![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT)

# Intro

This is a demo project used to practice and experiment with technologies while using a fun domain,
music. It's 100% dependent on connectivity to Spotify's API and does have any persistence.

The topics that you can find on this repo are Spring Security, OAuth2 and OIDC, Keycloak, Spring
Webflux, Hexagonal Architecture, Reactive caching with Redis and containerization.

The app has two layers of authentication. First it uses Keycloak as and identity provider but once
you log in you need additional authentication with Spotify. The reason behind this is that, besides
experimenting with Keycloak and OAuth2, I can host the application online without it being publicly
accessible. This way I don't have to deal with regulations. What regulations? Well, think about how
many cookie banners you have to close during the day...

This repo is only one piece in the tech stack. It is built automatically using a Tekton pipeline and
deployed using a gitops approach with ArgoCD into an Istio service mesh. It also has a basic front
built in Vue.js.

Want to see what I mean with all that? Take a look at the [videos](#videos).

If you want to experiment with this repo you need to go
to [Spotify Developer Dashboard](https://developer.spotify.com/dashboard/) and create a Spotify
application that would provide you with a client id and a client secret.

---

# Key Features

At the moment music discoverabilty is the main feature.

---

# Videos

Here are a some videos to showcase the tech stack and the development workflow.

#### [Recommendations Filters Feature](https://www.youtube.com/watch?v=QmzbV03ACkA)

#### [Hexagonal Architecture](https://www.youtube.com/watch?v=eiGwvGBXFT8)

#### [Tekton Pipelines](https://www.youtube.com/watch?v=HzNx88H7nxU)

#### [Canary Release](https://www.youtube.com/watch?v=HzNx88H7nxU)

#### [Dark Release](https://www.youtube.com/watch?v=zqSmLRE0lIQ)


---

# License

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT)



