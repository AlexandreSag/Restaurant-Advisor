<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;

class RestaurantFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        $hours = $this->faker->dayOfWeek();
        $hours0 = $hours." to ";
        $hours1 = $hours0.$this->faker->dayOfWeek();
        
        return [
            'name' => $this->faker->sentence(1,true),
            'description' => $this->faker->sentence(4,true),
            'grade' => $this->faker->numberBetween(0,5),
            'localization' => $this->faker->address(),
            'phone_number' => $this->faker->phoneNumber(),
            'website' => $this->faker->domainName(),
            'hours' => $hours1,
        ];
    }
}
