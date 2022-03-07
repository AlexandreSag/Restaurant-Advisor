# Monkeat

Monkeat is an api and a mobile application

## Installation

Step 1 clone reposetory
```bash
git clone git@rendu-git.etna-alternance.net:module-8726/activity-47735/group-953732
```

## Setup and Start API

Step 1 Go to the folder Monkeat

Step 2 edit env file with the correct login to your database

Step 3 Start migration and factory
```bash
php artisan migrate:fresh --seed
```
Step 4 Start API
```bash
php artisan serve
```

## Get your Monkeat API Token

Setp 1: Register
    Use Register route "localhost:8000/api/register" and enter your information login, password, email, name, firstname, age!

Setp 2: Get your Api Token
    Use auth route "localhost:8000/api/auth" and enter your login and password

## All API routes

All get methods do not require a token

```bash
methods  URl                         input        
post 	/register                    login, password, email, name, firstname, age
post 	/auth	                     login, password,     
get 	/users 	                     //               
get 	/restaurants 	             //
post 	/restaurant                  name, description, grade, localization, phone_number, website, hours
put 	/restaurant/{id} 	         name, description, grade, localization, phone_number, website, hours
delete 	/restaurant/{id} 	         //
get 	/restaurant/{id}/menus 	     //
post 	/restaurant/{id}/menu	     name, description, price
put 	/restaurant/{id}/menu/{id} 	 name, description, price
delete 	/restaurant/{id}/menu/{id} 	 //
```

## Build Mobil App

Step 1 Go to the file MonkeatApp

Step 2 open the file named "Monkeat" with Android Studio

Step 3 wait the gradle and build the App

## Contributor

heras_v and sage_a
