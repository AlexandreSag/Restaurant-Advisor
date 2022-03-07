<?php
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\MenuController;
use App\Http\Controllers\RestaurantController;
use App\Http\Controllers\Auth\AuthenticatedSessionController;
use App\Http\Controllers\Auth\RegisteredUserController;


/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::post('/register', [RegisteredUserController::class, 'store']);
Route::post('/auth', [AuthenticatedSessionController::class, 'store']);
Route::get('/users', [AuthenticatedSessionController::class, 'show']);
Route::get('/user/{id}',[AuthenticatedSessionController::class, 'index']);

Route::get('/restaurants',[RestaurantController::class,'index']);
Route::get('/restaurant/{id}',[RestaurantController::class,'show']);
Route::get('/restaurant/{id}/menus',[MenuController::class,'show']);

Route::get('/restaurant/find/{name}',[RestaurantController::class,'find']);
Route::get('/restaurant/{id}/menu/find/{name}',[MenuController::class,'find']);

Route::middleware('auth:api')->group(function() {
    Route::post('/restaurant',[RestaurantController::class,'store']);
    Route::put('/restaurant/{id}',[RestaurantController::class,'update']);
    Route::delete('/restaurant/{id}',[RestaurantController::class,'destroy']);

    Route::post('/restaurant/{id}/menu',[MenuController::class,'store']);
    Route::put('/restaurant/{id}/menu/{menu_id}',[MenuController::class,'update']);
    Route::delete('/restaurant/{id}/menu/{menu_id}',[MenuController::class,'destroy']);
});
