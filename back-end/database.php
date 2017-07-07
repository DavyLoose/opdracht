<?php
/**
 * Created by PhpStorm.
 * User: davy
 * Date: 6/07/2017
 * Time: 15:58
 */
class Db {

    public static function getConnection() {
        $host = "fdb16.awardspace.net";
        $database = "2388879_cars";
        $dsn = "mysql:host=$host;dbname=$database";
        // Try to connect to the database
        try{
            $conn = new PDO($dsn, "2388879_cars", "g5SaN6JSMrChvjs3");
            $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        } catch (PDOException $err) {
            return $err;
        }
        return $conn;
    }

}

class carDatabase {

    public static function getAllBrands()
    {
            return carDatabase::getAllTemplate("SELECT * FROM Brands");
    }

    public static function getAllCategories(){
        return carDatabase::getAllTemplate("SELECT * FROM categorie");
    }

    private static function getAllTemplate($select){
        try {
            $conn = Db::getConnection();
            $stmt = $conn->prepare($select);
            $stmt->execute();
            return  $stmt->fetchAll(PDO::FETCH_CLASS);
        } catch (PDOException $err) {
            return "An error occured during the getAllRecipe's Instance" + $err;
        }
    }

    public static function getAllCarsOfBrand($brand){
        try {
            $conn = Db::getConnection();
            $stmt = $conn->prepare("SELECT *
                  FROM cars
                  WHERE Brand_id = :brand");
            $stmt->bindValue(':brand', (int)$brand, PDO::PARAM_INT);
            $stmt->execute();
            return $stmt->fetchAll(PDO::FETCH_CLASS);
        } catch (PDOException $err) {
            return $err;
        }
    }

    public static function getAllOptionsForCar($car){
        try {
            $conn = Db::getConnection();
            $stmt = $conn->prepare("SELECT Options.ID, Car_ID, Option_Name, Price, Categorie_name
       FROM options
       JOIN categorie ON categorie.ID = options.Categorie_ID    
                  WHERE Car_ID = :car or Car_ID is NULL
                    ORDER BY Categorie_name ASC, price ASC");
            $stmt->bindValue(':car', (int)$car, PDO::PARAM_INT);
            $stmt->execute();
            return $stmt->fetchAll(PDO::FETCH_CLASS);
        } catch (PDOException $err) {
            return $err;
        }
    }



}