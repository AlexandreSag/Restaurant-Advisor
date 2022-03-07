<?php

namespace App\Http\Controllers;

use App\Models\Menu;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Database\Eloquent\ModelNotFoundException;
use App\Models\Restaurant;

class MenuController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request, $id)
    {
        try
        {
            Restaurant::findOrFail($id);
        }
        catch(ModelNotFoundException $e)
        {
            return abort(400);
        }
        $rules = array(
            'name' => 'required|string|max:255',
            'description' => 'required|string',
            'price' => 'required|numeric',
            'restaurant_id' => 'required|numeric',
        );

        $menu = ([
            'name' => $request->name,
            'description' => $request->description,
            'price' => $request->price,
            'restaurant_id' => $id,
        ]);

        $validator = Validator::make($menu, $rules);
          if ($validator->fails()) {
              return response()->json([
                'errors' => $validator->errors(),
              ], 400);
        }

        Menu::create($validator->validated());

        return response()->json([
        'success' => 'Menu créé'
        ], 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\Models\Menu  $menu
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
       return Menu::where('restaurant_id', $id)->get();
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Models\Menu  $menu
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $restaurant_id, $menu_id)
    {
        $menu = Menu::findOrFail($menu_id);

        if($menu->restaurant_id == $restaurant_id){
            if ($menu->update($request->all())){
                return response()->json([
                    'success' => 'Menu modified'
                ], 200);
            }
        }
        else{
            return response()->json([
                'fails' => 'Menu not found'
            ], 400);
        }
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Models\Menu  $menu
     * @return \Illuminate\Http\Response
     */
    public function destroy($restaurant_id, $menu_id)
    {
        $menu = Menu::findOrFail($menu_id);

        if($menu->restaurant_id == $restaurant_id){
            if ($menu->delete()){
                return response()->json([
                    'success' => 'Menu delete'
                ], 200);
            }
        }
        else{
            return response()->json([
                'fails' => 'Menu not found'
            ], 400);
        }
    }

        /**
     * Fonction recherche
     *
     * @param  \App\Models\Menu  $menu
     * @return \Illuminate\Http\Response
     */
    public function find($restaurant_id, $name){
        try
        {
            Menu::where('name', 'LIKE', '%'. $name. '%')->get();
        }
        catch(ModelNotFoundException $e)
        {
            return abort(400);
        }

        $find = Menu::where('name', 'LIKE', '%'. $name. '%')->get();
        
        if($find->isEmpty())
        {
            return response()->json([
                'fail' => 'no match'
            ], 200);
        }
        return $find;
    }
}
