<?php

namespace App\Http\Controllers\Auth;

use App\Models\User;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Hash;
use Illuminate\Database\Eloquent\ModelNotFoundException;



class AuthenticatedSessionController extends Controller
{
    /**
     * Handle an incoming authentication request.
     *
     * @param  \App\Http\Requests\Auth\LoginRequest  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $request->validate([
            'login' => 'required',
            'password' => 'required',
        ]);
     
        $user = User::where('login', $request->login)->first();
     
        if (! $user || ! Hash::check($request->password, $user->password)) {
            return response()->json([
                'error' => 'Wrong password or username'
            ], 400);
        }
        
        return response()->json([
            'success' => 'User connected',
            'user' => $user,
            'api_token' => $user->api_token,
        ], 200);
    }

    /**
     *
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function show()
    {
        return User::all();
    }

    /**
     * 
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function index($id)
    {
        try
        {
            User::findOrFail($id);
        }
        catch(ModelNotFoundException $e)
        {
            return abort(400);
        }
        return User::findOrFail($id);
    }
}
