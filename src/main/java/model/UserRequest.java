package model;

import config.Config;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserRequest {

    public static Response createUser(User user) {
        return given()
                .header("Content-Type", "application/json")
                .body(user)
                .post(Config.API_USER_CREATE);
    }

    public static Response authUser(User user) {
        return given()
                .header("Content-Type", "application/json")
                .body(user)
                .post(Config.API_AUTH_LOGIN);
    }

    public static Response deleteUser(String authToken) {
        String URIaddress = Config.API_USER_AUTH;
        return given()
                .header("Authorization", authToken)
                .delete(URIaddress);
    }
}