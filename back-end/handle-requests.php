<?php
require_once ("database.php");
/**
 * Created by PhpStorm.
 * User: davy
 * Date: 6/07/2017
 * Time: 15:54
 */
if (isset($_GET["action"]) && !empty($_GET["action"])) {
    $action = $_GET["action"];
    switch($action) {
        case "allbrands":
            echo(json_encode (carDatabase::getAllBrands()));
            break;
        case "brand":
            echo(json_encode (carDatabase::getAllCarsOfBrand($_GET["brand"])));
            break;
        case "categorie":
            echo(json_encode (carDatabase::getAllCategories()));
            break;
        case "options":
            echo(json_encode (carDatabase::getAllOptionsForCar($_GET["car"])));
            break;
    }
}