package com.bookit.utilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class APIUtilities  {

    private static String URI = ConfigurationReader.getValue("bookit.api.qa1");

    /**
     * Method that generates access token
     *
     * @return bearer token
     */
    public static String getTokenForBookit() {
        // https://cybertek-reservation-api-qa.herokuapp.com/sign?email={value}&password={value}
        Response response = given().
                queryParam("email", ConfigurationReader.getValue("team.leader.email")).
                queryParam("password", ConfigurationReader.getValue("team.leader.password")).
                when().
                get("/sign").prettyPeek();

        return response.jsonPath().getString("accessToken");
    }

    /**
     * Method that generates access token
     * @param role - type of user. allowed types: student team leader, student team member and teacher
     * @return bearer token
     */
    public static String getTokenForBookit(String role) {
        String userName = "";
        String password = "";
        if (role.toLowerCase().contains("lead")) {
            userName = ConfigurationReader.getValue("team.leader.email");
            password = ConfigurationReader.getValue("team.leader.password");
        } else if (role.toLowerCase().contains("teacher")) {
            userName = ConfigurationReader.getValue("teacher.email");
            password = ConfigurationReader.getValue("teacher.password");
        } else if (role.toLowerCase().contains("member")) {
            userName = ConfigurationReader.getValue("team.member.email");
            password = ConfigurationReader.getValue("team.member.password");
        } else {
            throw new RuntimeException("Invalid user type!");
        }
        Response response = given().
                queryParam("email", userName).
                queryParam("password", password).
                when().
                get("/sign").prettyPeek();
        return response.jsonPath().getString("accessToken");
    }

    public static void space() {
        System.out.println("\n########################################################################################\n");
    }

}
