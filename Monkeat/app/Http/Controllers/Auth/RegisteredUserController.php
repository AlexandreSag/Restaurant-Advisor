<?php

namespace App\Http\Controllers\Auth;

use App\Models\User;
use Illuminate\Support\Str;
use Illuminate\Http\Request;
use Illuminate\Validation\Rules;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use Illuminate\Auth\Events\Registered;
use Illuminate\Support\Facades\Validator;

class RegisteredUserController extends Controller
{
    /**
     * Handle an incoming registration request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     *
     * @throws \Illuminate\Validation\ValidationException
     */
    public function store(Request $request)
    {
        //login, password, email, name, firstname, age
        $rules = array(
          'login' => ['required', 'string', 'max:255', 'unique:users'],
          'password' => ['required', Rules\Password::defaults()],
          'email' => ['required', 'string', 'email', 'max:255', 'unique:users'],
          'name' => ['required', 'string', 'max:255'],
          'firstname' => ['required', 'string', 'max:255'],
          'age'=> ['required', 'integer', 'max:255'],
          'api_token' => ['required', 'string', 'max:101']
        );
        
        $api_token = Str::random(100);

        $user = ([
          'login' => $request->login,
          'password' => Hash::make($request->password),
          'email' => $request->email,
          'name' => $request->name,
          'firstname' => $request->firstname,
          'age' => $request->age,
          'api_token' => $api_token,
        ]);

        $validator = Validator::make($user, $rules);

          if ($validator->fails()) {
              return response()->json([
                'errors' => $validator->errors(),
              ], 400);
          }

          User::create($validator->validated());
          

          return response()->json([
            'success' => 'User créé'
          ], 201);
    }
}
