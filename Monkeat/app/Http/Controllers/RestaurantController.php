<?php

namespace App\Http\Controllers;

use App\Models\Restaurant;
use App\Models\Menu;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Database\Eloquent\ModelNotFoundException;

class RestaurantController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return Restaurant::all();
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $rules = array(
            'name' => 'required|string|max:255',
            'description' => 'required|string',
            'grade' => 'required|numeric|between:1,5',
            'localization' => 'required|string',
            'phone_number' => 'required|string|max:32',
            'website' => 'required|string|max:255',
            'hours' => 'required|string|max:255',
        );

        $validator = Validator::make($request->all(), $rules);

          if ($validator->fails()) {
              return response()->json([
                'errors' => $validator->errors(),
              ], 400);
          }

          Restaurant::create($validator->validated());

          return response()->json([
            'success' => 'Restaurant créé'
          ], 201);

    }

    /**
     * Display the specified resource.
     *
     * @param  \App\Models\Restaurant  $restaurant
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        try
        {
            Restaurant::findOrFail($id);
        }
        catch(ModelNotFoundException $e)
        {
            return abort(400);
        }
        return Restaurant::findOrFail($id);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Models\Restaurant  $restaurant
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        try
        {
            Restaurant::findOrFail($id);
        }
        catch(ModelNotFoundException $e)
        {
            return abort(400);
        }
        $restaurant = Restaurant::findOrFail($id);

        $rules = array(
            'name' => 'required|string|max:255',
            'description' => 'required|string',
            'grade' => 'required|numeric|between:1,5',
            'localization' => 'required|string',
            'phone_number' => 'required|string|max:32',
            'website' => 'required|string|max:255',
            'hours' => 'required|string|max:255',
        );

        $validator = Validator::make($request->all(), $rules);

          if ($validator->fails()) {
              return response()->json([
                'errors' => $validator->errors(),
              ], 400);
          }

          $restaurant->update($request->all());

          return response()->json([
            'success' => 'Restaurant updated'
          ], 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Models\Restaurant  $restaurant
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        try
        {
            Restaurant::findOrFail($id);
        }
        catch(ModelNotFoundException $e)
        {
            return abort(400);
        }

        $restaurant = Restaurant::findOrFail($id);

        Menu::where('restaurant_id', $id)->delete();

        if ($restaurant->delete()){
            return response()->json([
                'success' => 'Restaurant suprimed'
            ], 200);
        }

    }

    /**
     * Fonction recherche
     *
     * @param  \App\Models\Restaurant  $restaurant
     * @return \Illuminate\Http\Response
     */
    public function find($name){
        try
        {
            Restaurant::where('name', 'LIKE', '%'. $name. '%')->get();
        }
        catch(ModelNotFoundException $e)
        {
            return abort(400);
        }
        $find = Restaurant::where('name', 'LIKE', '%'. $name. '%')->get();

        if($find->isEmpty())
        {
            return response()->json([
                'fail' => 'no match'
            ], 400);
        }
        return $find;
    }
}
