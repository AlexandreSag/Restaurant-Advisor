<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;

class MenuFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        return [
            'name' => $this->faker->sentence(1,true),
            'description' => $this->faker->sentence(3,true),
            'price' => $this->faker->numberBetween(1,50),
            'restaurant_id' => $this->faker->numberBetween(1,10),
        ];
    }
}
