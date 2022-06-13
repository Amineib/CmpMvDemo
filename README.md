
# Movies Demo App

A Movies Demo App with a UI 100% made with Jetpack Compose. 
this demo is intended to interviewers. 

## PS
I'm leaving the api key hardcoded in the ApiKeyInterceptor class to simplify the usage of the demo app, i'm usually using Secrets Gradle Plugin to hide the api key in the local.properties file, and expose it via a variable BuildConfig.apiKey 

## PS2
This is the original app, so it has more functionalities and is more elaborate than the XML demo.

#### Get popular movies

```http
  GET /movie/popular
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `API_KEY` | `string` | **Required**. TMDB Api key, i'm leaving mine here to be deleted after i finish the interviews|

#### Returns the details for a specific movie

```http
  GET /movie/{movieId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `movieId`      | `string` | **Required**. Id of movie to fetch |

#### Get popular movies

```http
  GET /tv/popular
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `API_KEY` | `string` | **Required**. TMDB Api key, i'm leaving mine here to be deleted after i finish the interviews|

#### Returns the details for a specific movie

```http
  GET /tv/{serieId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `serieId`      | `string` | **Required**. Id of movie to fetch |

## Features

- Show list of popular movie
- Show details related to a movie
- Show list of popular series
- Show details of a serie 
- Show recommandations based on a serie or a movie
- toggle nightmode



## Android Architecture Components samples

Android Architecture Components used
- Room
- Compose
- Navigation with Compose
- Retrofit
- Pager 3 
- Viewmodels
- Flow 
- Navigation
