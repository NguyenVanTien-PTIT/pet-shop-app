package com.example.ecommerce_mobile_app.api;

import com.example.ecommerce_mobile_app.model.CartItem;
import com.example.ecommerce_mobile_app.model.Category;
import com.example.ecommerce_mobile_app.model.Country;
import com.example.ecommerce_mobile_app.model.Customer;
import com.example.ecommerce_mobile_app.model.Order;
import com.example.ecommerce_mobile_app.model.Product;
import com.example.ecommerce_mobile_app.model.Profile;
import com.example.ecommerce_mobile_app.model.Question;
import com.example.ecommerce_mobile_app.model.Review;
import com.example.ecommerce_mobile_app.model.ShippingAddress;
import com.example.ecommerce_mobile_app.model.State;
import com.example.ecommerce_mobile_app.model.WishlistItem;
import com.example.ecommerce_mobile_app.model.request.PostQuestionRequest;
import com.example.ecommerce_mobile_app.model.request.SendReviewRequest;
import com.example.ecommerce_mobile_app.model.request.SignInRequest;
import com.example.ecommerce_mobile_app.model.request.SignUpRequest;
import com.example.ecommerce_mobile_app.model.request.UpdatePasswordRequest;
import com.example.ecommerce_mobile_app.model.response.BaseResponse;
import com.example.ecommerce_mobile_app.model.response.LoadHomePageResponse;
import com.example.ecommerce_mobile_app.model.response.LoadOrderResponse;
import com.example.ecommerce_mobile_app.model.response.LoadProductPageResponse;
import com.example.ecommerce_mobile_app.model.response.LoginResponse;
import com.example.ecommerce_mobile_app.model.response.OrdersDTO;
import com.example.ecommerce_mobile_app.model.response.PaymentResponse;
import com.example.ecommerce_mobile_app.model.response.ResponseNormal;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    //    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
//    APIService apiService = new Retrofit.Builder()
//            .baseUrl("http://192.168.1.13:8081/")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//            .create(APIService.class);
    @GET("api/category")
    Call<List<Category>> getCategories();

    @GET("api/product")
    Call<List<Product>> getProducts();

    @GET("api/product/{id}")
    Call<Product> getProductById(@Path("id") int id);

    @POST("/login")
    Call<LoginResponse> signIn(@Body SignInRequest signInRequest);

    @POST("/user")
    Call<Customer> signUp(@Body SignUpRequest signUpRequest);

    @GET("api/cart/{customerId}")
    Call<BaseResponse<List<CartItem>>> getCart(@Path("customerId") int customerId);

    @POST("api/cart/{customerId}/update/{productId}/{quantity}")
    Call<BaseResponse<List<CartItem>>> updateCartItem(@Path("customerId") int customerId, @Path("productId") int productId, @Path("quantity") int quantity);

    @DELETE("api/cart/{customerId}/remove/{productId}")
    Call<BaseResponse<List<CartItem>>> removeCartItem(@Path("customerId") int customerId, @Path("productId") int productId);

    @POST("api/cart/{customerId}/add/{productId}/{quantity}")
    Call<BaseResponse<List<CartItem>>> addCartItem(@Path("customerId") int customerId, @Path("productId") int productId, @Path("quantity") int quantity);

    @DELETE("api/cart/{customerId}/delete")
    Call<BaseResponse<List<CartItem>>> deleteCart(@Path("customerId") int customerId);

    @POST("api/account/{id}/update_info")
    Call<BaseResponse<Customer>> updateInfo(@Path("id") int id, @Body Profile profile);

    @Multipart
    @POST("api/account/{id}/upload_photo")
    Call<BaseResponse<String>> updatePhoto(@Path("id") int id, @Part MultipartBody.Part file);

    @POST("api/account/{id}/update_password")
    Call<BaseResponse<String>> updatePassword(@Path("id") int id, @Body UpdatePasswordRequest updatePasswordRequest);

    @POST("api/account/{id}/update_address")
    Call<BaseResponse<String>> updateAddress(@Path("id") int id, @Body ShippingAddress shippingAddress);

    @GET("api/country")
    Call<BaseResponse<List<Country>>> getCountry();

    @GET("api/list_states_by_country/{id}")
    Call<BaseResponse<List<State>>> getStateByCountry(@Path("id") int id);

    @GET("api/account/{customerId}/order")
    Call<BaseResponse<List<Order>>> getOrders(@Path("customerId") int customerId);

    @GET("api/wishlist/{customerId}")
    Call<BaseResponse<List<WishlistItem>>> getWishlist(@Path("customerId") int customerId);

    @POST("api/wishlist/{customerId}/add/{productId}")
    Call<BaseResponse<List<WishlistItem>>> addWishlistItem(@Path("customerId") int customerId, @Path("productId") int productId);

    @DELETE("api/wishlist/{customerId}/remove/{productId}")
    Call<BaseResponse<List<WishlistItem>>> removeWishlistItem(@Path("customerId") int customerId, @Path("productId") int productId);

    @DELETE("api/wishlist/{customerId}/delete")
    Call<BaseResponse<List<WishlistItem>>> deleteWishlist(@Path("customerId") int customerId);

    @GET("api/list_questions_by_product/{productId}")
    Call<BaseResponse<List<Question>>> getQuestions(@Path("productId") int productId);

    @POST("api/question/{customerId}/post/{productId}")
    Call<BaseResponse<String>> sendQuestion(@Path("customerId") int customerId, @Path("productId") int productId, @Body PostQuestionRequest questionRequest);

    @GET("api/list_reviews_by_product/{productId}")
    Call<BaseResponse<List<Review>>> getReviews(@Path("productId") int productId);

    @GET("api/{customerId}/check_customer_can_review/{productId}")
    Call<BaseResponse<Boolean>> canReview(@Path("customerId") int customerId, @Path("productId") int productId);

    @GET("api/{customerId}/did_customer_reviewed/{productId}")
    Call<BaseResponse<Boolean>> hadReview(@Path("customerId") int customerId, @Path("productId") int productId);

    @POST("api/{customerId}/write_review/{productId}")
    Call<BaseResponse<String>> writeReview(@Path("customerId") int customerId, @Path("productId") int productId, @Body SendReviewRequest sendReviewRequest);

    @POST("api/{customerId}/place_order")
    Call<BaseResponse<String>> placeOrder(@Path("customerId") int customerId);

    @Multipart
    @POST("/user/{id}/upload_photo")
    Call<ResponseNormal<String>> uploadAvatar(@Path("id") int userId, @Part MultipartBody.Part file);

    @GET("/")
    Call<LoadHomePageResponse> loadHomePage();

    @GET("/product-page")
    Call<LoadProductPageResponse> loadProductPage();

    @GET("/product-page/cart")
    Call<BaseResponse<List<CartItem>>> addToCart(@Query("iduser") int customerId,
                                                 @Query("idproduct") int productId);

    @GET("/order/mobile")
    Call<LoadOrderResponse> loadOrderByCustomer(@Header("Authorization") String authHeader);

    @DELETE("/order/remove/mobile")
    Call<LoadOrderResponse> removeCartItem(@Query("id-product-order") int orderProductId,
                                           @Header("Authorization") String authHeader);

    @POST("/order/update/mobile")
    Call<LoadOrderResponse> updateCartItem(@Query("id-product-order") int orderProductId,
                                           @Query("action") String action,
                                           @Header("Authorization") String authHeader);

    @DELETE("/order/remove-all/{order-id}")
    Call<LoadOrderResponse> clearCartItems(@Path("order-id") int orderId,
                                           @Header("Authorization") String authHeader);

    @POST("/order/payment/{userId}")
    Call<PaymentResponse> payment(@Path("userId") int userId,
                                  @Body OrdersDTO ordersDTO,
                                  @Header("Authorization") String authHeader);

    @GET("/order/history")
    Call<List<OrdersDTO>> getOrderHistory(@Query("id-user") int userId,
                                          @Header("Authorization") String authHeader);

    @GET("/order/detail")
    Call<List<CartItem>> getOrderDetail(@Query("id") int orderId,
                                         @Header("Authorization") String authHeader);

    @PUT("/user/mobile")
    Call<LoginResponse> updateCustomer(@Body Customer customer,
                                       @Header("Authorization") String authHeader);

}
