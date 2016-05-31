package com.example.lenovo.testclasses;

import android.support.annotation.StringDef;

import com.example.lenovo.domain.Clas;
import com.example.lenovo.domain.PasswordChanger;
import com.example.lenovo.domain.Question;
import com.example.lenovo.domain.QuestionAndroid;
import com.example.lenovo.domain.QuestionResponse;
import com.example.lenovo.domain.StudentAnswerResponse;
import com.example.lenovo.domain.StudentGrade;
import com.example.lenovo.domain.Test;
import com.example.lenovo.domain.TestResponse;
import com.example.lenovo.domain.TokenResponse;
import com.example.lenovo.domain.User;
import com.example.lenovo.domain.UserLogin;
import com.example.lenovo.domain.network.ListQuestionsTokenResponse;
import com.example.lenovo.domain.network.QuestionToken;
import com.example.lenovo.domain.network.SingleTestTokenResponse;
import com.example.lenovo.domain.network.StudentAnswerToken;
import com.example.lenovo.domain.network.StudentGradesTokenResponse;
import com.example.lenovo.domain.network.TestToken;
import com.example.lenovo.domain.network.TestTokenResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Apis {

    //nu trebuie
    @GET("/ClassTest-Webapp/rest/test/login/get/{username}/{password}")
    Call<UserLogin> getUserLogin(@Path("username") String username, @Path("password") String password);

    //nu trebuie
    @GET("/ClassTest-Webapp/rest/test/register/getClasses")
    Call<List<Clas>> getClasses();

    //gata
    @GET("/ClassTest-Webapp/rest/test/testService/getTestsOfUser/{id}/{token}")
    Call<TestTokenResponse> getAllTestsOfUser(@Path("id") int id, @Path("token") String token);

    //gata
    @GET("/ClassTest-Webapp/rest/student/getStatistics/{id}/{token}")
    Call<StudentGradesTokenResponse> getGradesOfStudent(@Path("id") int id, @Path("token") String token);

    //nu trebuie
    @GET("/ClassTest-Webapp/rest/test/login/checkIfAvailable/{token}")
    Call<TokenResponse> checkIfAvailable(@Path("token") String token);

    //gata
    @POST("/ClassTest-Webapp/rest/test/login/logout")
    Call<TokenResponse> logout(@Body UserLogin userLogin);

    //gata
    @POST("/ClassTest-Webapp/rest/test/register/createUser")
    Call<Void> createUser(@Body User user);

    //nu trebuie
    @POST("/ClassTest-Webapp/rest/test/forgotPassword/sendEmail")
    Call<Void> sendEmail(@Body User user);

    //nu trebuie
    @POST("/ClassTest-Webapp/rest/test/forgotPassword/changePassword")
    Call<Void> changePassword(@Body PasswordChanger changer);

    //
    @GET("/ClassTest-Webapp/rest/test/testService/getTestWithId/{id}/{token}")
    Call<SingleTestTokenResponse> getTestWithId(@Path("id") int id, @Path("token") String token);

    //
    @GET("/ClassTest-Webapp/rest/test/testService/getQuestionWithTestId/{id}/{token}")
    Call<ListQuestionsTokenResponse> getQuestionsWithTestId(@Path("id") int id, @Path("token") String token);

    //gata
    @POST("/ClassTest-Webapp/rest/test/testService/makeAvailable")
    Call<TokenResponse> makeTestAvailable(@Body TestToken testToken);

    //gata
    @POST("/ClassTest-Webapp/rest/test/testService/makeQuestionAvailable")
    Call<TokenResponse> makeQuestionAvailable(@Body QuestionToken questionToken);

    //gata
    @POST("/ClassTest-Webapp/rest/test/testService/makeUnavailableAndClosed")
    Call<TokenResponse> makeTestCloseAndUnavailable(@Body TestToken testToken);

    //gata
    @GET("/ClassTest-Webapp/rest/student/getGradesOfTest/{testId}/{token}")
    Call<StudentGradesTokenResponse> getGradesOfTest(@Path("testId") int testId, @Path("token") String token);

    //gata
    @GET("/ClassTest-Webapp/rest/test/testService/getAvailableTest/{userId}/{token}")
    Call<SingleTestTokenResponse> getAvailbleTestForUser(@Path("userId") int userId, @Path("token") String token);

    @GET("/ClassTest-Webapp/rest/test/testService/verifyIfQuestionIsAvailable/{questionId}")
    Call<QuestionResponse> verifyIfQuestionIsAvailable(@Path("questionId") int questionId);

    @GET("/ClassTest-Webapp/rest/test/testService/verifyIfTestIsAvailable/{testId}")
    Call<TestResponse> verifyIfTestIsAvailable(@Path("testId") int testId);

    //rezolvat
    @POST("/ClassTest-Webapp/rest/test/answer/studentAnswer")
    Call<TokenResponse> sendAnswer(@Body StudentAnswerToken studentAnswerToken);
}
