package com.example.films


const val TAG = "Films Application"

const val APIKey = "24ab06e71fc730e392e92a68c467de4c"
const val APIUrl = "http://api.themoviedb.org"
const val APIBaseUrl = "http://image.tmdb.org/t/p/"

const val APIGetPopularMovies_Page = "$APIUrl/3/movie/popular?api_key=$APIKey&language=en-US&page="
const val APIGetGenresList = "$APIUrl/3/genre/movie/list?api_key=$APIKey&language=en-US"

const val widthBackgroundImage = "w780"
const val widthPosterImage = "w500"
const val widthActorImage = "w780"

const val TAG_retrieveMoviesWorkers = "tag_to_retrieve_movies_between_workers"

const val NOTIFICATION_CHANNEL_ID = "com.example.films_notifications"
